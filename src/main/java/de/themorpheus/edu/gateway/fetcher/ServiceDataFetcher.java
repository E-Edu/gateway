package de.themorpheus.edu.gateway.fetcher;

import graphql.schema.DataFetcher;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ServiceDataFetcher {

	public DataFetcher getServiceByName() {
		Map<String, Map<String, Object>> serviceNameToService = new HashMap<>();

		Map<String, Object> service = new HashMap<>();
		service.put("name", "Gateway");
		service.put("version", "1.0");
		service.put("commit", "fg34g534z");
		service.put("buildTime", 123);
		serviceNameToService.put((String) service.get("name"), service);

		return environment -> {
			System.out.println(environment.getArguments());
			//return serviceNameToService.get((String) environment.getArgument("name"));
			return new Service("Gateway", "2.0", "nvoirgn0w", 123);
		};
	}

	public DataFetcher getServiceVersionDataFetcher() {
		return environment -> "1.0";
	}

}
