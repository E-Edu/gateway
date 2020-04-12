package de.themorpheus.edu.gateway.backend;

import de.themorpheus.edu.gateway.GatewayApplication;
import de.themorpheus.edu.gateway.backend.user.UserCreateDTO;
import de.themorpheus.edu.gateway.backend.user.UserErrorResponseDTO;
import de.themorpheus.edu.gateway.backend.user.UserLoginRequestDTO;
import de.themorpheus.edu.gateway.backend.user.UserLoginResponseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;

public class UserService extends Service {

	public UserService() {
		super("https://user.e-edu.the-morpheus.de");
	}

	public RegisterResponse createUser(
		@NotNull String email,
		@NotNull  String password,
		@NotNull  String firstName,
		@NotNull  String lastName,
		@Nullable String teacherToken) throws IOException {

		Request request = request("/user")
			.post(body(new UserCreateDTO(
				email,
				password,
				firstName,
				lastName,
				teacherToken
			)))
			.build();
		try (Response response = getClient().newCall(request).execute()) {
			if (response.code() != 201) return response(RegisterResponse.class, response.body().string());
			return new RegisterResponse(null);
		}
	}

	public LoginResponse login(String email, String password) throws IOException {
		Request request = request("/user/login")
			.post(body(new UserLoginRequestDTO(email, password))).build();
		try (Response response = getClient().newCall(request).execute()) {

			if (response.code() != 200) {
				UserErrorResponseDTO errorResponseDTO = response(UserErrorResponseDTO.class, response.body().string());
				return new LoginResponse(null, errorResponseDTO.getError());
			}

			UserLoginResponseDTO responseDTO = response(UserLoginResponseDTO.class, response.body().string());
			return new LoginResponse(responseDTO.getSession(), null);
		}
	}

	@Getter
	@RequiredArgsConstructor
	public static class LoginResponse {
		private final String session;
		private final String error;
	}

	@Getter
	@RequiredArgsConstructor
	public static class RegisterResponse {
		private final String error;
	}

}
