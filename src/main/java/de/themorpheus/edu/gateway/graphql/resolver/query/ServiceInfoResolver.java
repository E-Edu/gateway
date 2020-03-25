package de.themorpheus.edu.gateway.graphql.resolver.query;

import de.themorpheus.edu.gateway.graphql.dto.ServiceInfoDTO;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Component
public class ServiceInfoResolver implements GraphQLQueryResolver {

	public ServiceInfoDTO serviceByName(@NotNull @NotEmpty @NotBlank String name, DataFetchingEnvironment environment) {
		return new ServiceInfoDTO(
			name,
			"1.0",
			"0a49608",
			1234567
		);
	}

}
