package de.themorpheus.edu.gateway.graphql.resolver.query.task;

import de.themorpheus.edu.gateway.graphql.dto.task.ModuleDTO;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Min;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class ModuleResolver implements GraphQLQueryResolver {

	public ModuleDTO moduleById(@Min(0) int moduleId, DataFetchingEnvironment environment) {
		return TaskResolver.EXAMPLE.getLecture().getModule();
	}

}
