package de.themorpheus.edu.gateway.graphql.resolver.query.task;

import de.themorpheus.edu.backend.api.TaskApi;
import de.themorpheus.edu.backend.invoker.ApiException;
import de.themorpheus.edu.backend.model.Task;
import de.themorpheus.edu.gateway.graphql.dto.task.DifficultyDTO;
import de.themorpheus.edu.gateway.graphql.dto.task.LectureDTO;
import de.themorpheus.edu.gateway.graphql.dto.task.ModuleDTO;
import de.themorpheus.edu.gateway.graphql.dto.task.SubjectDTO;
import de.themorpheus.edu.gateway.graphql.dto.task.TaskDTO;
import de.themorpheus.edu.gateway.graphql.dto.task.TaskTypeDTO;
import de.themorpheus.edu.gateway.graphql.resolver.query.user.UserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Min;
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

	@Autowired private TaskApi taskApi;
	@Autowired private TaskTypeResolver taskTypeResolver;
	@Autowired private LectureResolver lectureResolver;
	@Autowired private DifficultyResolver difficultyResolver;


	public TaskDTO taskById(@Min(0) int taskId, DataFetchingEnvironment environment) throws ApiException {
		Task task = taskApi.getTask("eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1ODk0ODE3NDEsImV4cCI6MTYwOTQ1NTYwMCwibm9uY2UiOiJuaWNlbGl0dGxlcmFuZG9tc3RyaW5nIiwiYXVkIjpbImdld2lhLmNvbSJdLCJzY29wZXMiOlsidGFzay50YXNrLnJlYWQuYWxsIiwidGFzay50YXNrLndyaXRlLmFsbCIsInRhc2sudGFzay5kZWxldGUuYWxsIiwidGFzay50YXNrZ3JvdXAucmVhZC5hbGwiLCJ0YXNrLnRhc2tncm91cC53cml0ZS5hbGwiLCJ0YXNrLnRhc2tncm91cC5kZWxldGUuYWxsIiwidGFzay5zdWJqZWN0LnJlYWQuYWxsIiwidGFzay5zdWJqZWN0LndyaXRlLmFsbCIsInRhc2suc3ViamVjdC5kZWxldGUuYWxsIiwidGFzay5tb2R1bGUucmVhZC5hbGwiLCJ0YXNrLm1vZHVsZS53cml0ZS5hbGwiLCJ0YXNrLm1vZHVsZS5kZWxldGUuYWxsIiwidGFzay5sZWN0dXJlLnJlYWQuYWxsIiwidGFzay5sZWN0dXJlLndyaXRlLmFsbCIsInRhc2subGVjdHVyZS5kZWxldGUuYWxsIiwidGFzay5kaWZmaWN1bHR5LnJlYWQuYWxsIiwidGFzay5kaWZmaWN1bHR5LndyaXRlLmFsbCIsInRhc2suZGlmZmljdWx0eS5kZWxldGUuYWxsIiwidGFzay50YXNrdHlwZS5yZWFkLmFsbCIsInRhc2sudGFza3R5cGUud3JpdGUuYWxsIiwidGFzay50YXNrdHlwZS5kZWxldGUuYWxsIiwidGFzay51c2VyLnJlYWQuYWxsIiwidGFzay51c2VyLmRlbGV0ZS5hbGwiLCJ0YXNrLnNvbHV0aW9uLm11bHRpcGxlY2hvaWNlLnJlYWQuYWxsIiwidGFzay5zb2x1dGlvbi5tdWx0aXBsZWNob2ljZS53cml0ZS5hbGwiLCJ0YXNrLnNvbHV0aW9uLm11bHRpcGxlY2hvaWNlLmRlbGV0ZS5hbGwiLCJ0YXNrLnNvbHV0aW9uLmFuYWdyYW0ucmVhZC5hbGwiLCJ0YXNrLnNvbHV0aW9uLmFuYWdyYW0ud3JpdGUuYWxsIiwidGFzay5zb2x1dGlvbi5hbmFncmFtLmRlbGV0ZS5hbGwiLCJ0YXNrLnNvbHV0aW9uLmZyZWVzdHlsZS5yZWFkLmFsbCIsInRhc2suc29sdXRpb24uZnJlZXN0eWxlLndyaXRlLmFsbCIsInRhc2suc29sdXRpb24uZnJlZXN0eWxlLmRlbGV0ZS5hbGwiLCJ0YXNrLnNvbHV0aW9uLmltYWdlLnJlYWQuYWxsIiwidGFzay5zb2x1dGlvbi5pbWFnZS53cml0ZS5hbGwiLCJ0YXNrLnNvbHV0aW9uLmltYWdlLmRlbGV0ZS5hbGwiLCJ0YXNrLnNvbHV0aW9uLnRvcGljY29ubmVjdGlvbi5yZWFkLmFsbCIsInRhc2suc29sdXRpb24udG9waWNjb25uZWN0aW9uLndyaXRlLmFsbCIsInRhc2suc29sdXRpb24udG9waWNjb25uZWN0aW9uLmRlbGV0ZS5hbGwiLCJ0YXNrLnNvbHV0aW9uLnNpbXBsZWVxdWF0aW9uLnJlYWQuYWxsIiwidGFzay5zb2x1dGlvbi5zaW1wbGVlcXVhdGlvbi53cml0ZS5hbGwiLCJ0YXNrLnNvbHV0aW9uLnNpbXBsZWVxdWF0aW9uLmRlbGV0ZS5hbGwiXX0.m3MuV-N7lkYn3SSamesEZ1o6FMrrMNQBXMv8JneSuoXu-5KgN2p7VMZJlvXN0_toKQQ1wixlGh-DxSO6pEfU_w", "SECRET", taskId);
		return new TaskDTO(
				task.getTaskId(),
				task.getTask(),
				UserResolver.EXAMPLE,
				task.getNecessaryPoints(),
				taskTypeResolver.taskTypeById(task.getTaskTypeId().getTaskTypeId(), environment),
				lectureResolver.lectureById(task.getLecture().getLectureId(), environment),
				difficultyResolver.difficultyById(task.getDifficultyId().getDifficultyId(), environment)
		);
	}

}
