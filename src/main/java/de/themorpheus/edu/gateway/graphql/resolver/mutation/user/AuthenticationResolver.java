package de.themorpheus.edu.gateway.graphql.resolver.mutation.user;

import de.themorpheus.edu.gateway.graphql.dto.user.JwtRequestDTO;
import de.themorpheus.edu.gateway.graphql.dto.user.JwtResultDTO;
import de.themorpheus.edu.gateway.graphql.dto.user.UserAuthDTO;
import de.themorpheus.edu.gateway.graphql.dto.user.UserAuthResultDTO;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import de.themorpheus.edu.gateway.graphql.resolver.util.HeaderUtil;
import de.themorpheus.edu.gateway.graphql.resolver.util.JsonWebToken;
import de.themorpheus.edu.gateway.graphql.resolver.util.RefreshToken;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AuthenticationResolver implements GraphQLMutationResolver {

	public UserAuthResultDTO authenticate(@Valid UserAuthDTO userAuth, DataFetchingEnvironment environment) {
		Optional<String> optionalDeviceId = HeaderUtil.findHeader(environment, HeaderUtil.DEVICE_ID);

		deviceId: {
			HeaderUtil.setCookie(
				environment,
				Map.of(
					HeaderUtil.DEVICE_ID, optionalDeviceId.orElse("EXAMPLE_DEVICE_COOKIE"),
					//HeaderUtil.CookieOption.SAME_SITE.getValue(), "Strict" //TODO: Activate
					HeaderUtil.CookieOption.SAME_SITE.getValue(), "None"
				),
				HeaderUtil.CookieOption.SECURE,
				HeaderUtil.CookieOption.HTTP_ONLY
			);
		}

		//TODO: Backend authentication
		UserAuthResultDTO.UserAuthResultType resultType = UserAuthResultDTO.UserAuthResultType.SUCCESS;
		UUID userId = UUID.randomUUID();

		if (resultType == UserAuthResultDTO.UserAuthResultType.SUCCESS) {
			refreshToken: {
				HeaderUtil.setCookie(
					environment,
					Map.of(
						HeaderUtil.REFRESH_TOKEN, RefreshToken.generate(userId),
						HeaderUtil.CookieOption.SAME_SITE.getValue(), "None" //TODO: Strict
					),
					HeaderUtil.CookieOption.SECURE,
					HeaderUtil.CookieOption.HTTP_ONLY
				);
			}
		}

		return new UserAuthResultDTO(resultType);
	}

	public JwtResultDTO jwt(@Valid JwtRequestDTO requestDTO, DataFetchingEnvironment environment) {
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
