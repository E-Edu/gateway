package de.themorpheus.edu.gateway.graphql.resolver.query.task;

import de.themorpheus.edu.gateway.graphql.dto.task.DifficultyDTO;
import de.themorpheus.edu.gateway.graphql.dto.task.LectureDTO;
import de.themorpheus.edu.gateway.graphql.dto.task.ModuleDTO;
import de.themorpheus.edu.gateway.graphql.dto.task.SubjectDTO;
import de.themorpheus.edu.gateway.graphql.dto.task.TaskDTO;
import de.themorpheus.edu.gateway.graphql.dto.task.TaskTypeDTO;
import de.themorpheus.edu.gateway.graphql.resolver.query.user.UserResolver;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Min;
import java.util.UUID;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class TaskResolver implements GraphQLQueryResolver {

	public static final TaskDTO EXAMPLE = new TaskDTO(
		0,
		"Create something",
		UserResolver.EXAMPLE,
		10,
		new TaskTypeDTO(
			0,
			"Exercise"
		),
		new LectureDTO(
			0,
			"Integralrechnung",
			new ModuleDTO(
				0,
				"Analysis",
				new SubjectDTO(
					0,
					"Math"
				)
			)
		),
		new DifficultyDTO(
			0,
			"Hard"
		)
	);

	public TaskDTO taskById(@Min(0) int taskId, DataFetchingEnvironment environment) {
		return TaskResolver.EXAMPLE;
	}

}
