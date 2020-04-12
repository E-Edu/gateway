package de.themorpheus.edu.gateway.graphql.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

	@NotBlank private String passwordHash;
	@NotBlank private String email;
	@NotBlank private String firstName;
	@NotBlank private String lastName;

}
