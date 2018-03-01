package at.jku.csi.marketplace.task.interaction;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.csi.marketplace.participant.Participant;
import at.jku.csi.marketplace.task.Task;

@Document
public class TaskInteraction {
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

}
