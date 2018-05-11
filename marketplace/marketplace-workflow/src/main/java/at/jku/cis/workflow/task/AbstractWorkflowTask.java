package at.jku.cis.workflow.components;

import org.camunda.bpm.engine.delegate.JavaDelegate;

public abstract class AbstractWorkflowTask implements JavaDelegate {

	private String taskId;
	private String processId;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

}
