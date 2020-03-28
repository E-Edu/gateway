package de.themorpheus.edu.gateway.graphql.resolver.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.context.DefaultGraphQLServletContext;

public class HeaderUtil {

	public static final String DEVICE_ID = "device_id";
	public static final String REFRESH_TOKEN = "refresh_token";

	public static final String COOKIE = "cookie";

	public static List<String> findRequestHeader(DataFetchingEnvironment environment, String header) {
		DefaultGraphQLServletContext context = environment.getContext();
		HttpServletRequest request = context.getHttpServletRequest();
		Enumeration<String> headers = request.getHeaders(header);

		List<String> result = new ArrayList<>();
		while (headers.hasMoreElements()) {
			String currentHeader = headers.nextElement();
			result.add(currentHeader);
		}

		return result;
	}

	public static void setHeader(DataFetchingEnvironment environment, String header, String value) {
		DefaultGraphQLServletContext context = environment.getContext();
		HttpServletResponse response = context.getHttpServletResponse();
		response.setHeader(header, value);
	}

	public static void addCookie(DataFetchingEnvironment environment, Cookie cookie) {
		DefaultGraphQLServletContext context = environment.getContext();
		HttpServletResponse response = context.getHttpServletResponse();
		response.addCookie(cookie);
	}

	public static String getCookie(DataFetchingEnvironment environment, String key) {
		List<String> cookiesList = HeaderUtil.findRequestHeader(environment, COOKIE);
		if (cookiesList.isEmpty()) return null;

		for (String cookies : cookiesList) {
			for (String cookie : cookies.split(";")) {
				cookie = cookie.trim();

				if (cookie.startsWith(key) && cookie.contains("="))
					return cookie.split("=")[1];
			}
		}

		return null;
	}

	@RequiredArgsConstructor
	public enum CookieOption {
		MAX_AGE(true, "Max-Age"),
		EXPIRES(true, "Expires"),
		PATH(true, "Path"),
		DOMAIN(true, "Domain"),
		SAME_SITE(true, "SameSite"),
		SECURE(false, "Secure"),
		HTTP_ONLY(false, "HttpOnly");

		@Getter private final boolean valueRequired;
		@Getter private final String value;

	}

}
