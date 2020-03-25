package de.themorpheus.edu.gateway.fetcher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service {

	private String name;
	private String version;
	private String commit;
	private int buildTime;

}
