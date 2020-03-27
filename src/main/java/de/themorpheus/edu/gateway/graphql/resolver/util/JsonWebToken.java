package de.themorpheus.edu.gateway.graphql.resolver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JsonWebToken {

	private static final SecureRandom RANDOM = new SecureRandom();

	private static final int RANDOM_SIZE = 128;
	private static final String SECRET = "nrj5m5sui65sdu";
	private static final Algorithm ALGORITHM = Algorithm.HMAC512(SECRET);

	public static String generate(UUID userId) {
		byte[] rand = new byte[RANDOM_SIZE];
		RANDOM.nextBytes(rand);

		return JWT.create()
			.withIssuer("e-edu")
			.withSubject("jwt")
			.withClaim("userId", userId.toString())
			.withClaim("rand", Base64.getEncoder().encodeToString(rand))
			.withIssuedAt(Date.from(Instant.now()))
			.withExpiresAt(Date.from(Instant.now().plus(Duration.ofMinutes(15))))
			.sign(ALGORITHM);
	}

	public static VerificationResult verify(String jwt) {
		DecodedJWT decodedJWT = JWT.decode(jwt);
		if (decodedJWT.getExpiresAt().after(Date.from(Instant.now())))
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
