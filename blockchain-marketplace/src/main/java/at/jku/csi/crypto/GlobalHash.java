package at.jku.csi.crypto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GlobalHash {
	private String userId;
	private String value;
	
	public GlobalHash() {	
	}
	
	public GlobalHash(String userId, String hash) {
		this.userId = userId;
		this.value = hash;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHash() {
		return value;
	}

	public void setHash(String hash) {
		this.value = hash;
	}

	@Override
	public String toString() {
		return "GlobalHash [userId=" + userId + ", hash=" + value + "]";
	}

}
