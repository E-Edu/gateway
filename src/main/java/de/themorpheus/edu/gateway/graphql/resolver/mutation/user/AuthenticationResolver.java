package de.themorpheus.edu.gateway.graphql.resolver.mutation.user;

import de.themorpheus.edu.gateway.graphql.dto.user.JwtResultDTO;
import de.themorpheus.edu.gateway.graphql.dto.user.UserAuthDTO;
import de.themorpheus.edu.gateway.graphql.dto.user.UserAuthResultDTO;
import org.springframework.stereotype.Component;
import javax.servlet.http.Cookie;
import javax.validation.Valid;
import de.themorpheus.edu.gateway.graphql.resolver.util.HeaderUtil;
import de.themorpheus.edu.gateway.graphql.resolver.util.JsonWebToken;
import de.themorpheus.edu.gateway.graphql.resolver.util.RefreshToken;
import java.time.Duration;
import java.util.UUID;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AuthenticationResolver implements GraphQLMutationResolver {

	public UserAuthResultDTO authenticate(@Valid UserAuthDTO userAuth, DataFetchingEnvironment environment) {
		String deviceId = HeaderUtil.getCookie(environment, HeaderUtil.DEVICE_ID);

		deviceId: {
			Cookie cookie = new Cookie(HeaderUtil.DEVICE_ID, deviceId == null ? "EXAMPLE_DEVICE_COOKIE" : deviceId);
			cookie.setHttpOnly(true);
			cookie.setMaxAge((int) Duration.ofDays(365).toSeconds());
			//cookie.setSecure(true); //TODO: Only in productive
			HeaderUtil.addCookie(environment, cookie);
		}

		//TODO: Backend authentication
		UserAuthResultDTO.UserAuthResultType resultType = UserAuthResultDTO.UserAuthResultType.SUCCESS;
		UUID userId = UUID.randomUUID();

		if (resultType == UserAuthResultDTO.UserAuthResultType.SUCCESS) {
			refreshToken: {
				Cookie cookie = new Cookie(HeaderUtil.REFRESH_TOKEN, RefreshToken.generate(userId));
				cookie.setHttpOnly(true);
				cookie.setMaxAge((int) Duration.ofDays(365).toSeconds());
				//cookie.setSecure(true); //TODO: Only in productive

				HeaderUtil.addCookie(environment, cookie);
				//TODO: Same-Site?
			}
		}

		return new UserAuthResultDTO(resultType);
	}

	public JwtResultDTO jwt(DataFetchingEnvironment environment) {
		// Get cookie
		String refreshTokenCookie = HeaderUtil.getCookie(environment, HeaderUtil.REFRESH_TOKEN);
		if (refreshTokenCookie == null) return new JwtResultDTO(null, JwtResultDTO.JwtStatus.INVALID); //TODO: 403 Forbidden

		// Verify refresh token
		RefreshToken.VerificationResult result = RefreshToken.verify(refreshTokenCookie);

		// Malformed or invalid
		if (result.getStatus() == RefreshToken.VerificationStatus.INVALID ||
		result.getStatus() == RefreshToken.VerificationStatus.MALFORMED
		) return new JwtResultDTO(null, JwtResultDTO.JwtStatus.INVALID); //TODO: 403 Forbidden

		// Expired
		if (result.getStatus() == RefreshToken.VerificationStatus.EXPIRED)
			return new JwtResultDTO(null, JwtResultDTO.JwtStatus.EXPIRED);

		UUID userId = result.getUserId();

		// Create jwt
		String jwt = JsonWebToken.generate(userId);
		return new JwtResultDTO(jwt, JwtResultDTO.JwtStatus.VERIFIED);
	}

}
