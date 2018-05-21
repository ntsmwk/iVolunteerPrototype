package at.jku.cis.iVolunteer.workflow.rest;

import java.util.Map;

public class WorkflowStep {

	private String taskId;
	private String label;
	private Map<String, Object> params;

	public WorkflowStep() {
	}

	public WorkflowStep(String taskId, String label) {
		super();
		this.taskId = taskId;
		this.label = label;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
