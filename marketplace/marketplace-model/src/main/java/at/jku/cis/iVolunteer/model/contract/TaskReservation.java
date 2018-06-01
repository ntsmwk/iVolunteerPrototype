package at.jku.cis.iVolunteer.model.contract;

import at.jku.cis.iVolunteer.model.source.Source;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;

public class TaskReservation {

	private Source source;
	private TaskDTO task;

	public TaskReservation() {
		// TODO Auto-generated constructor stub
	}

	public TaskReservation(Source source, TaskDTO task) {
		this.source = source;
		this.task = task;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public TaskDTO getTask() {
		return task;
	}

	public void setTask(TaskDTO task) {
		this.task = task;
	}
}