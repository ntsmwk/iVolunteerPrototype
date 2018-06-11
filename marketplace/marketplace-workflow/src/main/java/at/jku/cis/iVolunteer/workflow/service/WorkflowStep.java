package at.jku.cis.iVolunteer.workflow.service;

public class WorkflowStep {

	private String taskId;
	private String label;

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
}
