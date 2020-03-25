package de.themorpheus.edu.gateway.graphql.resolver.query.task;

import de.themorpheus.edu.gateway.graphql.dto.task.TaskTypeDTO;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Min;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class TaskTypeResolver implements GraphQLQueryResolver {

	public TaskTypeDTO taskTypeById(@Min(0) int taskTypeId, DataFetchingEnvironment environment) {
		return TaskResolver.EXAMPLE.getTaskType();
	}

}
