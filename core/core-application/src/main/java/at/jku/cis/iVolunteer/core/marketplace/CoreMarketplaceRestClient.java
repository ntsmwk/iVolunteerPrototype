package at.jku.cis.iVolunteer.core.marketplace;

import static java.text.MessageFormat.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class CoreMarketplaceRestClient {

	private static final String AUTHORIZATION = "Authorization";

	@Autowired
	private RestTemplate restTemplate;
	
	public User registerOrUpdateMarketplaceUser(String marketplaceURL, String authorization, User user) {
		String preUrl = "{0}/user/register";
		String url = format(preUrl, marketplaceURL, user);
		return restTemplate.postForObject(url, buildEntity(user, authorization), User.class);
	}

	
	public User subscribeUserToTenant(String marketplaceUrl, String marketplaceId, String tenantId, String userId, String authorization, UserRole role) {		
		String preUrl = "{0}/user/subscribe/{1}/{2}/{3}/{4}";
		String url = format(preUrl, marketplaceUrl, marketplaceId, tenantId, userId, role);
		return restTemplate.postForObject(url, buildEntity(new User(), authorization), User.class);
	}
	
	public User unsubscribeUserFromTenant(String marketplaceUrl, String marketplaceId, String tenantId, String userId, String authorization, UserRole role) {
		String preUrl = "{0}/user/unsubscribe/{1}/{2}/{3}/{4}";
		String url = format(preUrl, marketplaceUrl, marketplaceId, tenantId, userId, role);
		return restTemplate.postForObject(url, buildEntity(new User(), authorization), User.class);
	}

	private HttpEntity<?> buildEntity(Object body, String authorization) {
		return new HttpEntity<>(body, buildAuthorizationHeader(authorization));
	}

	private HttpHeaders buildAuthorizationHeader(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, authorization);
		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}
}