package at.jku.cis.iVolunteer.trustifier.blockchain;

import java.util.Date;

public class BcPublishedTask {
	private String hash;
	private Date timestamp;
	private String taskId;
	private String marketplaceId;
	
	public BcPublishedTask() {
		
	}

	public BcPublishedTask(String hash, Date timestamp, String taskId, String marketplaceId) {
		super();
		this.hash = hash;
		this.timestamp = timestamp;
		this.taskId = taskId;
		this.marketplaceId = marketplaceId;
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

}
