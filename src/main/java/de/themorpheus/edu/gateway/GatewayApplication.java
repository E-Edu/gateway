package de.themorpheus.edu.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GatewayApplication {

	@Value("${graphql.url:graphql}")
	private static final String GRAPHQL_URL = "/graphql";

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer webConfiguration() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping(GRAPHQL_URL).allowedOrigins(
					"http://localhost:3000",
					"api.e-edu.the-morpheus.de"
				).allowedMethods("GET", "OPTION", "POST", "PATCH", "PUT", "DELETE");
			}
		};
	}

}
