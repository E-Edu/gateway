package de.themorpheus.edu.gateway.graphql.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTypeDTO {

	@Min(0)
	private int taskTypeId;
	@NotNull @NotEmpty @NotBlank
	private String nameKey;

}
