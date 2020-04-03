package de.themorpheus.edu.gateway.graphql.resolver.query;

import de.themorpheus.edu.gateway.graphql.dto.generic.ServiceInfoDTO;
import de.themorpheus.edu.gateway.util.GitInfo;
import com.jcabi.manifests.Manifests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class ServiceInfoResolver implements GraphQLQueryResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceInfoResolver.class.getSimpleName());

	private static ServiceInfoDTO GATEWAY;

	static {
		try {
			GitInfo info = GitInfo.load();
			GATEWAY = new ServiceInfoDTO(
				"Gateway",
				ServiceInfoResolver.class.getPackage().getImplementationVersion(),
				info.getCommitId(),
				Long.parseLong(Manifests.read("Build-Time"))
			);
		} catch (IOException ex) {
			LOGGER.error("Error while initializing service info using git properties", ex);
			GATEWAY = new ServiceInfoDTO(
				"Gateway",
				ServiceInfoResolver.class.getPackage().getImplementationVersion(),
				"",
				-1
			);
		}
	}

	public ServiceInfoDTO serviceInfoByName(@NotNull @NotEmpty @NotBlank String name, DataFetchingEnvironment environment) {
		return GATEWAY;
	}

}
