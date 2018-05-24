package at.jku.cis.iVolunteer.workflow.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.workflow.marketplace.MarketplaceRestClient;

@Component
public class UnreserveServiceTask implements JavaDelegate {

	private static final String TASK_ID = "taskId";

	
	@Autowired
	private MarketplaceRestClient marketplaceRestClient;

	@Override
	public void execute(DelegateExecution delegateExecution) {
		String taskId = delegateExecution.getVariable(TASK_ID, String.class);
		System.out.println(this.getClass().getName() +"{taskId: "+ taskId+"}");
		
		marketplaceRestClient.unreserveTask(taskId);
	}
}
