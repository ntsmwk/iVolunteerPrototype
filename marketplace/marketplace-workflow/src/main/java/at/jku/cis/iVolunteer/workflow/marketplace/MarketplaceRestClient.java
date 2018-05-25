package at.jku.cis.iVolunteer.workflow.marketplace;

import static java.text.MessageFormat.format;

import java.net.URI;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MarketplaceRestClient {

	private static final String TASK_CONTROLLER_URI = "{0}/task/{1}";

	private static final String AUTHORIZATION = "Authorization";
	@Value("${marketplace.uri}")
	private URI marketplaceURI;

	public MarketplaceRestClient() {
	}

	@Autowired
	private RestTemplate restTemplate;
	
	public void publishTask(String taskId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + "/publish");
		restTemplate.postForObject(requestURI, buildEntity(authorization), Void.class);
	}

	public void reserveTask(String taskId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + "/reserve");
		restTemplate.postForObject(requestURI, buildEntity(authorization), Void.class);
	}

	public void unreserveTask(String taskId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + "/unreserve");
		restTemplate.postForObject(requestURI, buildEntity(authorization), Void.class);
	}

	public void assignTask(String taskId, String volunteerId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + "/assign");

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestURI).queryParam("volunteerId",
				volunteerId);
		restTemplate.exchange(builder.toUriString(), HttpMethod.POST, buildEntity(authorization), Void.class);
	}

	public void unassignTask(String taskId, String volunteerId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + "/unassign");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestURI).queryParam("volunteerId",
				volunteerId);
		restTemplate.exchange(builder.toUriString(), HttpMethod.POST, buildEntity(authorization), Void.class);
	}

	public void startTask(String taskId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + "/start");
		restTemplate.postForObject(requestURI, buildEntity(authorization), Void.class);
	}

	public void suspendTask(String taskId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + "/suspend");
		restTemplate.postForObject(requestURI, buildEntity(authorization), Void.class);
	}

	public void resumeTask(String taskId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + "/resume");
		restTemplate.postForObject(requestURI, buildEntity(authorization), Void.class);
	}

	public void finishTask(String taskId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + "/finish");
		restTemplate.postForObject(requestURI, buildEntity(authorization), Void.class);
	}

	private HttpEntity<?> buildEntity(String authorization) {
		return new HttpEntity<>(null, buildAuthorizationHeader(authorization));
	}

	private HttpHeaders buildAuthorizationHeader(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, authorization);
		return headers;
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(TASK_CONTROLLER_URI, marketplaceURI, requestPath);
	}
}
