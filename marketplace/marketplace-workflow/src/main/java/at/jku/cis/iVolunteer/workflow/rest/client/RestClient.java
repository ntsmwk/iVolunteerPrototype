package at.jku.cis.iVolunteer.workflow.rest.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public abstract class RestClient {

	private static final String AUTHORIZATION = "Authorization";

	public HttpEntity<?> buildEntity(String authorization) {
		return buildEntity(null, authorization);
	}

	public HttpEntity<?> buildEntity(Object body, String authorization) {
		return new HttpEntity<>(body, buildAuthorizationHeader(authorization));
	}

	public HttpHeaders buildAuthorizationHeader(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, authorization);
		return headers;
	}
}
