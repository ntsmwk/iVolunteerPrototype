package at.jku.cis.iVolunteer.workflow.rest;

import org.apache.commons.lang3.StringUtils;

public class WorkflowType {
	private String key;
	private String name;

	public WorkflowType(String key, String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof WorkflowType)) {
			return false;
		}
		return StringUtils.equals(((WorkflowType) obj).key, key);
	}

}
