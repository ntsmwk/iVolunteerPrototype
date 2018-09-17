package at.jku.cis.iVolunteer.core.marketplace;

import static java.text.MessageFormat.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.user.dto.VolunteerDTO;

@Service
public class CoreMarketplaceRestClient {

	private static final String AUTHORIZATION = "Authorization";

	private static final String MARKETPLACE_REGISTER_VOLUNTEER = "{0}/volunteer";

	@Autowired
	private RestTemplate restTemplate;

	public VolunteerDTO registerVolunteer(String marketplaceURL, String authorization, VolunteerDTO volunteerDto) {
		String url = format(MARKETPLACE_REGISTER_VOLUNTEER, marketplaceURL, volunteerDto);
		return restTemplate.postForObject(url, buildEntity(volunteerDto, authorization), VolunteerDTO.class);
	}

	
	private HttpEntity<?> buildEntity(Object body, String authorization) {
		return new HttpEntity<>(body, buildAuthorizationHeader(authorization));
	}

	private HttpHeaders buildAuthorizationHeader(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, authorization);
		return headers;
	}
}