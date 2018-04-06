
package com.confiance.framework.api.core;

import cc.plural.jsonij.JPath;

import com.confiance.framework.api.utils.LoggerUtil;
import com.google.gson.Gson;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Author: Nainappa Illi 
 * Date: 02/17/2018
 */
public class JsonBody extends Body {
  private static final Gson gson = new Gson();
  private static final String TEMPLATE_START_SYMBOL = "{%";
  private static final String TEMPLATE_END_SYMBOL = "%}";
  private static final String MID_START_SYMBOL_QUOTE = "\"<+-+>";
  private static final String MID_END_SYMBOL_QUOTE = "<-+->\"";
  private static final String MID_START_SYMBOL = "\"<--->";
  private static final String MID_END_SYMBOL = "<+++>\"";
  private static final String TO_BE_REPLACED_START = "<+-+->";
  private static final String TO_BE_REPLACED_END = "<-+-+>";

  public JsonBody(String rawJson) {
    setBodyString(rawJson);
  }

  public JsonBody(String template, Map<String, Object> params) {
    String processedTemplate = preProcessSrc(template);
    String src = new String(processedTemplate);
    if (params == null) {
      params = new HashMap<String, Object>();
    }

    for (Map.Entry<String, Object> e : params.entrySet()) {
      src = src.replace(MID_START_SYMBOL_QUOTE + e.getKey() + MID_END_SYMBOL_QUOTE,
          MID_START_SYMBOL_QUOTE + TO_BE_REPLACED_START + e.getKey() + TO_BE_REPLACED_END
              + MID_END_SYMBOL_QUOTE);
      src = src.replace(MID_START_SYMBOL + e.getKey() + MID_END_SYMBOL, MID_START_SYMBOL
          + TO_BE_REPLACED_START + e.getKey() + TO_BE_REPLACED_END + MID_END_SYMBOL);
    }

    try {
      src = removeUnusedNodeOfJson(src, processedTemplate);
    } catch (IOException e) {
      LoggerUtil.log(e.getMessage());
    }

    for (Map.Entry<String, Object> e : params.entrySet()) {
      src = src.replace(MID_START_SYMBOL_QUOTE + TO_BE_REPLACED_START + e.getKey()
          + TO_BE_REPLACED_END + MID_END_SYMBOL_QUOTE, "\"" + convertObject(e.getValue()) + "\"");
      src = src.replace(MID_START_SYMBOL + TO_BE_REPLACED_START + e.getKey() + TO_BE_REPLACED_END
          + MID_END_SYMBOL, convertObject(e.getValue()));
    }

    setBodyString(src);
  }

  /* object converters */
  private String convertObject(Object src) {
    if (src instanceof String || src instanceof Integer || src instanceof Boolean) {
      return String.valueOf(src);
    }
    try {
      return gson.toJson(src);
    } catch (Exception e) {
      LoggerUtil.log(e.getMessage());
      Map<String, Object> newMap = new HashMap<String, Object>();
      if (src instanceof HashMap) {
        Map s = (HashMap) src;

        for (Object v : s.values()) {
          if (v instanceof TestObject) {
            newMap.put("arg0", v);
          }
        }

        return gson.toJson(newMap);
      } else {
        return "{}";
      }
    }
  }

  @Override
  public String get(String jPath) throws NoSuchFieldException {
    try {
      cc.plural.jsonij.JSON json = cc.plural.jsonij.JSON.parse(getBodyString());
      return JPath.evaluate(json, jPath).toString();
    } catch (Exception e) {
      LoggerUtil.log(e.getMessage());
      throw new NoSuchFieldException(String.format("Cannot find [%s] in body: [%s] \n %s", jPath,
          getBodyString(), e.getMessage()));
    }
  }

  @Override
  public <T> T getObject(final String jPath, final Class<T> type) throws NoSuchFieldException {
    return gson.fromJson(get(jPath), type);
  }

  @Override
  public <T> T getObject(final Class<T> type) throws NoSuchFieldException {
    return getObject("/", type);
  }

