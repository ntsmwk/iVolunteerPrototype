package at.jku.cis.iVolunteer.core.marketplace;

import static java.text.MessageFormat.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class CoreMarketplaceRestClient {

	private static final String AUTHORIZATION = "Authorization";

	private String preUrl;

	@Autowired
	private RestTemplate restTemplate;

	public User registerUser(String marketplaceURL, String authorization, User user) {
		UserRole role = user.getSubscribedTenants().stream().map(t -> t.getRole()).findFirst().orElse(null);

		switch (role) {
			case VOLUNTEER:
				preUrl = "{0}/volunteer";
				break;
			case HELP_SEEKER:
				preUrl = "{0}/helpseeker";
				break;
			case RECRUITER:
				preUrl = "{0}/recruiter";
				break;
			case FLEXPROD:
				preUrl = "{0}/flexprod";
				break;
			default:
				break;
		}

		String url = format(preUrl, marketplaceURL, user);
		return restTemplate.postForObject(url, buildEntity(user, authorization), User.class);
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