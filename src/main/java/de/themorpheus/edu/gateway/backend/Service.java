package de.themorpheus.edu.gateway.backend;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

@RequiredArgsConstructor
public abstract class Service {

	private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
	protected static final Gson GSON = new GsonBuilder()
		.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
		.create();

	private final String uri;

	@Getter(AccessLevel.PROTECTED)
	private final OkHttpClient client = new OkHttpClient();

	protected Request.Builder request(String route) {
		return new Request.Builder()
			.url(uri + route);
	}

	protected RequestBody body(Object obj) {
		return RequestBody.create(GSON.toJson(obj), JSON);
	}

	protected <T> T response(Class<T> clazz, String json) {
		return GSON.fromJson(json, clazz);
	}

}
