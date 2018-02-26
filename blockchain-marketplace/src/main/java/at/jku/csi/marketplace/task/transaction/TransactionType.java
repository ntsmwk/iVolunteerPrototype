package at.jku.csi.marketplace.task.transaction;

import java.util.Arrays;

public enum TransactionType {
	RESERVATION("Reservation"), ASSIGNMENT("Assignment");

	private String value;

	TransactionType(String value) {
		this.value = value;
	}

	public String getNumVal() {
		return value;
	}

	public static TransactionType fromValue(String value) {
		for (TransactionType transactionType : values()) {
			if (transactionType.value.equalsIgnoreCase(value)) {
				return transactionType;
			}
		}
		throw new IllegalArgumentException("Unknown enum type " + value + ", Allowed values are "
				+ Arrays.toString(values()));
	}
}
