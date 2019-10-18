
package com.confiance.framework.api.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class Executor {

	protected static ObjectMapper mapper = new ObjectMapper();

	public static Responses doGetCall(String strEndPoint, Map<String, String> headers) throws Exception {
		Request req = new Request(Method.GET, new EndPoint(strEndPoint));
		setHeaders(req, headers);
		setBody(req, "");
		return req.doExecute();
	}

	public static Responses doGetCall(String strEndPoint) throws Exception {
		Request req = new Request(Method.GET, new EndPoint(strEndPoint));
		Map<String, String> headers = new HashMap<String, String>();
		setHeaders(req, headers);
		setBody(req, "");
		return req.doExecute();
	}

	public static Responses doPostCall(String strEndPoint, String strBody, Map<String, String> headers)
			throws Exception {
		Request req = new Request(Method.POST, new EndPoint(strEndPoint));
		setHeaders(req, headers);
		setBody(req, strBody);
		return req.doExecute();
	}

	public static Responses doPostCall(String strEndPoint, String strBody) throws Exception {
		Request req = new Request(Method.POST, new EndPoint(strEndPoint));
		Map<String, String> headers = new HashMap<String, String>();
		setHeaders(req, headers);
		setBody(req, strBody);
		return req.doExecute();
	}

	public static Responses doPutCall(String strEndPoint, String strBody, Map<String, String> headers)
			throws Exception {
		Request req = new Request(Method.PUT, new EndPoint(strEndPoint));
		setHeaders(req, headers);
		setBody(req, strBody);
		return req.doExecute();
	}

	public static Responses doPutCall(String strEndPoint, String strBody) throws Exception {
		Request req = new Request(Method.PUT, new EndPoint(strEndPoint));
		Map<String, String> headers = new HashMap<String, String>();
		setHeaders(req, headers);
		setBody(req, strBody);
		return req.doExecute();
	}

	public static Responses doDeleteCall(String strEndPoint, String strBody, Map<String, String> headers)
			throws Exception {
		Request req = new Request(Method.DELETE, new EndPoint(strEndPoint));
		setHeaders(req, headers);
		setBody(req, strBody);
		return req.doExecute();
	}

	public static Responses doDeleteCall(String strEndPoint, String strBody) throws Exception {
		Request req = new Request(Method.DELETE, new EndPoint(strEndPoint));
		Map<String, String> headers = new HashMap<String, String>();
		setHeaders(req, headers);
		setBody(req, strBody);
		return req.doExecute();
	}

	public static void setHeaders(Request req, Map<String, String> headers) {
		// Add requiqred headers to request
		Iterator<String> itr = headers.keySet().iterator();
		while (itr.hasNext()) {
			String key = itr.next();
			req = req.addHeader(new Header(key, headers.get(key)));
		}
	}

	public static void setBody(Request req, String strBody) {
		// Add body to request
		Body bod = new Body();
		bod.setBodyString(strBody);
		req.setBody(bod);
	}

	public static <T> T doGetCallWithPojo(String strEndPoint, Map<String, String> headers, Class<T> responsePojo)
			throws Exception {
		Responses response = doGetCall(strEndPoint, headers);
		return mapper.readValue(response.getBody().getBodyString(), responsePojo);
	}

	public static <T> T doPostCallWithPojo(String strEndPoint, String strBody, Map<String, String> headers,
			Class<T> responsePojo) throws Exception {
		Responses response = doPostCall(strEndPoint, strBody, headers);
		return mapper.readValue(response.getBody().getBodyString(), responsePojo);
	}

	public static <T> T doPutCallWithPojo(String strEndPoint, String strBody, Map<String, String> headers,
			Class<T> responsePojo) throws Exception {
		Responses response = doPutCall(strEndPoint, strBody, headers);
		return mapper.readValue(response.getBody().getBodyString(), responsePojo);
	}

	public static <T> T doDeleteCallWithPojo(String strEndPoint, String strBody, Map<String, String> headers,
			Class<T> responsePojo) throws Exception {
		Responses response = doDeleteCall(strEndPoint, strBody, headers);
		return mapper.readValue(response.getBody().getBodyString(), responsePojo);
	}
}
