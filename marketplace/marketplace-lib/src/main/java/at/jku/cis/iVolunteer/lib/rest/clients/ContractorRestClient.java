package at.jku.cis.iVolunteer.lib.rest.clients;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import at.jku.cis.iVolunteer.lib.rest.RestUtils;
import at.jku.cis.iVolunteer.model.contract.TaskReservation;

@Service
public class ContractorRestClient {

	private static final String RESERVE = "/reserve";

	private static final String CONTRACTOR_URI = "{0}/trustifier/contractor/task/{1}";

	@Value("${marketplace.trustifier.uri}")
	private URI trustifierUri;

	@Autowired
	private RestTemplate restTemplate;

	//TODO fit to contractor rest endpoints...
	
	public void reserveTask(String taskId, String authorization) {
		String requestURI = buildContractorRequestURI(RESERVE);
		
		
		TaskReservation reservation = new TaskReservation();
		
		// restTemplate.postForObject(requestURI, buildEntity(authorization),
		// Void.class);
	}

	public void unreserveTask(String taskId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + "/unreserve");

		// restTemplate.postForObject(requestURI, buildEntity(authorization),
		// Void.class);
	}

	public void assignTask(String taskId, String volunteerId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + "/assign");

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestURI).queryParam("volunteerId",
				volunteerId);
		// restTemplate.exchange(builder.toUriString(), HttpMethod.POST,
		// buildEntity(authorization), Void.class);
	}

	public void unassignTask(String taskId, String volunteerId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + "/unassign");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestURI).queryParam("volunteerId",
				volunteerId);
		// restTemplate.exchange(builder.toUriString(), HttpMethod.POST,
		// buildEntity(authorization), Void.class);
	}

	public void finishTask(String taskId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + "/finish");
		restTemplate.postForObject(requestURI, RestUtils.buildEntity(authorization), Void.class);
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(CONTRACTOR_URI, trustifierUri, requestPath);
	}
}
