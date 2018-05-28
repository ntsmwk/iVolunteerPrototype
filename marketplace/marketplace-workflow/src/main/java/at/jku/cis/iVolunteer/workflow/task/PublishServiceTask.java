package at.jku.cis.iVolunteer.workflow.task;

import java.util.HashSet;
import java.util.Set;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.workflow.marketplace.MarketplaceRestClient;

@Component
public class PublishServiceTask implements JavaDelegate {

	private static final String TASK_ID = "taskId";


	@Autowired
	private MarketplaceRestClient marketplaceRestClient;
	
	@Override
	public void execute(DelegateExecution delegateExecution) {
		String taskId = delegateExecution.getVariable(TASK_ID, String.class);
		String token = delegateExecution.getVariable("accessToken", String.class);
		System.out.println(this.getClass().getName() +"{taskId: "+ taskId+"}");
		
		Set<String> volunteers = new HashSet<String>();
		volunteers.add("broiser");
		volunteers.add("mweissenbek");
		delegateExecution.setVariable("volunteers", volunteers);
		marketplaceRestClient.publishTask(taskId, token);
	}
}
