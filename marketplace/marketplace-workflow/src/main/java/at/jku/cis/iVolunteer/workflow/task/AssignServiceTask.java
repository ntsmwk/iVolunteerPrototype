package at.jku.cis.iVolunteer.workflow.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model.source.Source;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.user.Volunteer;
import at.jku.cis.iVolunteer.workflow.rest.client.ContractorRestClient;
import at.jku.cis.iVolunteer.workflow.rest.client.WorkflowMarketplaceRestClient;

@Component
public class AssignServiceTask implements ServiceTask {

	@Autowired private ContractorRestClient contractorRestClient;
	@Autowired private WorkflowMarketplaceRestClient marketplaceRestClient;

	@Value("${marketplace.uri}") private String marketplaceUri;

	@Override
	public void execute(DelegateExecution delegateExecution) {
		String taskId = delegateExecution.getVariable(TASK_ID, String.class);
		String volunteerId = delegateExecution.getVariable(VOLUNTEER_ID, String.class);
		String token = delegateExecution.getVariable(TOKEN, String.class);
		System.out.println(this.getClass().getName() + "{taskId: " + taskId + ", volunteerId: " + volunteerId + "}");

		Task task = marketplaceRestClient.findTaskById(marketplaceUri, taskId, token);
		Source source = marketplaceRestClient.findSource(marketplaceUri, token);
		Volunteer volunteer = marketplaceRestClient.findVolunteerById(marketplaceUri, volunteerId, token);
		contractorRestClient.assignTask(task, source, volunteer, token);
	}
}