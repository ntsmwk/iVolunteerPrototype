package at.jku.cis.iVolunteer.model.contract;

import at.jku.cis.iVolunteer.model.source.dto.SourceDTO;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.model.user.dto.VolunteerDTO;

public class TaskAssignmentDTO {
	private SourceDTO source;

	private TaskDTO task;
	private VolunteerDTO volunteer;

	public SourceDTO getSource() {
		return source;
	}

	public void setSource(SourceDTO source) {
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