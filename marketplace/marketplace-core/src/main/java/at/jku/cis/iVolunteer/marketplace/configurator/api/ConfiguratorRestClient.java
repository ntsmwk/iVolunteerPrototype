package at.jku.cis.iVolunteer.marketplace.configurator.api;

import java.util.List;

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


@Service
public class ConfiguratorRestClient {
	@Autowired private RestTemplate restTemplate;
	@Value("${configurator.uri}") private String url;


	public HttpStatus initConfigurator(List<String> tenantIds) {
		ResponseEntity<Object> resp = restTemplate.exchange(
				url + "/init/configurator/all" , HttpMethod.PUT, buildEntity(tenantIds), Object.class);
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
