package at.jku.csi.marketplace.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;

import at.jku.csi.marketplace.blockchain.IHashObject;

public class CompletedTask implements IHashObject {
	private String interactionId;
	private String taskId;
	private String taskName;
	private String taskDescription;
	private List<String> requiredComptences = new ArrayList<>();
	private List<String> acquirableComptences = new ArrayList<>();
	private String participantId;
	private Date timestamp;
	private JsonObject json;

	public JsonObject getJson() {
		return json;
	}

	public void setJson(JsonObject json) {
		this.json = json;
	}

	public CompletedTask() {
	}

	public String getInteractionId() {
		return interactionId;
	}

	public void setInteractionId(String interactionId) {
		this.interactionId = interactionId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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

	public List<String> getRequiredComptences() {
		return requiredComptences;
	}

	public void setRequiredComptences(List<String> requiredComptences) {
		this.requiredComptences = requiredComptences;
	}

	public List<String> getAcquirableComptences() {
		return acquirableComptences;
	}

	public void setAcquirableComptences(List<String> acquirableComptences) {
		this.acquirableComptences = acquirableComptences;
	}

	@Override
	public String toHashString() {
		// TODO: re-think (CompletedTaskBuilder)
		return json.toString();
	}

}
