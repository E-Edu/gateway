package de.themorpheus.edu.gateway.graphql.resolver.query.task;

import de.themorpheus.edu.gateway.graphql.dto.task.SubjectDTO;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Min;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class SubjectResolver implements GraphQLQueryResolver {

	public SubjectDTO subjectById(@Min(0) int subjectId, DataFetchingEnvironment environment) {
		return TaskResolver.EXAMPLE.getLecture().getModule().getSubject();
	}

}
