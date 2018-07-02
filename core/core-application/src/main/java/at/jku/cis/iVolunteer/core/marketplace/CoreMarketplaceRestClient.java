package at.jku.cis.iVolunteer.core.marketplace;

import static java.text.MessageFormat.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.core.participant.dto.CoreVolunteerDTO;
import at.jku.cis.iVolunteer.model.task.interaction.dto.TaskInteractionDTO;

@Service
public class CoreMarketplaceRestClient {

	private static final String AUTHORIZATION = "Authorization";

	private static final String MARKETPLACE_REGISTER_VOLUNTEER = "{0}/volunteer";

	@Autowired
	private RestTemplate restTemplate;

	public TaskInteractionDTO registerVolunteer(String marketplaceURL, String authorization,
			CoreVolunteerDTO volunteerDto) {
		String url = format(MARKETPLACE_REGISTER_VOLUNTEER, marketplaceURL, volunteerDto);
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