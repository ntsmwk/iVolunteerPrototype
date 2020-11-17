package at.jku.cis.iVolunteer.core.task;

import static java.text.MessageFormat.format;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@Service
public class ClassInstanceRestClient {

	private static final String AUTHORIZATION = "Authorization";

	@Autowired
	private RestTemplate restTemplate;
	
	public List<ClassInstance> getClassInstancesByUserAndTenant(String marketplaceURL, String authorization, ClassArchetype archetype, String userId, String tenantId) {
		String preUrl = "{0}/meta/core/class/instance/all/tenant/{1}/archetype/{2}/user/{3}";
		String url = format(preUrl, marketplaceURL,tenantId, archetype, userId );
		ResponseEntity<ClassInstance[]> resp = restTemplate.exchange(url, HttpMethod.GET, buildEntity(null, authorization), ClassInstance[].class);
		List<ClassInstance> ret = Arrays.asList(resp.getBody());
		return ret;
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