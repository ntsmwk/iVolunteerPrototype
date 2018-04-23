package at.jku.cis.trustifier.blockchain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleHash {
	private String value;

	public SimpleHash() {
	}

	public SimpleHash(String hash) {
		this.value = hash;
	}

	public String getHash() {
		return value;
	}

	public void setHash(String hash) {
		this.value = hash;
	}

	@Override
	public String toString() {
		return "SimpleHash [hash=" + value + "]";
	}

}
