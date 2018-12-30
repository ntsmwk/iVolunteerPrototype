package at.jku.cis.iVolunteer.model.task.interaction;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.TaskOperation;
import at.jku.cis.iVolunteer.model.user.User;

@Document
public class TaskInteraction {

	@Id
	private String id;
	@DBRef
	private Task task;
	@DBRef
	private User participant;
	private TaskOperation operation;
	private Date timestamp;
	private String comment;
	
	private String taskInteractionType;
	private String marketplaceId;
	
	private String taskId;
	private String volunteerId;
	
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

	public User getParticipant() {
		return participant;
	}

	public void setParticipant(User participant) {
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
	
	public String getTaskInteractionType() {
		return taskInteractionType;
	}

	public void setTaskInteractionType(String taskInteractionType) {
		this.taskInteractionType = taskInteractionType;
	}

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}	

	public String getVolunteerId() {
		return volunteerId;
	}

	public void setVolunteerId(String volunteerId) {
		this.volunteerId = volunteerId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	private MultiValueMap<String, String> getBasicProperties() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("timeStamp", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(timestamp));
		map.add("taskId", taskId);
		map.add("marketplaceId", marketplaceId);
		return map;
	}
	
	public MultiValueMap<String, String> getTaskInteractionProperties() {
		MultiValueMap<String, String> map = getBasicProperties();
		map.add("taskInteractionType", taskInteractionType);
		map.add("volunteerId", volunteerId); 
		return map;
	}
	
	public MultiValueMap<String, String> getPublishedTaskProperties() {
		return getBasicProperties();
	}
	
	public MultiValueMap<String, String> getFinishedTaskProperties() {
		MultiValueMap<String, String> map = getBasicProperties();
		map.add("volunteerId", getVolunteerId());
		return map;
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
