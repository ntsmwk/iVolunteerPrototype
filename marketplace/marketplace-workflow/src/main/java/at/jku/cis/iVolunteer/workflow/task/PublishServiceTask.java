package at.jku.cis.iVolunteer.workflow.task;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.user.dto.VolunteerDTO;
import at.jku.cis.iVolunteer.workflow.rest.client.WorkflowMarketplaceRestClient;

@Component
public class PublishServiceTask implements ServiceTask {

	@Autowired
	private WorkflowMarketplaceRestClient marketplaceRestClient;
	
	@Value("${marketplace.uri}")
	private String marketplaceUri;

	@Override
	public void execute(DelegateExecution delegateExecution) {
		String taskId = delegateExecution.getVariable(TASK_ID, String.class);
		String token = delegateExecution.getVariable(TOKEN, String.class);
		System.out.println(this.getClass().getName() + "{taskId: " + taskId + "}");

		marketplaceRestClient.publishTask(marketplaceUri,taskId, token);
		delegateExecution.setVariable(VOLUNTEER_IDS, extractVolunteerIds(marketplaceRestClient.findVolunteers(marketplaceUri,token)));
	}

	private Set<String> extractVolunteerIds(List<VolunteerDTO> volunteers) {
		return volunteers.stream().map(volunteer -> volunteer.getId()).collect(toSet());
	}
}
