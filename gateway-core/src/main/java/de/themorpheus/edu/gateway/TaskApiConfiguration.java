package de.themorpheus.edu.gateway;

import de.themorpheus.edu.backend.api.DifficultyApi;
import de.themorpheus.edu.backend.api.LectureApi;
import de.themorpheus.edu.backend.api.ModuleApi;
import de.themorpheus.edu.backend.api.SubjectApi;
import de.themorpheus.edu.backend.api.TaskApi;
import de.themorpheus.edu.backend.api.TaskTypeApi;
import de.themorpheus.edu.backend.invoker.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskApiConfiguration {

	@Bean
	public TaskApi taskApi() {
		return new TaskApi(apiClient());
	}

	@Bean
	public TaskTypeApi taskTypeApi() {
		return new TaskTypeApi(apiClient());
	}

	@Bean
	public LectureApi lectureApi() {
		return new LectureApi(apiClient());
	}

	@Bean
	public ModuleApi moduleApi() {
		return new ModuleApi(apiClient());
	}

	@Bean
	public SubjectApi subjectApi() {
		return new SubjectApi(apiClient());
	}

	@Bean
	public DifficultyApi difficultyApi() {
		return new DifficultyApi(apiClient());
	}

	@Bean
	public ApiClient apiClient() {
		return new ApiClient();
	}

}
