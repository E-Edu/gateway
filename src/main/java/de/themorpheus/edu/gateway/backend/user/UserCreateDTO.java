package de.themorpheus.edu.gateway.backend.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserCreateDTO {

	private final String email;
	private final String password;
	private final String firstName;
	private final String lastName;
	private final String teacherToken;

}
