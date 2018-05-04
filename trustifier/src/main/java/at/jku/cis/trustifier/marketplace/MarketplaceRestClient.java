package at.jku.cis.trustifier.marketplace;

import static java.text.MessageFormat.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.trustifier.model.task.Task;
import at.jku.cis.trustifier.model.task.interaction.TaskInteraction;

@Service
public class MarketplaceRestClient {

	private static final String RESERVE_URL = "{0}/task/{1}/reserve";
	
	@Autowired
	private RestTemplate restTemplate;

	public TaskInteraction reserve(String marketplaceURL, String authorization, Task task) {
		HttpEntity<?> entity = new HttpEntity<>(null, buildAuthorizationHeader(authorization));
		String url = format(RESERVE_URL, marketplaceURL, task.getId());
		return restTemplate.postForObject(url, entity, TaskInteraction.class);
	}

	private HttpHeaders buildAuthorizationHeader(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authorization);
		return headers;
	}

}
