package at.jku.cis.workflow.component;

public abstract class AbstractWorkflowComponent {

	private String componentId;
	private String componentName;

	public abstract void execute();

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
}
