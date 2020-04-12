package de.themorpheus.edu.gateway.backend;

import lombok.Getter;

public class BackendAPI {

	@Getter private final UserService userService = new UserService();

}
