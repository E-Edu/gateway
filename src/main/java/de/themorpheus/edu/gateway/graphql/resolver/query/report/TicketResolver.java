package de.themorpheus.edu.gateway.graphql.resolver.query.report;

import de.themorpheus.edu.gateway.graphql.dto.report.TicketDTO;
import de.themorpheus.edu.gateway.graphql.resolver.query.task.TaskResolver;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Min;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class TicketResolver implements GraphQLQueryResolver {

	private static final TicketDTO EXAMPLE = new TicketDTO(
		0,
		"Invalide Aufgabe",
		"Die Aufgabe ist invalide",
		TaskResolver.EXAMPLE,
		TicketDTO.TicketType.INVALID_TASK
	);

	public TicketDTO ticketByTicketId(@Min(0) int ticketId, DataFetchingEnvironment environment) {
		if (EXAMPLE.getTicketId() != ticketId) return null;

		return TicketResolver.EXAMPLE;
	}

}
