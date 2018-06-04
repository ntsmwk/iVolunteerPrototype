package at.jku.cis.iVolunteer.workflow.rest.client;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import at.jku.cis.iVolunteer.model.contract.TaskAssignment;
import at.jku.cis.iVolunteer.model.contract.TaskCompletation;
import at.jku.cis.iVolunteer.model.contract.TaskReservation;

@Service
public class ContractorRestClient extends RestClient {
	private static final String CONTRACTOR_URI = "{0}/trustifier/contractor/task/{1}";
	private static final String RESERVE = "/reserve";
	private static final String UNRESERVE = "/unreserve";
	private static final String ASSIGN = "/assign";
	private static final String UNASSIGN = "/unassign";
	private static final String FINISH = "/finish";

	@Value("${marketplace.trustifier.uri}")
	private URI trustifierUri;

	@Autowired
	private RestTemplate restTemplate;

	public void reserveTask(String taskId, String authorization) {
		String requestURI = buildContractorRequestURI(RESERVE);
		TaskReservation reservation = new TaskReservation();
		// reservation.setSource(source);
		// reservation.setTask(task);

		restTemplate.postForObject(requestURI, buildEntity(reservation, authorization), Void.class);
	}

	public void unreserveTask(String taskId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + UNRESERVE);
		TaskReservation reservation = new TaskReservation();
		// reservation.setSource(source);
		// reservation.setTask(task);

		restTemplate.postForObject(requestURI, buildEntity(reservation, authorization), Void.class);

	}

	public void assignTask(String taskId, String volunteerId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + ASSIGN);
		TaskAssignment assignment = new TaskAssignment();
		// assignment.setSource(source);
		// assignment.setTask(task);
		// assignment.setVolunteer(volunteer);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestURI).queryParam("volunteerId",
				volunteerId);
		restTemplate.exchange(builder.toUriString(), HttpMethod.POST, buildEntity(assignment, authorization),
				Void.class);
	}

	public void unassignTask(String taskId, String volunteerId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + UNASSIGN);
		TaskAssignment assignment = new TaskAssignment();
		// assignment.setSource(source);
		// assignment.setTask(task);
		// assignment.setVolunteer(volunteer);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestURI).queryParam("volunteerId",
				volunteerId);
		restTemplate.exchange(builder.toUriString(), HttpMethod.POST, buildEntity(assignment, authorization),
				Void.class);
	}

	public void finishTask(String taskId, String authorization) {
		String requestURI = buildContractorRequestURI(taskId + FINISH);
		TaskCompletation completation = new TaskCompletation();
		// completation.setSource(source);
		// completation.setTask(task);

		restTemplate.postForObject(requestURI, buildEntity(completation, authorization), Void.class);
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(CONTRACTOR_URI, trustifierUri, requestPath);
	}
}
