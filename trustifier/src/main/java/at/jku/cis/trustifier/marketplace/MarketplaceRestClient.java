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

	//private static final String MARKETPLACE_ASSIGN_URL = "{0}/task/{1}/assign";
	private static final String MARKETPLACE_RESERVE_URL = "{0}/task/{1}/reserve";

	@Autowired
	private RestTemplate restTemplate;

	public TaskInteraction reserve(String marketplaceURL, String authorization, Task task) {
		HttpEntity<?> entity = new HttpEntity<>(null, buildAuthorizationHeader(authorization));
		String url = format(MARKETPLACE_RESERVE_URL, marketplaceURL, task.getId());
		return restTemplate.postForObject(url, entity, TaskInteraction.class);
	}

	public TaskInteraction assign() {
		// String requestUrl = MessageFormat.format("{0}/task/{1}/assign/{2}", uri,
		// taskInteraction.getTask().getId(),
		// taskInteraction.getParticipant().getId());
		// return restTemplate.postForObject(requestUrl, null, TaskInteraction.class);
		return null;
	}

	private HttpHeaders buildAuthorizationHeader(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authorization);
		return headers;
	}

}
