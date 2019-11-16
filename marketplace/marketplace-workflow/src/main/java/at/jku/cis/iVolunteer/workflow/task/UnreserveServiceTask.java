package at.jku.cis.iVolunteer.workflow.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model.source.Source;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.workflow.rest.client.ContractorRestClient;
import at.jku.cis.iVolunteer.workflow.rest.client.WorkflowMarketplaceRestClient;

@Component
public class UnreserveServiceTask implements ServiceTask {

	@Autowired private ContractorRestClient contractorRestClient;

	@Autowired private WorkflowMarketplaceRestClient marketplaceRestClient;
	@Value("${marketplace.uri}") private String marketplaceUri;

	@Override
	public void execute(DelegateExecution delegateExecution) {
		String taskId = delegateExecution.getVariable(TASK_ID, String.class);
		String token = delegateExecution.getVariable(TOKEN, String.class);
		System.out.println(this.getClass().getName() + "{taskId: " + taskId + "}");

		Source source = marketplaceRestClient.findSource(marketplaceUri, token);
		Task task = marketplaceRestClient.findTaskById(marketplaceUri, taskId, token);

		contractorRestClient.unreserveTask(task, source, token);
	}
}
