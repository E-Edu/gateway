package de.themorpheus.edu.gateway.graphql.resolver.query.user;

import de.themorpheus.edu.gateway.graphql.dto.user.UserDTO;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class UserResolver implements GraphQLQueryResolver {

	public static final UserDTO EXAMPLE = new UserDTO(
		UUID.randomUUID(),
		"Max",
		"Mustermann",
		"max@mustermann.example",
		String.valueOf(System.currentTimeMillis()),
		2, // Admin
		0
	);

	public UserDTO userByUserId(@NotNull UUID userId, DataFetchingEnvironment environment) {
		if (!EXAMPLE.getUserId().equals(userId)) return null;

		return EXAMPLE;
	}

	public UserDTO userByUserName(@NotNull @NotEmpty @NotBlank String firstName,
								  @NotNull @NotEmpty @NotBlank String lastName,
								  DataFetchingEnvironment environment) {
		if (!EXAMPLE.getFirstName().equalsIgnoreCase(firstName)) return null;
		if (!EXAMPLE.getLastName().equalsIgnoreCase(lastName)) return null;

		return EXAMPLE;
	}

	public UserDTO userByEmail(@NotNull @NotEmpty @NotBlank String email, DataFetchingEnvironment environment) {
		if (!EXAMPLE.getEmail().equals(email)) return null;

		return EXAMPLE;
	}

}
