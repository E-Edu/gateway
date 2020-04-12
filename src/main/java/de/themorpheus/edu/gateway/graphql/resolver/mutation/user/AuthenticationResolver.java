package de.themorpheus.edu.gateway.graphql.resolver.mutation.user;

import de.themorpheus.edu.gateway.GatewayApplication;
import de.themorpheus.edu.gateway.backend.UserService;
import de.themorpheus.edu.gateway.graphql.dto.user.JwtResultDTO;
import de.themorpheus.edu.gateway.graphql.dto.user.UserAuthDTO;
import de.themorpheus.edu.gateway.graphql.dto.user.UserAuthResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.http.Cookie;
import javax.validation.Valid;
import de.themorpheus.edu.gateway.graphql.dto.user.UserRegisterDTO;
import de.themorpheus.edu.gateway.graphql.dto.user.UserRegisterResultDTO;
import de.themorpheus.edu.gateway.graphql.resolver.util.DeviceId;
import de.themorpheus.edu.gateway.graphql.resolver.util.HeaderUtil;
import de.themorpheus.edu.gateway.graphql.resolver.util.JsonWebToken;
import de.themorpheus.edu.gateway.graphql.resolver.util.RefreshToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.Duration;
import java.util.UUID;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AuthenticationResolver implements GraphQLMutationResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationResolver.class.getSimpleName());

	@SuppressWarnings("checkstyle:IllegalCatch")
	public UserRegisterResultDTO register(@Valid UserRegisterDTO userRegisterDTO, DataFetchingEnvironment environment) {
		UserRegisterResultDTO.UserAuthResultType resultType;

		try {
			UserService.RegisterResponse registerResponse = GatewayApplication.getBackendAPI().getUserService().createUser(
				userRegisterDTO.getEmail(),
				userRegisterDTO.getPasswordHash(),
				userRegisterDTO.getFirstName(),
				userRegisterDTO.getLastName(),
				null
			);

			if (registerResponse.getError() == null)
				resultType = UserRegisterResultDTO.UserAuthResultType.SUCCESS;
			else if ("user already exist".equals(registerResponse.getError()))
				resultType = UserRegisterResultDTO.UserAuthResultType.KEY_ALREADY_REGISTERED;
			else
				resultType = UserRegisterResultDTO.UserAuthResultType.REGISTRATION_FAILED;

		} catch (Exception ex) {
			LOGGER.error("Error while register", ex);
			resultType = UserRegisterResultDTO.UserAuthResultType.REGISTRATION_FAILED;
		}

		return new UserRegisterResultDTO(resultType);
	}

	@SuppressWarnings("checkstyle:IllegalCatch")
	public UserAuthResultDTO authenticate(@Valid UserAuthDTO userAuth, DataFetchingEnvironment environment) {
		DecodedJWT requestDeviceId = DeviceId.get(environment);
		//TODO: Use for protection

		UserAuthResultDTO.UserAuthResultType resultType;
		UUID userId = null;

		backendRequest: try {
			UserService.LoginResponse loginResponse = GatewayApplication.getBackendAPI().getUserService().login(userAuth.getKey(), userAuth.getPasswordHash());
			String sessionToken = loginResponse.getSession();
			String error = loginResponse.getError();

			if (sessionToken == null) {
				//TODO: Error handling

				resultType = UserAuthResultDTO.UserAuthResultType.AUTHENTICATION_FAILED;
				break backendRequest;
			}

			DecodedJWT token = JWT.decode(sessionToken);
			userId = UUID.fromString(token.getClaim("uuid").asString());

			resultType = UserAuthResultDTO.UserAuthResultType.SUCCESS;
		} catch (Exception ex) {
			LOGGER.error("Error while authentication", ex);
			resultType = UserAuthResultDTO.UserAuthResultType.AUTHENTICATION_FAILED;
		}

		if (resultType == UserAuthResultDTO.UserAuthResultType.SUCCESS) {
			refreshToken: {
				Cookie cookie = new Cookie(HeaderUtil.REFRESH_TOKEN, RefreshToken.generate(userId));
				cookie.setHttpOnly(true);
				cookie.setMaxAge((int) Duration.ofDays(365).toSeconds());
				if (GatewayApplication.PRODUCTIVE) cookie.setSecure(true);

				HeaderUtil.addCookie(environment, cookie);
				//TODO: Same-Site?
			}

			if (requestDeviceId == null || !requestDeviceId.getClaim("userId").asString().equals(userId.toString()))
				DeviceId.set(environment, userId);
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
