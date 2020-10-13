package at.jku.cis.iVolunteer.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Weekday {
	MONDAY("MONDAY"), TUESDAY("TUESDAY"), WEDNESDAY("WEDNESDAY"), THURSDAY("THURSDAY"), FRIDAY("FRIDAY"), 
	SATURDAY("SATURDAY"), SUNDAY("SUNDAY");
	
	private final String day;

	private Weekday(String day) {
		this.day = day;
	}

	public String getWeekday() {
		return this.day;
	}

	@Override
	public String toString() {
		return day;
	}

	@JsonCreator
	public static Weekday getFromDay(String day) {
		for (Weekday weekday : Weekday.values()) {
			if (weekday.getWeekday().equals(day)) {
				return weekday;
			}
		}
		throw new IllegalArgumentException();
	}
}
