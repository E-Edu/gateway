package de.themorpheus.edu.gateway;

import de.themorpheus.edu.gateway.backend.BackendAPI;
import de.themorpheus.edu.gateway.util.GitInfo;
import com.careykevin.graphql.actuator.instrumentation.EnableGraphQLActuator;
import com.jcabi.manifests.Manifests;
import io.sentry.Sentry;
import io.sentry.SentryClient;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.IOException;

@EnableGraphQLActuator
@SpringBootApplication
public class GatewayApplication {

	public static final boolean PRODUCTIVE = Boolean.parseBoolean(System.getenv("PRODUCTIVE"));
	public static final boolean SENTRY_ENABLED = Boolean.parseBoolean(System.getenv("SENTRY_ENABLED"));

	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayApplication.class.getSimpleName());

	@Getter private static BackendAPI backendAPI;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(GatewayApplication.class, args);
		initSentry();

		backendAPI = new BackendAPI();
	}

	@Bean
	public WebMvcConfigurer webConfiguration() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
					.addMapping("/graphql")
					.allowedOrigins(
						"*" //TODO: Not in production
					)
					.allowedMethods("GET", "OPTION", "POST", "PATCH", "PUT", "DELETE");
			}
		};
	}

	private static void initSentry() {
		if (!PRODUCTIVE && !SENTRY_ENABLED) {
			Sentry.init("");
			LOGGER.info("Sentry error reporting is disabled.");
			return;
		}

		Sentry.init("https://198f56a887e14287bbf3e6d1b6fd2a5d@sentry.the-morpheus.de/9");
		SentryClient sentry = Sentry.getStoredClient();
		sentry.addTag("OS", System.getProperty("OS"));
		sentry.addTag("version", GatewayApplication.class.getPackage().getImplementationVersion());
		sentry.addTag("title", GatewayApplication.class.getPackage().getImplementationTitle());
		sentry.addTag("environment", PRODUCTIVE ? "production" : "dev");

		try {
			GitInfo gitInfo = GitInfo.load();
			sentry.addTag("build-host", gitInfo.getBuildHost());
			sentry.addTag("build-time", Manifests.read("Build-Time"));
			sentry.addTag("build-user-email", gitInfo.getBuildUserEmail());
			sentry.addTag("build-version", gitInfo.getBuildVersion());
			sentry.addTag("git-branch", gitInfo.getBranch());
			sentry.addTag("git-commit-id", gitInfo.getCommitId());
			sentry.addTag("git-commit-message", gitInfo.getCommitMessageShort());
			sentry.addTag("git-commit-user-email", gitInfo.getCommitUserEmail());
			sentry.addTag("git-dirty", gitInfo.getGitDirty());
		} catch (IOException ex) {
			LOGGER.error("Error while loading git information", ex);
		}

		LOGGER.info("Sentry error reporting is enabled.");
	}

}
