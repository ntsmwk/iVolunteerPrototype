package at.jku.cis.iVolunteer.model.task.interaction.dto;

import java.util.Date;

import com.google.gson.JsonObject;

import at.jku.cis.iVolunteer.model.hash.IHashObject;

public class TaskInteractionDTO implements IHashObject {

	private String id;
	private TaskInteractionDTO task;
	private String operation;
	private Date timestamp;
	private String comment;

	public TaskInteractionDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TaskInteractionDTO getTask() {
		return task;
	}

	public void setTask(TaskInteractionDTO task) {
		this.task = task;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
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
	public String toHashObject() {
		JsonObject json = new JsonObject();
		json.addProperty("id", id);
		json.addProperty("task", task.toString());
		json.addProperty("operation", operation);
		json.addProperty("timestamp", timestamp.toString());
		json.addProperty("comment", comment);
		return json.toString();
	}

}
