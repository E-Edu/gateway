package de.themorpheus.edu.gateway.graphql.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResultDTO {

	@NotNull
	private UserAuthResultType result;

	public enum UserAuthResultType {
		SUCCESS,
		KEY_ALREADY_REGISTERED,
		REGISTRATION_FAILED,
		MAINTENANCE
	}

}