  private String preProcessSrc(String src) {
    String processedSrc = src.replaceAll(
        Pattern.quote(String.format("\"%s", TEMPLATE_START_SYMBOL)), MID_START_SYMBOL_QUOTE);
    processedSrc = processedSrc.replaceAll(
        Pattern.quote(String.format("%s\"", TEMPLATE_END_SYMBOL)), MID_END_SYMBOL_QUOTE);
    processedSrc = processedSrc
        .replaceAll(Pattern.quote(String.format("%s", TEMPLATE_START_SYMBOL)), MID_START_SYMBOL);
    processedSrc = processedSrc.replaceAll(Pattern.quote(String.format("%s", TEMPLATE_END_SYMBOL)),
        MID_END_SYMBOL);
    return processedSrc;
  }

  private String removeUnusedNodeOfJson(String json, String template) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(json);
    JsonNode templateRoot = mapper.readTree(template);
    ObjectWriter writer = mapper.writer();

    removeEmptyJsonNodes(root, templateRoot);
    return writer.writeValueAsString(root);
  }

  private JsonNode removeEmptyJsonNodes(JsonNode root, JsonNode template) throws IOException {
    ObjectWriter writer = new ObjectMapper().writer();

    if (root.isObject()) {
      List<String> nodeToRemove = new ArrayList<String>();
      Iterator<String> fls = root.getFieldNames();
      ObjectNode objNode = (ObjectNode) root;
      while (fls.hasNext()) {
        String fieldName = fls.next();
        JsonNode rootChild = objNode.get(fieldName);
        JsonNode templateChild = template.get(fieldName);

        String rootChildSerialized = writer.writeValueAsString(rootChild);
        String templateChildSerialized = writer.writeValueAsString(templateChild);
        if (!rootChild.getFieldNames().hasNext()
            && rootChildSerialized.equals(templateChildSerialized)
            && (rootChildSerialized.contains(MID_START_SYMBOL)
                || rootChildSerialized.contains(MID_START_SYMBOL_QUOTE))) {
          nodeToRemove.add(fieldName);
        } else {
          removeEmptyJsonNodes(rootChild, templateChild);
          if ("{}".equalsIgnoreCase(writer.writeValueAsString(rootChild))
              || "[]".equalsIgnoreCase(writer.writeValueAsString(rootChild))) {
            nodeToRemove.add(fieldName);
          }
        }

      }

      for (String field : nodeToRemove) {
        objNode.remove(field);
      }
      return objNode;
    } else if (root.isArray()) {
      List<Integer> nodeToRemove = new ArrayList<Integer>();
      ArrayNode arrayNode = (ArrayNode) root;
      for (int i = 0; i < arrayNode.size(); ++i) {
        JsonNode rootChild = arrayNode.get(i);
        JsonNode templateChild = template.get(i);

        String rootChildSerialized = writer.writeValueAsString(rootChild);
        String templateChildSerialized = writer.writeValueAsString(templateChild);
        if (!rootChild.getFieldNames().hasNext()
            && rootChildSerialized.equals(templateChildSerialized)
            && (rootChildSerialized.contains(MID_START_SYMBOL)
                || rootChildSerialized.contains(MID_START_SYMBOL_QUOTE))) {
          nodeToRemove.add(i);
        } else {
          removeEmptyJsonNodes(rootChild, templateChild);
          if (!rootChild.getFieldNames().hasNext()) {
            nodeToRemove.add(i);
          }
        }
      }

      Collections.reverse(nodeToRemove);

      for (int i : nodeToRemove) {
        arrayNode.remove(i);
      }
      return arrayNode;
    } else {
      return root;
    }
  }

  @Override
  public List<Header> getRequiredHeaders() {
    List<Header> headers = new ArrayList<Header>();
    headers.add(new Header("Content-Type", "application/json"));
    headers.add(new Header("Accept", "application/json"));
    return headers;
  }
}
