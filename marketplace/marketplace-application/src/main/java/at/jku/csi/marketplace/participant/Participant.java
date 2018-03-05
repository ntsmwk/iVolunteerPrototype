package at.jku.csi.marketplace.participant;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Participant {

	@Id
	private String id;

	private String username;
	@JsonIgnore
	private String password;
	private ParticipantProfile profile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public ParticipantProfile getProfile() {
		return profile;
	}

	public void setProfile(ParticipantProfile profile) {
		this.profile = profile;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Participant && this.getId().equals(((Participant)obj).getId())) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

}
