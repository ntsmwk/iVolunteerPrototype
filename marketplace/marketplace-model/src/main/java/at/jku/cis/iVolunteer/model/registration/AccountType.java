package at.jku.cis.iVolunteer.model.registration;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AccountType {
	ORGANIZATION("ORGANIZATION"), PERSON("PERSON"), ADMIN("ADMIN");
	
	private final String type;
	
	private AccountType(String type) {
		this.type = type;
	}
	

	public String getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return type;
	}

	@JsonCreator
	public static AccountType getFromAccountType(String type) {
		for (AccountType t : AccountType.values()) {
			if (t.getType().equals(type)) {
				return t;
			}
		}
		throw new IllegalArgumentException();

	}
}
