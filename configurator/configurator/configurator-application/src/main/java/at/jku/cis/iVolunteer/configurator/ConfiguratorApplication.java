package at.jku.cis.iVolunteer.configurator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ConfiguratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfiguratorApplication.class, args);
	}
}
