package at.jku.cis.workflow.model;

import at.jku.cis.workflow.model.Workflow.WorkflowStep;

public class WorkflowProcess {

	private String taskId;
	private Workflow workflow;
	private int currentWorkflowStep;

	public WorkflowProcess(String taskId, Workflow workflow, int currentWorkflowStep) {
		this.taskId = taskId;
		this.workflow = workflow;
		this.currentWorkflowStep = currentWorkflowStep;
	}

	public WorkflowStep getFollowingWorkflowStep() {
		return workflow.getFollowingWorkflowStep(currentWorkflowStep);
	}

	public void executeFollowingWorkflowStep() {
		this.workflow.getFollowingWorkflowStep(currentWorkflowStep).getWorkflowComponent().execute();
		this.currentWorkflowStep++;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public int getCurrentWorkflowStep() {
		return currentWorkflowStep;
	}

	public void setCurrentWorkflowStep(int currentWorkflowStep) {
		this.currentWorkflowStep = currentWorkflowStep;
	}
}
