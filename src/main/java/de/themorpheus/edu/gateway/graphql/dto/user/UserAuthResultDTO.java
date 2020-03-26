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
public class UserAuthResultDTO {

	@NotNull
	private UserAuthResultType result;

	public enum UserAuthResultType {
		SUCCESS,
		USER_NOT_FOUND,
		AUTHENTICATION_FAILED,
		MAINTENANCE
	}

}
