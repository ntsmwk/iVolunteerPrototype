package at.jku.cis.iVolunteer.model.contract;

import at.jku.cis.iVolunteer.model.participant.dto.VolunteerDTO;
import at.jku.cis.iVolunteer.model.source.Source;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;

public class TaskAssignment {
	private Source source;

	private TaskDTO task;
	private VolunteerDTO volunteer;

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

	public VolunteerDTO getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(VolunteerDTO volunteer) {
		this.volunteer = volunteer;
	}

}