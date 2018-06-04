package at.jku.cis.iVolunteer.workflow.task;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.workflow.rest.client.MarketplaceRestClient;

@Component
public class StartServiceTask implements JavaDelegate {

	private static final String TASK_ID = "taskId";
	private static final String ACCESS_TOKEN = "accessToken";
	private static final String VOLUNTEERS = "volunteers";

	@Autowired
	private TaskService taskService;

	@Autowired
	private MarketplaceRestClient marketplaceRestClient;

	@Override
	public void execute(DelegateExecution delegateExecution) {
		String taskId = delegateExecution.getVariable(TASK_ID, String.class);
		String token = delegateExecution.getVariable(ACCESS_TOKEN, String.class);
		System.out.println(this.getClass().getName() + "{taskId: " + taskId + "}");

		marketplaceRestClient.startTask(taskId, token);

		retrieveAllTaskByDelegationExecution(delegateExecution).forEach((task) -> {
			taskService.resolveTask(task.getId());
		});
		delegateExecution.removeVariable(VOLUNTEERS);
	}

	private List<Task> retrieveAllTaskByDelegationExecution(DelegateExecution delegateExecution) {
		return taskService.createTaskQuery().processInstanceId(delegateExecution.getProcessInstanceId()).list();
	}
}
