package de.themorpheus.edu.gateway.graphql.resolver.mutation.user;

import de.themorpheus.edu.gateway.graphql.dto.user.UserAuthDTO;
import de.themorpheus.edu.gateway.graphql.dto.user.UserAuthResultDTO;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AuthenticationResolver implements GraphQLMutationResolver {

	public UserAuthResultDTO authenticate(@Valid UserAuthDTO userAuth, DataFetchingEnvironment environment) {
		return new UserAuthResultDTO(UserAuthResultDTO.UserAuthResultType.SUCCESS);
	}

}
