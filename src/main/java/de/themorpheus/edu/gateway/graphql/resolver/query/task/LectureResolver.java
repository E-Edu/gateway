package de.themorpheus.edu.gateway.graphql.resolver.query.task;

import de.themorpheus.edu.gateway.graphql.dto.task.LectureDTO;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Min;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class LectureResolver implements GraphQLQueryResolver {

	public LectureDTO lectureById(@Min(0) int lectureId, DataFetchingEnvironment environment) {
		return TaskResolver.EXAMPLE.getLecture();
	}

}
