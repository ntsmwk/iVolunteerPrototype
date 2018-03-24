package at.jku.csi.marketplace.participant;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Participant {

	@Id
	private String id;
	private String username;
	@JsonIgnore
	private String password;

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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Participant)) {
			return false;
		}
		return ((Participant) obj).id.equals(id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
