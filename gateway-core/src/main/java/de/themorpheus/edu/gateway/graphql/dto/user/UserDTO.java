package de.themorpheus.edu.gateway.graphql.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	@NotNull
	private UUID userId;
	@NotNull @NotEmpty @NotBlank
	private String firstName;
	@NotNull @NotEmpty @NotBlank
	private String lastName;
	@NotNull @NotEmpty @NotBlank
	private String email;

	@NotNull @NotEmpty @NotBlank
	private String createdAt;
	@Min(0)
	private int role;
	@Min(0)
	private int status;

}
