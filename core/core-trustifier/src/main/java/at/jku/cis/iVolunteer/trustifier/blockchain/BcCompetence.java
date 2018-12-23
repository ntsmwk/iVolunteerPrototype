package at.jku.cis.iVolunteer.trustifier.blockchain;

import java.util.Date;

public class BcCompetence {

	private String hash;
	private Date timeStamp;
	private String competenceId;
	private String marketplaceId;
	private String volunteerId;
	
	public BcCompetence() {
		
	}

	public BcCompetence(String hash, Date timestamp, String competenceId, String marketplaceId, String volunteerId) {
		super();
		this.hash = hash;
		this.timeStamp = timestamp;
		this.competenceId = competenceId;
		this.marketplaceId = marketplaceId;
		this.volunteerId = volunteerId;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Date getTimestamp() {
		return timeStamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timeStamp = timestamp;
	}

	public String getCompetenceId() {
		return competenceId;
	}

	public void setCompetenceId(String competenceId) {
		this.competenceId = competenceId;
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

}
