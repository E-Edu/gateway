package de.themorpheus.edu.gateway.graphql.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDTO {

	@NotNull @NotEmpty @NotBlank
	private String passwordHash;
	@NotNull @NotEmpty @NotBlank
	private String key;

}
