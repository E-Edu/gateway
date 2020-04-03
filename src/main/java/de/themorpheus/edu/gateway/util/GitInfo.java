package de.themorpheus.edu.gateway.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.Properties;

@Data
@AllArgsConstructor
public class GitInfo {

	private String branch;
	private String buildHost;
	private String buildUserEmail;
	private String buildVersion;
	private String commitId;
	private String commitMessageShort;
	private String commitUserEmail;
	private String gitDirty;

	public static GitInfo load() throws IOException {
		Resource resource = new ClassPathResource("git.properties");
		Properties properties = new Properties();
		properties.load(resource.getInputStream());

		return new GitInfo(
			properties.getProperty("git.branch"),
			properties.getProperty("git.build.host"),
			properties.getProperty("git.build.user.email"),
			properties.getProperty("git.build.version"),
			properties.getProperty("git.commit.id"),
			properties.getProperty("git.commit.message.short"),
			properties.getProperty("git.commit.user.email"),
			properties.getProperty("git.dirty")
		);
	}

}
