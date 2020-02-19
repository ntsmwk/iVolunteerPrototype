package at.jku.cis.iVolunteer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import at.jku.cis.iVolunteer.initialize.CoreInitializationService;

@SpringBootApplication
public class CoreApplication {

	@Autowired private CoreInitializationService coreInitializationService;

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@PostConstruct
	private void init() {
		this.coreInitializationService.init();

	}

}
