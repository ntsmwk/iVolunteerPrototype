package at.jku.csi.marketplace.task;

import java.util.Date;

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
		StringBuilder sb = new StringBuilder();
		sb.append(interactionId);
		sb.append(taskId);
		sb.append(participantId);
		sb.append(timestamp);
		return sb.toString();
	}

}
