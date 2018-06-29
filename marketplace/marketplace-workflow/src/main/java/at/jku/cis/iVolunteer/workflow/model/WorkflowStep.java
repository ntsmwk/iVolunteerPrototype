package at.jku.cis.iVolunteer.workflow.model;

import java.util.Map;

public class WorkflowStep {

	private String taskId;
	private String label;
	private String assignee;
	private Map<String, Object> params;
	private WorkflowStepType workflowStepType;

	public WorkflowStep() {
	}

	public WorkflowStep(String taskId, String label, WorkflowStepType workflowStepType, String assignee) {
		super();
		this.taskId = taskId;
		this.label = label;
		this.workflowStepType = workflowStepType;
		this.assignee = assignee;
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

	public WorkflowStepType getWorkflowStepType() {
		return workflowStepType;
	}

	public void setWorkflowStepType(WorkflowStepType workflowStepType) {
		this.workflowStepType = workflowStepType;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
}
