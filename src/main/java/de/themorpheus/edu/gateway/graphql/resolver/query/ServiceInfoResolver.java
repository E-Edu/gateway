package de.themorpheus.edu.gateway.graphql.resolver.query;

import de.themorpheus.edu.gateway.graphql.dto.generic.ServiceInfoDTO;
import com.jcabi.manifests.Manifests;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class ServiceInfoResolver implements GraphQLQueryResolver {

	private static final ServiceInfoDTO GATEWAY = new ServiceInfoDTO(
		"Gateway",
		ServiceInfoResolver.class.getPackage().getImplementationVersion(),
		Manifests.read("Commit-Id"),
		Long.parseLong(Manifests.read("Build-Time"))
	);

	public ServiceInfoDTO serviceInfoByName(@NotNull @NotEmpty @NotBlank String name, DataFetchingEnvironment environment) {
		return GATEWAY;
	}

}
