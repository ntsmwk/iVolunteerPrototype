package at.jku.cis.iVolunteer.trustifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TrustifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrustifierApplication.class, args);
	}

}
