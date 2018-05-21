package at.jku.cis.iVolunteer.marketplace.task.interaction;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.marketplace.participant.Participant;
import at.jku.cis.iVolunteer.marketplace.task.Task;

@Document
public class TaskInteraction {

	@Id
	private String id;
	@DBRef
	private Task task;
	@DBRef
	private Participant participant;
	private TaskOperation operation;
	private Date timestamp;
	private String comment;

	public TaskInteraction() {
	}

	public TaskInteraction(Task task, TaskOperation operation, Date timestamp) {
		this.task = task;
		this.operation = operation;
		this.timestamp = timestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public TaskOperation getOperation() {
		return operation;
	}

	public void setOperation(TaskOperation operation) {
		this.operation = operation;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TaskInteraction)) {
			return false;
		}
		return ((TaskInteraction) obj).id.equals(id);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
