package at.jku.cis.iVolunteer.configurator.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.configurator.model._httprequests.ClassConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.ClassInstanceConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.MatchingConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.PropertyConfiguratorResponseRequestBody;


@Service
public class ResponseRestClient {

	@Autowired private RestTemplate restTemplate;

	public ResponseEntity<Object> sendClassConfiguratorResponse(String url, ClassConfiguratorResponseRequestBody body) {
		try {
			ResponseEntity<Object> resp = restTemplate.exchange(url, HttpMethod.POST, buildEntity(body), Object.class);
			return resp;
		} catch(Exception e) {
			return handleException(e);
		}
	}

	public ResponseEntity<Object> sendClassInstanceConfiguratorResponse(String url, ClassInstanceConfiguratorResponseRequestBody body) {
		try {
			ResponseEntity<Object> resp = restTemplate.exchange(url, HttpMethod.POST, buildEntity(body), Object.class);
			return resp;
		} catch(Exception e) {
			return handleException(e);
		}
	}

	public ResponseEntity<Object> sendMatchingConfiguratorResponse(String url, MatchingConfiguratorResponseRequestBody body) {
		try {
			ResponseEntity<Object> resp = restTemplate.exchange(url, HttpMethod.POST, buildEntity(body), Object.class);
			return resp;
		} catch(Exception e) {
			return handleException(e);
		}
	}
	
	public ResponseEntity<Object> sendPropertyConfiguratorResponse(String url, PropertyConfiguratorResponseRequestBody body) {
		try {
			ResponseEntity<Object> resp = restTemplate.exchange(url, HttpMethod.POST, buildEntity(body), Object.class);
			return resp;
		} catch(Exception e) {
			return handleException(e);
		}
	}
	
	private ResponseEntity<Object> handleException(Exception e) {
		return ResponseEntity.badRequest().build();
	}

	private HttpEntity<?> buildEntity(Object body) {
		return new HttpEntity<>(body, buildHeaders());
	}

	private HttpHeaders buildHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}
}
