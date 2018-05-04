package at.jku.cis.trustifier.marketplace;

import static java.text.MessageFormat.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.trustifier.model.task.Task;
import at.jku.cis.trustifier.model.task.interaction.TaskInteraction;
import at.jku.cis.trustifier.model.volunteer.Volunteer;

@Service
public class MarketplaceRestClient {

	private static final String AUTHORIZATION = "Authorization";
	private static final String MARKETPLACE_RESERVE_URL = "{0}/task/{1}/reserve";
	private static final String MARKETPLACE_ASSIGN_URL = "{0}/task/{1}/assign/{2}";

	@Autowired
	private RestTemplate restTemplate;

	public TaskInteraction reserve(String marketplaceURL, String authorization, Task task) {
		String url = format(MARKETPLACE_RESERVE_URL, marketplaceURL, task.getId());
		return restTemplate.postForObject(url, buildEntity(authorization), TaskInteraction.class);
	}

	public TaskInteraction assign(String marketplaceURL, String authorization, Task task, Volunteer volunteer) {
		String url = format(MARKETPLACE_ASSIGN_URL, marketplaceURL, task.getId(), volunteer.getId());
		return restTemplate.postForObject(url, buildEntity(authorization), TaskInteraction.class);
	}

	private HttpEntity<?> buildEntity(String authorization) {
		return new HttpEntity<>(null, buildAuthorizationHeader(authorization));
	}

	private HttpHeaders buildAuthorizationHeader(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, authorization);
		return headers;
	}

}
