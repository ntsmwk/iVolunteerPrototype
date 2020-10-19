package at.jku.cis.iVolunteer.model.meta.core.clazz;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TaskInstanceStatus {

	OPEN("OPEN"), CLOSED("CLOSED")
	;

	private final String status;

	private TaskInstanceStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	@Override
	public String toString() {
		return status;
	}

	@JsonCreator
	public static TaskInstanceStatus getFromStatus(String status) {
		for (TaskInstanceStatus st : TaskInstanceStatus.values()) {
			if (st.getStatus().equals(status)) {
				return st;
			}
		}
		throw new IllegalArgumentException();
	}
}
