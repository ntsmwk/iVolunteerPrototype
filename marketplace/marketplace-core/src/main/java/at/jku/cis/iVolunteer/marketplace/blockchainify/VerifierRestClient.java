package at.jku.cis.iVolunteer.marketplace.blockchainify;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@Service
public class VerifierRestClient {

	private static final String PUBLISHED_COMPETENCE_ENTRY = "classInstance";

	private static final String VERIFIER_URI = "{0}/trustifier/verifier/{1}";

	private static final String AUTHORIZATION = "Authorization";

	@Value("${trustifier.uri}") private URI trustifierURI;

	@Autowired private RestTemplate restTemplate;

	public boolean verifyClassInstance(ClassInstance classInstance, String authorization) {
		String requestURI = buildContractorRequestURI(PUBLISHED_COMPETENCE_ENTRY);
		return restTemplate.postForObject(requestURI, buildEntity(classInstance, authorization), Boolean.class)
				.booleanValue();
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(VERIFIER_URI, trustifierURI, requestPath);
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
