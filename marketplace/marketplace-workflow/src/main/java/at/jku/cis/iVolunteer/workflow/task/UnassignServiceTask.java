package at.jku.cis.iVolunteer.workflow.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model.participant.dto.VolunteerDTO;
import at.jku.cis.iVolunteer.model.source.dto.SourceDTO;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.workflow.rest.client.ContractorRestClient;
import at.jku.cis.iVolunteer.workflow.rest.client.WorkflowMarketplaceRestClient;

@Component
public class UnassignServiceTask implements ServiceTask {

	@Autowired
	private ContractorRestClient contractorRestClient;

	@Autowired
	private WorkflowMarketplaceRestClient marketplaceRestClient;
	@Value("${marketplace.uri}")
	private String marketplaceUri;

	@Override
	public void execute(DelegateExecution delegateExecution) {
		String taskId = delegateExecution.getVariable(TASK_ID, String.class);
		String token = delegateExecution.getVariable(TOKEN, String.class);
		String volunteerId = delegateExecution.getVariable(VOLUNTEER_ID, String.class);
		System.out.println(this.getClass().getName() + "{taskId: " + taskId + ", volunteerId: " + volunteerId + "}");

		SourceDTO source = marketplaceRestClient.findSource(marketplaceUri, token);
		TaskDTO task = marketplaceRestClient.findTaskById(marketplaceUri, taskId, token);
		VolunteerDTO volunteer = marketplaceRestClient.findVolunteerById(marketplaceUri, volunteerId, token);

		contractorRestClient.unassignTask(task, source, volunteer, token);
	}

}
