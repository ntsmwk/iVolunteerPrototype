package at.jku.cis.iVolunteer.trustifier.blockchain;

public class BcClassInstance {

	private String hash;
	private String volunteerId;

	public BcClassInstance() {
	}
	
	public BcClassInstance(String hash, String volunteerId) {
		super();
		this.setHash(hash);
		this.setVolunteerId(volunteerId);
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getVolunteerId() {
		return volunteerId;
	}

	public void setVolunteerId(String volunteerId) {
		this.volunteerId = volunteerId;
	}

}
