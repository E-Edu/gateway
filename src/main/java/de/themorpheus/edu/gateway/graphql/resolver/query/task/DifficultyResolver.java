package de.themorpheus.edu.gateway.graphql.resolver.query.task;

import de.themorpheus.edu.gateway.graphql.dto.task.DifficultyDTO;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Min;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class DifficultyResolver implements GraphQLQueryResolver {

	public DifficultyDTO difficultyById(@Min(0) int difficultyId, DataFetchingEnvironment environment) {
		return TaskResolver.EXAMPLE.getDifficulty();
	}

}
