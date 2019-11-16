package at.jku.cis.iVolunteer.workflow.rest.client;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.marketplace.config.rest.client.configuration.WorkflowRestTemplate;
import at.jku.cis.iVolunteer.model.contract.TaskAssignment;
import at.jku.cis.iVolunteer.model.contract.TaskCompletation;
import at.jku.cis.iVolunteer.model.contract.TaskReservation;
import at.jku.cis.iVolunteer.model.source.Source;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class ContractorRestClient extends RestClient {
	private static final String CONTRACTOR_URI = "{0}/trustifier/contractor/task/{1}";
	private static final String RESERVE = "reserve";
	private static final String UNRESERVE = "unreserve";
	private static final String ASSIGN = "assign";
	private static final String UNASSIGN = "unassign";
	private static final String FINISH = "finish";

	@Value("${trustifier.uri}") private URI trustifierUri;

	@Autowired @WorkflowRestTemplate private RestTemplate restTemplate;

	public void reserveTask(Task task, Source source, String authorization) {
		String requestURI = buildContractorRequestURI(RESERVE);
		TaskReservation reservation = new TaskReservation();
		reservation.setSource(source);
		reservation.setTask(task);

		restTemplate.postForObject(requestURI, buildEntity(reservation, authorization), Void.class);
	}

	public void unreserveTask(Task task, Source source, String authorization) {
		String requestURI = buildContractorRequestURI(UNRESERVE);
		TaskReservation reservation = new TaskReservation();
		reservation.setSource(source);
		reservation.setTask(task);

		restTemplate.postForObject(requestURI, buildEntity(reservation, authorization), Void.class);

	}

	public void assignTask(Task task, Source source, Volunteer volunteer, String authorization) {
		String requestURI = buildContractorRequestURI(ASSIGN);
		TaskAssignment assignment = new TaskAssignment();
		assignment.setSource(source);
		assignment.setTask(task);
		assignment.setVolunteer(volunteer);

		restTemplate.postForObject(requestURI, buildEntity(assignment, authorization), Void.class);
	}

	public void unassignTask(Task task, Source source, Volunteer volunteer, String authorization) {
		String requestURI = buildContractorRequestURI(UNASSIGN);
		TaskAssignment assignment = new TaskAssignment();
		assignment.setSource(source);
		assignment.setTask(task);
		assignment.setVolunteer(volunteer);

		restTemplate.postForObject(requestURI, buildEntity(assignment, authorization), Void.class);
	}

	public void finishTask(Task task, Source source, String authorization) {
		String requestURI = buildContractorRequestURI(FINISH);
		TaskCompletation completation = new TaskCompletation();
		completation.setSource(source);
		completation.setTask(task);

		restTemplate.postForObject(requestURI, buildEntity(completation, authorization), Void.class);
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(CONTRACTOR_URI, trustifierUri, requestPath);
	}
}
