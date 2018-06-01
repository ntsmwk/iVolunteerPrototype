package at.jku.cis.iVolunteer.workflow.task;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.lib.rest.clients.MarketplaceRestClient;
import at.jku.cis.iVolunteer.model.participant.dto.VolunteerDTO;

@Component
public class PublishServiceTask implements JavaDelegate {

	private static final String TASK_ID = "taskId";
	private static final String ACCESS_TOKEN = "accessToken";
	private static final String VOLUNTEERS = "volunteers";

	@Autowired
	private MarketplaceRestClient marketplaceRestClient;

	@Override
	public void execute(DelegateExecution delegateExecution) {
		String taskId = delegateExecution.getVariable(TASK_ID, String.class);
		String token = delegateExecution.getVariable(ACCESS_TOKEN, String.class);
		System.out.println(this.getClass().getName() + "{taskId: " + taskId + "}");

		marketplaceRestClient.publishTask(taskId, token);
		delegateExecution.setVariable(VOLUNTEERS, extractUsername(marketplaceRestClient.findVolunteers(token)));
	}

	private Set<String> extractUsername(List<VolunteerDTO> volunteers) {
		return volunteers.stream().map(volunteer -> volunteer.getUsername()).collect(toSet());
	}
}
