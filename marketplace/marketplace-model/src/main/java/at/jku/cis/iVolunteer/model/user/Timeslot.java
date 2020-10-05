package at.jku.cis.iVolunteer.model.user;

public class Timeslot {

	Weekday weekday;
	int fromHours1;
	int fromMins1;
	int toHours1 = 23;
	int toMins1 = 59;
	
	int fromHours2;
	int fromMins2;
	int toHours2;
	int toMins2;
	
	boolean active;
	boolean secondActive;

	public Weekday getWeekday() {
		return weekday;
	}

	public void setWeekday(Weekday weekday) {
		this.weekday = weekday;
	}

	public int getFromHours1() {
		return fromHours1;
	}

	public void setFromHours1(int fromHours1) {
		this.fromHours1 = fromHours1;
	}

	public int getFromMins1() {
		return fromMins1;
	}

	public void setFromMins1(int fromMins1) {
		this.fromMins1 = fromMins1;
	}

	public int getToHours1() {
		return toHours1;
	}

	public void setToHours1(int toHours1) {
		this.toHours1 = toHours1;
	}

	public int getToMins1() {
		return toMins1;
	}

	public void setToMins1(int toMins1) {
		this.toMins1 = toMins1;
	}

	public int getFromHours2() {
		return fromHours2;
	}

	public void setFromHours2(int fromHours2) {
		this.fromHours2 = fromHours2;
	}

	public int getFromMins2() {
		return fromMins2;
	}

	public void setFromMins2(int fromMins2) {
		this.fromMins2 = fromMins2;
	}

	public int getToHours2() {
		return toHours2;
	}

	public void setToHours2(int toHours2) {
		this.toHours2 = toHours2;
	}

	public int getToMins2() {
		return toMins2;
	}

	public void setToMins2(int toMins2) {
		this.toMins2 = toMins2;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isSecondActive() {
		return secondActive;
	}

	public void setSecondActive(boolean secondActive) {
		this.secondActive = secondActive;
	}
	
	

	
	
	
	
	
	
}
