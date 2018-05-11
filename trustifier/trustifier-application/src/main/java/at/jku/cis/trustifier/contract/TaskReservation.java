package at.jku.cis.trustifier.contract;

import at.jku.cis.trustifier.model.source.Source;
import at.jku.cis.trustifier.model.task.Task;

public class TaskReservation {

	private Source source;
	private Task task;

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
