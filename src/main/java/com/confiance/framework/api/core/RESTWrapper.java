
package com.confiance.framework.api.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


/**
 * Author: Nainappa Illi Date: 08/8/2019
 */
public class RESTWrapper {
	private static final String SET_COOKIE = "Set-Cookie";

	/**
	 * Wrapper for all restAssured calls with Header mapper, Content Type, End
	 * point, Request body as String and Type of method request This is the common
	 * method for all the Restassured requests.
	 *
	 * @param Header
	 *            header for request
	 * @param ContentType
	 *            Content type for the request
	 * @param url
	 *            End point of the request
	 * @param strJsonBody
	 *            Schema Registry route
	 * @param requestMethodtype
	 *            requestmethod type.
	 */
	public static Responses doRequest(HashMap<String, Header> header, String contentType, String strURL,
			String strJsonBody, Method requestMethodtype) {
		Client _client = Client.create();
		WebResource wr = _client.resource(strURL);
		WebResource.Builder builder = wr.getRequestBuilder();

		// Fetching Response from API
		// ClientResponse cr = builder.method("POST", ClientResponse.class, jsonReqest);
		Map<String, String> headers = new HashMap<String, String>();
		ClientResponse cr = null;
		if (header != null) {
			for (Header h : header.values()) {
				builder = builder.header(h.getName(), h.getValue());
			}
		}
		builder = builder.header("Content-Type", contentType);
		if (requestMethodtype.name().equals("GET")) {
			cr = builder.method("GET", ClientResponse.class, null);
		} else if (requestMethodtype.name().equals("POST")) {
			cr = builder.method("POST", ClientResponse.class, strJsonBody);
		} else if (requestMethodtype.name().equals("PUT")) {
			cr = builder.method("PUT", ClientResponse.class, strJsonBody);
		} else if (requestMethodtype.name().equals("DELETE")) {
			cr = builder.method("DELTE", ClientResponse.class, strJsonBody);
		} else if (requestMethodtype.name().equals("OPTIONS")) {
			cr = builder.method("OPTIONS", ClientResponse.class, strJsonBody);
		} else
			throw new RuntimeException("Invalid method type. Please mention the proper request method type.");
		return parseResponse(cr);
	}

	/**
	 * This method is for parsing the response of the request.
	 *
	 * @param Response
	 *            of the request
	 */
	public static Responses parseResponse(ClientResponse rs) {
		HashMap<String, Header> resHeaders = new HashMap<String, Header>();
		MultivaluedMap<String, String> rsHeaders = rs.getHeaders();
		for (Entry<String, List<String>> entry : rsHeaders.entrySet()) {
			if (entry.getValue() == null || entry.getValue().isEmpty()) {
			    continue;
			   }
			String headerName = entry.getKey();
			String headerValue = entry.getValue().toString();
			if (headerName.equalsIgnoreCase(SET_COOKIE)) {
				CookieHeader cookieHeader = (CookieHeader) resHeaders.get(SET_COOKIE);

				if (cookieHeader == null) {
					cookieHeader = new CookieHeader();
					cookieHeader.setName(headerName);
				}

				String[] cookieStrs = headerValue.split(";");
				for (String cookieStr : cookieStrs) {
					String[] cookieSepStr = cookieStr.split("=");
					String cookieName = cookieSepStr[0].trim();
					String cookieValue = null;

					if (cookieSepStr.length < 2) {
						cookieValue = "";
					} else {
						cookieValue = cookieSepStr[1].trim();
					}

					cookieHeader.addCookie(new Cookie(cookieName, cookieValue));
				}
				resHeaders.put(SET_COOKIE, cookieHeader);
				continue;
			}
			resHeaders.put(headerName, new Header(headerName, headerValue));
		}
		resHeaders.put("response-code", new Header("response-code", String.valueOf(rs.getStatus())));
		String responseBodyString = rs.getEntity(String.class).trim();
		Body resBody = null;
		if (responseBodyString.startsWith("{")) {
			resBody = new JsonBody(responseBodyString);
		} else if (responseBodyString.startsWith("<")) {
			// resBody = new XmlBody(responseBodyString);
		} else {
			resBody = new Body();
			resBody.setBodyString(responseBodyString);
		}

		Responses response = new Responses(resHeaders, resBody);
		return response;
	}
}
