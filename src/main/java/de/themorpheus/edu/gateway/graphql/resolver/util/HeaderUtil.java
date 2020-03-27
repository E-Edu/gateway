package de.themorpheus.edu.gateway.graphql.resolver.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;
import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.context.DefaultGraphQLServletContext;

public class HeaderUtil {

	public static final String DEVICE_ID = "device_id";
	public static final String REFRESH_TOKEN = "refresh_token";

	public static final String SET_COOKIE = "set-cookie";
	public static final String COOKIE = "cookie";

	public static Optional<String> findHeader(DataFetchingEnvironment environment, String header) {
		DefaultGraphQLServletContext context = environment.getContext();
		HttpServletRequest request = context.getHttpServletRequest();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String currentHeader = headerNames.nextElement();
			if (currentHeader.equalsIgnoreCase(header)) return Optional.of(request.getHeader(currentHeader));
		}

		return Optional.empty();
	}

	public static void setHeader(DataFetchingEnvironment environment, String header, String value) {
		DefaultGraphQLServletContext context = environment.getContext();
		HttpServletResponse response = context.getHttpServletResponse();
		response.setHeader(header, value);
	}

	public static void setCookie(DataFetchingEnvironment environment, Map<String, String> cookies, CookieOption... options) {
		DefaultGraphQLServletContext context = environment.getContext();
		HttpServletResponse response = context.getHttpServletResponse();
		StringBuilder value = new StringBuilder();
		cookies.forEach((cookieKey, cookieValue) -> {
			value.append(cookieKey);
			value.append('=');
			value.append(cookieValue);
			value.append(';');
		});

		for (CookieOption option : options) {
			if (option.isValueRequired()) throw new IllegalArgumentException("Consider passing this option via 'cookies': " + option);
			value.append(option.getValue());
			value.append(';');
		}

		response.setHeader(SET_COOKIE, value.toString());
	}

	public static String getCookie(DataFetchingEnvironment environment, String key) {
		Optional<String> cookieOptional = HeaderUtil.findHeader(environment, COOKIE);
		if (!cookieOptional.isPresent()) return null;

		String[] parts = cookieOptional.get().split(";");

		for (String part : parts)
			if (part.startsWith(key) && part.contains("="))
				return part.split("=")[1];

		return null;
	}

	@RequiredArgsConstructor
	public enum CookieOption {
		MAX_AGE(true,"Max-Age"),
		EXPIRES(true,"Expires"),
		PATH(true,"Path"),
		DOMAIN(true,"Domain"),
		SAME_SITE(true,"SameSite"),
		SECURE(false,"Secure"),
		HTTP_ONLY(false,"HttpOnly");

		@Getter private final boolean valueRequired;
		@Getter private final String value;

	}

}
