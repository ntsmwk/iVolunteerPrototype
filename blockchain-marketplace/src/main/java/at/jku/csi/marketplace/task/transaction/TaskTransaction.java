package at.jku.csi.marketplace.task.transaction;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.csi.marketplace.participant.Volunteer;
import at.jku.csi.marketplace.task.Task;

@Document
public class TaskTransaction {

	@DBRef
	private Task task;
	private TaskStatus status;
	@DBRef
	private Volunteer volunteer;
	private Date timestamp;

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public Volunteer getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
