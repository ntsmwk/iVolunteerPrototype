package at.jku.cis.iVolunteer.model.task.interaction.dto;

import java.util.Date;

import com.google.gson.JsonObject;

import at.jku.cis.iVolunteer.model.hash.IHashObject;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.model.user.dto.UserDTO;

public class TaskInteractionDTO implements IHashObject {

	private String id;
	private TaskDTO task;
	private String operation;
	private UserDTO participant;
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

	public TaskDTO getTask() {
		return task;
	}

	public void setTask(TaskDTO task) {
		this.task = task;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public UserDTO getParticipant() {
		return participant;
	}

	public void setParticipant(UserDTO participant) {
		this.participant = participant;
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
