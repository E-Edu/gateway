package de.themorpheus.edu.gateway.backend.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserLoginRequestDTO {

	private final String email;
	private final String password;

}
