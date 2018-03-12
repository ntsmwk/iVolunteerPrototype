package at.jku.csi.marketplace.blockchain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

	
	@Bean
	public RestTemplate prodduceRestTemplate() {
		return new RestTemplate();
	}
}
