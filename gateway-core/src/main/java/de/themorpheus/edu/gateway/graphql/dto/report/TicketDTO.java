package de.themorpheus.edu.gateway.graphql.dto.report;

import de.themorpheus.edu.gateway.graphql.dto.task.TaskDTO;
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
public class TicketDTO {

	@Min(0)
	private int ticketId;
	@NotNull @NotEmpty @NotBlank
	private String title;
	@NotNull @NotEmpty @NotBlank
	private String description;
	@NotNull
	private TaskDTO task;
	@NotNull
	private TicketType ticketType;

	public enum TicketType {
		NONE
	}

}
