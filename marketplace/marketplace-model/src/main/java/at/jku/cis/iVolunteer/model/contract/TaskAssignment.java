package at.jku.cis.iVolunteer.model.contract;

import at.jku.cis.iVolunteer.model.source.Source;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.user.Volunteer;

public class TaskAssignment {
	private Source source;

	private Task task;
	private Volunteer volunteer;

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

	public Volunteer getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

}