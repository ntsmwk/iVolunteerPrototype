package at.jku.cis.iVolunteer.workflow.marketplace;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MarketplaceRestClient {


	private static final String TASK_CONTROLLER_URI = "{0}/task/{1}";

	@Value("${marketplace.uri}")
	private URI marketplaceURI;

	@Autowired
	private RestTemplate restTemplate;
	
	public void createTask() {
//		String requestURI = buildContractorRequestURI(TASK_ENTRY);
//		return restTemplate.postForObject(requestURI, taskEntry, String.class);
	}
	
	private String buildContractorRequestURI(String requestPath) {
		return format(TASK_CONTROLLER_URI, marketplaceURI, requestPath);
	}
}
