package at.jku.cis.iVolunteer.model.volunteer.profile;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TaskEntry {

	private String id;
	private Date timestamp;
	private String taskId;
	private String taskName;
	private String taskDescription;
	private String marketplaceId;
	private String volunteerId;
	

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

	public String getVolunteerId() {
		return volunteerId;
	}

	public void setVolunteerId(String volunteerId) {
		this.volunteerId = volunteerId;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	public MultiValueMap<String, String> getProperties() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("timeStamp", new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'").format(getTimestamp()));
		map.add("taskId", getTaskId());
		map.add("marketplaceId", getMarketplaceId());
		map.add("volunteerId", getVolunteerId());
		return map;
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
				+ taskDescription + ", marketplaceId=" + marketplaceId + ", timestamp=" + timestamp 
				+ "volunteerId" + volunteerId + "]";
	}
}
