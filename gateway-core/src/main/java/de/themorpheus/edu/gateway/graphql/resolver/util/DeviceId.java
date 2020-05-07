package de.themorpheus.edu.gateway.graphql.resolver.util;

import de.themorpheus.edu.gateway.GatewayApplication;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import javax.servlet.http.Cookie;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import graphql.schema.DataFetchingEnvironment;

public class DeviceId {

	private static final SecureRandom RANDOM = new SecureRandom();

	private static final int RANDOM_SIZE = 128;
	private static final String SECRET = "nrj5m5sui65sdu"; //TODO
	private static final Algorithm ALGORITHM = Algorithm.HMAC512(SECRET);

	public static DecodedJWT get(DataFetchingEnvironment environment) {
		String cookie = HeaderUtil.getCookie(environment, HeaderUtil.DEVICE_ID);
		if (cookie == null) return null;
		return JWT.decode(cookie);
	}

	public static DecodedJWT getOrSetIfNotExists(DataFetchingEnvironment environment, UUID userId) {
		DecodedJWT deviceId = get(environment);
		if (deviceId != null) return deviceId;

		return set(environment, userId);
	}

	public static DecodedJWT set(DataFetchingEnvironment environment, UUID userId) {
		byte[] nonce = new byte[RANDOM_SIZE];
		RANDOM.nextBytes(nonce);

		String deviceId = JWT.create()
			.withIssuer("e-edu")
			.withSubject("deviceId")
			.withClaim("userId", userId.toString())
			.withClaim("userAgent", HeaderUtil.findRequestHeader(environment, "user-agent"))
			.withClaim("nonce", Base64.getEncoder().encodeToString(nonce))
			.withIssuedAt(Date.from(Instant.now()))
			.withExpiresAt(Date.from(Instant.now().plus(Duration.ofDays(365))))
			.sign(ALGORITHM);

		Cookie cookie = new Cookie(HeaderUtil.DEVICE_ID, deviceId);
		cookie.setHttpOnly(true);
		cookie.setMaxAge((int) Duration.ofDays(365).toSeconds());
		if (GatewayApplication.PRODUCTIVE) cookie.setSecure(true);

		HeaderUtil.addCookie(environment, cookie);
		System.out.println("add device cookie");

		return JWT.decode(deviceId);
	}

}
