package at.jku.cis.iVolunteer.workflow.rest.client;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.marketplace.config.rest.client.configuration.WorkflowRestTemplate;
import at.jku.cis.iVolunteer.model.contract.TaskAssignmentDTO;
import at.jku.cis.iVolunteer.model.contract.TaskCompletationDTO;
import at.jku.cis.iVolunteer.model.contract.TaskReservationDTO;
import at.jku.cis.iVolunteer.model.source.dto.SourceDTO;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.model.user.dto.VolunteerDTO;

@Service
public class ContractorRestClient extends RestClient {
	private static final String CONTRACTOR_URI = "{0}/trustifier/contractor/task/{1}";
	private static final String RESERVE = "reserve";
	private static final String UNRESERVE = "unreserve";
	private static final String ASSIGN = "assign";
	private static final String UNASSIGN = "unassign";
	private static final String FINISH = "finish";

	@Value("${trustifier.uri}")
	private URI trustifierUri;

	@Autowired
	@WorkflowRestTemplate
	private RestTemplate restTemplate;

	public void reserveTask(TaskDTO task, SourceDTO source, String authorization) {
		String requestURI = buildContractorRequestURI(RESERVE);
		TaskReservationDTO reservation = new TaskReservationDTO();
		reservation.setSource(source);
		reservation.setTask(task);

		restTemplate.postForObject(requestURI, buildEntity(reservation, authorization), Void.class);
	}

	public void unreserveTask(TaskDTO task, SourceDTO source, String authorization) {
		String requestURI = buildContractorRequestURI(UNRESERVE);
		TaskReservationDTO reservation = new TaskReservationDTO();
		reservation.setSource(source);
		reservation.setTask(task);

		restTemplate.postForObject(requestURI, buildEntity(reservation, authorization), Void.class);

	}

	public void assignTask(TaskDTO task, SourceDTO source, VolunteerDTO volunteer, String authorization) {
		String requestURI = buildContractorRequestURI(ASSIGN);
		TaskAssignmentDTO assignment = new TaskAssignmentDTO();
		assignment.setSource(source);
		assignment.setTask(task);
		assignment.setVolunteer(volunteer);

		restTemplate.postForObject(requestURI, buildEntity(assignment, authorization), Void.class);
	}

	public void unassignTask(TaskDTO task, SourceDTO source, VolunteerDTO volunteer, String authorization) {
		String requestURI = buildContractorRequestURI(UNASSIGN);
		TaskAssignmentDTO assignment = new TaskAssignmentDTO();
		assignment.setSource(source);
		assignment.setTask(task);
		assignment.setVolunteer(volunteer);

		restTemplate.postForObject(requestURI, buildEntity(assignment, authorization), Void.class);
	}

	public void finishTask(TaskDTO task, SourceDTO source, String authorization) {
		String requestURI = buildContractorRequestURI(FINISH);
		TaskCompletationDTO completation = new TaskCompletationDTO();
		completation.setSource(source);
		completation.setTask(task);

		restTemplate.postForObject(requestURI, buildEntity(completation, authorization), Void.class);
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(CONTRACTOR_URI, trustifierUri, requestPath);
	}
}
