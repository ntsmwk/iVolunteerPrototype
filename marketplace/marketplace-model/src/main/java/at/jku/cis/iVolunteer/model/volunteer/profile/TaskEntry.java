package at.jku.cis.iVolunteer.model.volunteer.profile;

import java.util.Date;

import com.google.gson.JsonObject;

import at.jku.cis.iVolunteer.model.hash.IHashObject;

public class TaskEntry implements IHashObject{

	private String id;
	private String taskId;
	private String taskName;
	private String taskDescription;
	private String marketplaceId;
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

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TaskEntry)) {
			return false;
		}
		return ((TaskEntry) obj).id.equals(id);
	}

	@Override
	public String toString() {
		return "TaskEntry [id=" + id + ", taskId=" + taskId + ", taskName=" + taskName + ", taskDescription="
				+ taskDescription + ", marketplaceId=" + marketplaceId + ", timestamp=" + timestamp + "]";
	}
	
	@Override
	public String toHashObject() {
		JsonObject json = new JsonObject();
		json.addProperty("id", id);
		json.addProperty("taskId", taskId);
		json.addProperty("taskName", taskName);
		json.addProperty("taskDescription", taskDescription);
		json.addProperty("timestamp", timestamp.toString());
		return json.toString();
	}

}
