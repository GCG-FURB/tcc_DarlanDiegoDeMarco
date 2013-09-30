package br.com.furb.tagarela.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

public class JsonUtils {
	private static final String URL_CATEGORIES = "http://murmuring-falls-7702.herokuapp.com/categories/";
	private static final String URL_USERS = "http://murmuring-falls-7702.herokuapp.com/users/";

	public static String validaJson(String results) {
		if (results.startsWith("{")) {
			return "[" + results + "]";
		}
		return results;
	}

	public static String getUserJsonResponse(int user) {
		HttpGet httpGet = new HttpGet(URL_USERS + "/" + user);
		httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader("Content-Type", "application/json");

		String json = null;
		try {
			HttpResponse response = HttpUtils.doRequest(httpGet);
			json = HttpUtils.getContent(response);
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}
		return json;
	}

	public static String getCategoriesResponse() {
		HttpGet httpGet = new HttpGet(URL_CATEGORIES);
		httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader("Content-Type", "application/json");

		String json = null;
		try {
			HttpResponse response = HttpUtils.doRequest(httpGet);
			json = HttpUtils.getContent(response);
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}
		return json;

	}

}
