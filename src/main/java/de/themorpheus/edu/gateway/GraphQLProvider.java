package de.themorpheus.edu.gateway;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import de.themorpheus.edu.gateway.fetcher.ServiceDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.io.IOException;
import java.net.URL;
import javax.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {

	private GraphQL graphQL;

	@Autowired private ServiceDataFetcher serviceDataFetcher;

	@Bean
	public GraphQL graphQL() {
		return this.graphQL;
	}

	@PostConstruct
	public void init() throws IOException {
		this.graphQL = GraphQL.newGraphQL(buildSchema()).build();
	}

	@Bean
	@SneakyThrows
	public GraphQLSchema buildSchema() {
		URL url = Resources.getResource("schema.graphqls");
		String sdl = Resources.toString(url, Charsets.UTF_8);

		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
		RuntimeWiring runtimeWiring = buildWiring();
		SchemaGenerator schemaGenerator = new SchemaGenerator();
		return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
	}

	private RuntimeWiring buildWiring() {
		return RuntimeWiring.newRuntimeWiring()
			.type(newTypeWiring("Query")
				.dataFetcher("serviceByName", serviceDataFetcher.getServiceByName()))
			.build();
	}
}
