package at.jku.cis.iVolunteer.workflow.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model.source.dto.SourceDTO;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.workflow.rest.client.ContractorRestClient;
import at.jku.cis.iVolunteer.workflow.rest.client.WorkflowMarketplaceRestClient;

@Component
public class FinishServiceTask implements ServiceTask {
	@Autowired
	private ContractorRestClient contractorRestClient;

	@Autowired
	private WorkflowMarketplaceRestClient marketplaceRestClient;

	@Override
	public void execute(DelegateExecution delegateExecution) {
		String taskId = delegateExecution.getVariable(TASK_ID, String.class);
		String token = delegateExecution.getVariable(TOKEN, String.class);
		
		System.out.println(this.getClass().getName() + "{taskId: " + taskId + "}");

		TaskDTO task = marketplaceRestClient.findTaskById("",taskId, token);
		SourceDTO source = marketplaceRestClient.findSource("",token);

		contractorRestClient.finishTask(task, source, token);
	}
}
