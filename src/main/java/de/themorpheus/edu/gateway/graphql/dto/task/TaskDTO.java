package de.themorpheus.edu.gateway.graphql.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

	@Min(0)
	private int taskId;
	@NotNull @NotEmpty @NotBlank
	private String task;
	@NotNull
	private UUID authorId;
	@Min(0)
	private int necessaryPoints;
	private TaskTypeDTO taskType;
	private LectureDTO lecture;
	private DifficultyDTO difficulty;

}
