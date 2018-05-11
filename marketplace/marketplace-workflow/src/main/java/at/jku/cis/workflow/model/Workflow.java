package at.jku.cis.workflow.model;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.workflow.component.AbstractWorkflowComponent;

public abstract class Workflow {

	private WorkflowType workFlowType;

	private final List<WorkflowStep> components = new ArrayList<>();

	public WorkflowStep getFollowingWorkflowStep(int currentWorkflowStep) {
		return components.stream().filter(step -> step.getWorkflowStep() == currentWorkflowStep + 1).findFirst().orElse(null);
	}
	
	public WorkflowType getWorkFlowType() {
		return workFlowType;
	}

	public void setWorkFlowType(WorkflowType workFlowType) {
		this.workFlowType = workFlowType;
	}

	public List<WorkflowStep> getComponents() {
		return components;
	}

	public class WorkflowStep {
		private int workflowStep;
		private AbstractWorkflowComponent workflowComponent;

		public WorkflowStep(int workflowStep, AbstractWorkflowComponent workflowComponent) {
			this.workflowStep = workflowStep;
			this.workflowComponent = workflowComponent;
		}

		public int getWorkflowStep() {
			return workflowStep;
		}

		public void setWorkflowStep(int workflowStep) {
			this.workflowStep = workflowStep;
		}

		public AbstractWorkflowComponent getWorkflowComponent() {
			return workflowComponent;
		}

		public void setWorkflowComponent(AbstractWorkflowComponent workflowComponent) {
			this.workflowComponent = workflowComponent;
		}

	}

}