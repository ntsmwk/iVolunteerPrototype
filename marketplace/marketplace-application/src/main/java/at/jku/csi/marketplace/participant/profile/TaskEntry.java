package at.jku.csi.marketplace.participant.profile;

import java.util.Date;

import com.google.gson.JsonObject;

import at.jku.csi.marketplace.blockchain.IHashObject;

public class TaskEntry implements IHashObject {

	private String id;
	private String taskId;
	private String taskName;
	private String taskDescription;
	private Date timestamp;

	public TaskEntry() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TaskEntry)) {
			return false;
		}
		return ((TaskEntry) obj).id == id;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toHashString() {
		JsonObject json = new JsonObject();
		json.addProperty("id", id);
		json.addProperty("taskId", taskId);
		json.addProperty("timestamp", timestamp.toString());
		return json.toString();
	}

}
