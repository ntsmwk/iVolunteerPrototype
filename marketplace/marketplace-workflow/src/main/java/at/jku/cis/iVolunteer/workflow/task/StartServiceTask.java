package at.jku.cis.iVolunteer.workflow.task;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.workflow.rest.client.WorkflowMarketplaceRestClient;

@Component
public class StartServiceTask implements ServiceTask {

	@Autowired
	private TaskService taskService;

	@Autowired
	private WorkflowMarketplaceRestClient marketplaceRestClient;

	@Override
	public void execute(DelegateExecution delegateExecution) {
		String taskId = delegateExecution.getVariable(TASK_ID, String.class);
		String token = delegateExecution.getVariable(TOKEN, String.class);
		System.out.println(this.getClass().getName() + "{taskId: " + taskId + "}");

		marketplaceRestClient.startTask("",taskId, token);

		retrieveAllTaskByDelegationExecution(delegateExecution).forEach((task) -> {
			taskService.resolveTask(task.getId());
		});
		delegateExecution.removeVariable(VOLUNTEER_IDS);
	}

	private List<Task> retrieveAllTaskByDelegationExecution(DelegateExecution delegateExecution) {
		return taskService.createTaskQuery().processInstanceId(delegateExecution.getProcessInstanceId()).list();
	}
}
