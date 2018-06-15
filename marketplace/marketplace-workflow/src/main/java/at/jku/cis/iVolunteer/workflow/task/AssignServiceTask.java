package at.jku.cis.iVolunteer.workflow.task;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model.participant.dto.VolunteerDTO;
import at.jku.cis.iVolunteer.model.source.dto.SourceDTO;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.workflow.rest.client.ContractorRestClient;
import at.jku.cis.iVolunteer.workflow.rest.client.MarketplaceRestClient;

@Component
public class AssignServiceTask implements JavaDelegate {

	private static final String TASK_ID = "taskId";
	private static final String VOLUNTEER = "volunteer";

	@Autowired
	private ContractorRestClient contractorRestClient;

	@Autowired
	private MarketplaceRestClient marketplaceRestClient;
	

	@Override
	public void execute(DelegateExecution delegateExecution) {
		String taskId = delegateExecution.getVariable(TASK_ID, String.class);
		String volunteerName = delegateExecution.getVariable(VOLUNTEER, String.class);
		String token = delegateExecution.getVariable("accessToken", String.class);
		System.out.println(this.getClass().getName() + "{taskId: " + taskId + ", volunteerId: " + volunteerName + "}");
		TaskDTO task = marketplaceRestClient.findTaskById(taskId, token);
		SourceDTO source = marketplaceRestClient.findSource(token);
		VolunteerDTO volunteer = marketplaceRestClient.findVolunteerByUserName(volunteerName, token);

		contractorRestClient.assignTask(task, source, volunteer, token);
	}
}
