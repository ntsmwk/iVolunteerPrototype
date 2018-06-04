package at.jku.cis.iVolunteer.lib.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;


public class RestUtils {

	private static final String AUTHORIZATION = "Authorization";
	
	public static HttpEntity<?> buildEntity(String authorization) {
		return buildEntity(null, authorization);
	}
	
	public static HttpEntity<?> buildEntity(Object body, String authorization) {
		return new HttpEntity<>(body, buildAuthorizationHeader(authorization));
	}
	
	public static HttpHeaders buildAuthorizationHeader(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, authorization);
		return headers;
	}
}
