package at.jku.cis.iVolunteer.workflow.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ResumeServiceTask implements JavaDelegate{

	
	private static final String TASK_ID = "taskId";

	@Override
	public void execute(DelegateExecution delegateExecution) {
		String taskId = delegateExecution.getVariable(TASK_ID, String.class);
		System.out.println(this.getClass().getName() +"{taskId: "+ taskId+"}");
	}
}
