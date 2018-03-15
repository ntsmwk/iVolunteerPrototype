package at.jku.csi.marketplace.task;

import java.util.Date;

import com.google.gson.JsonObject;

import at.jku.csi.marketplace.blockchain.IHashObject;

public class CompletedTask implements IHashObject {
	private String interactionId;
	private String taskId;
	private String participantId;
	private Date timestamp;

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

	@Override
	public String toHashString() {
		JsonObject json = new JsonObject();
		json.addProperty("interactionId", interactionId);
		json.addProperty("taskId", taskId);
		json.addProperty("participantId", participantId);
		json.addProperty("timestamp", timestamp.toString());

		return json.toString();
	}

}
