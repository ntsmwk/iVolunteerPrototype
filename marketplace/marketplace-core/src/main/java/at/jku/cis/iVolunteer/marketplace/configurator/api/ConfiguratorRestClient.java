package at.jku.cis.iVolunteer.marketplace.configurator.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.model._httprequests.InitConfiguratorRequest;


@Service
public class ConfiguratorRestClient {
	@Autowired private RestTemplate restTemplate;
	@Value("${configurator.uri}") private String url;

	public HttpStatus initConfigurator(InitConfiguratorRequest body, String key) {
		ResponseEntity<Object> resp = restTemplate.exchange(
				url + "/init/configurator/" + key, HttpMethod.PUT, buildEntity(body), Object.class);
		return resp.getStatusCode();
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
