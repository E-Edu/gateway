package de.themorpheus.edu.gateway.graphql.resolver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class RefreshToken {

	private static final SecureRandom RANDOM = new SecureRandom();

	private static final int RANDOM_SIZE = 128;
	private static final String SECRET = "mvpowrngiow34ng0";
	private static final Algorithm ALGORITHM = Algorithm.HMAC512(SECRET);

	public static String generate(UUID userId) {
		byte[] nonce = new byte[RANDOM_SIZE];
		RANDOM.nextBytes(nonce);

		Calendar expirationDate = Calendar.getInstance();
		expirationDate.setTimeInMillis(System.currentTimeMillis());
		expirationDate.add(Calendar.YEAR, 1);

		return JWT.create()
			.withIssuer("e-edu")
			.withSubject("refresh_token")
			.withClaim("userId", userId.toString())
			.withClaim("nonce", Base64.getEncoder().encodeToString(nonce))
			.withIssuedAt(Date.from(Instant.now()))
			.withExpiresAt(expirationDate.getTime())
			.sign(ALGORITHM);
	}

	public static VerificationResult verify(String jwt) {
		DecodedJWT decodedJWT;

		try {
			decodedJWT = JWT.decode(jwt);
		} catch (JWTDecodeException ignored) {
			return new VerificationResult(VerificationStatus.MALFORMED, null);
		}

		if (decodedJWT.getExpiresAt().before(Date.from(Instant.now())))
			return new VerificationResult(VerificationStatus.EXPIRED, null);

		try {
			ALGORITHM.verify(decodedJWT);
		} catch (SignatureVerificationException ignored) {
			return new VerificationResult(VerificationStatus.INVALID, null);
		}

		UUID userId;
		try {
			userId = UUID.fromString(decodedJWT.getClaim("userId").asString());
		} catch (IllegalArgumentException ignored) {
			return new VerificationResult(VerificationStatus.MALFORMED, null);
		}

		return new VerificationResult(VerificationStatus.VERIFIED, userId);
	}

	@Data
	@RequiredArgsConstructor
	public static class VerificationResult {

		private final VerificationStatus status;
		private final UUID userId;

	}

	public enum VerificationStatus {
		VERIFIED, INVALID, EXPIRED, MALFORMED
	}

}
