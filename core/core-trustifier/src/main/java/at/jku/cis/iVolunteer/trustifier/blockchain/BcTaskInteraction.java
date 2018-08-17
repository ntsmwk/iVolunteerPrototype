package at.jku.cis.iVolunteer.trustifier.blockchain;

import java.util.Date;

public class BcTaskInteraction {
	private String hash;
	private Date timestamp;
	private String taskId;
	private String marketplaceId;
	// private String volunteerId;
	private String taskInteractionType;

	public BcTaskInteraction() {

	}

	public BcTaskInteraction(String hash, Date timestamp, String taskId, String marketplaceId,
			String taskInteractionType) {
		super();
		this.hash = hash;
		this.timestamp = timestamp;
		this.taskId = taskId;
		this.marketplaceId = marketplaceId;
		this.taskInteractionType = taskInteractionType;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public String getTaskInteractionType() {
		return taskInteractionType;
	}

	public void setTaskInteractionType(String taskInteractionType) {
		this.taskInteractionType = taskInteractionType;
	}

	@Override
	public String toString() {
		return "BcTaskInteraction [hash=" + hash + ", timestamp=" + timestamp + ", taskId=" + taskId
				+ ", marketplaceId=" + marketplaceId + ", taskInteractionType=" + taskInteractionType + "]";
	}

}
