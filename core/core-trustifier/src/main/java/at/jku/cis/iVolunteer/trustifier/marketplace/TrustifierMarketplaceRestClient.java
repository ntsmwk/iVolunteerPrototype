package at.jku.cis.iVolunteer.trustifier.marketplace;

import static java.text.MessageFormat.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.participant.dto.VolunteerDTO;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.model.task.interaction.dto.TaskInteractionDTO;

@Service
public class TrustifierMarketplaceRestClient {

	private static final String AUTHORIZATION = "Authorization";
	
	private static final String MARKETPLACE_RESERVE_URL = "{0}/task/{1}/reserve";
	private static final String MARKETPLACE_UNRESERVE_URL = "{0}/task/{1}/unreserve";
	
	private static final String MARKETPLACE_ASSIGN_URL = "{0}/task/{1}/assign?volunteerId={2}";
	private static final String MARKETPLACE_UNASSIGN_URL = "{0}/task/{1}/unassign?volunteerId={2}";
	
	private static final String MARKETPLACE_FINISH_URL = "{0}/task/{1}/finish";

	@Autowired
	private RestTemplate restTemplate;

	public TaskInteractionDTO reserve(String marketplaceURL, String authorization, TaskDTO task) {
		String url = format(MARKETPLACE_RESERVE_URL, marketplaceURL, task.getId());
		return restTemplate.postForObject(url, buildEntity(authorization), TaskInteractionDTO.class);
	}

	public TaskInteractionDTO unreserve(String marketplaceURL, String authorization, TaskDTO task) {
		String url = format(MARKETPLACE_UNRESERVE_URL, marketplaceURL, task.getId());
		return restTemplate.postForObject(url, buildEntity(authorization), TaskInteractionDTO.class);
	}

	public TaskInteractionDTO assign(String marketplaceURL, String authorization, TaskDTO task, VolunteerDTO volunteer) {
		String url = format(MARKETPLACE_ASSIGN_URL, marketplaceURL, task.getId(), volunteer.getId());
		return restTemplate.postForObject(url, buildEntity(authorization), TaskInteractionDTO.class);
	}

	public TaskInteractionDTO unassign(String marketplaceURL, String authorization, TaskDTO task, VolunteerDTO volunteer) {
		String url = format(MARKETPLACE_UNASSIGN_URL, marketplaceURL, task.getId(), volunteer.getId());
		return restTemplate.postForObject(url, buildEntity(authorization), TaskInteractionDTO.class);
	}

	public TaskInteractionDTO finish(String marketplaceURL, String authorization, TaskDTO task) {
		String url = format(MARKETPLACE_FINISH_URL, marketplaceURL, task.getId());
		return restTemplate.postForObject(url, buildEntity(authorization), TaskInteractionDTO.class);
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
