package at.jku.cis.workflow.model;

public enum WorkflowType {

	SIMPLE_WORKFLOW("simpleWorkflow"), DEFAULT_WORKFLOW("defaultWorkflow");

	private String value;

	private WorkflowType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
