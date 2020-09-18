package at.jku.cis.iVolunteer.model.rule.engine;

import at.jku.cis.iVolunteer.model.user.User;

public class RuleExecution {

	private User volunteer;
	private RuleStatus status;
	private int timesFired;

	public RuleExecution() {
		status = RuleStatus.NOT_FIRED;
	}

	public RuleExecution(User volunteer) {
		this();
		this.volunteer = volunteer;
		timesFired = 0;
	}

	public void setVolunteer(User volunteer) {
		this.volunteer = volunteer;
	}

	public User getVolunteer() {
		return volunteer;
	}

	public void setStatus(String status) {
		this.status = RuleStatus.valueOf(status);
		if (this.status == RuleStatus.FIRED)
			timesFired++;
	}

	public void setStatus(RuleStatus status) {
		this.status = status;
		if (this.status == RuleStatus.FIRED)
			timesFired++;
	}

	public RuleStatus getStatus() {
		return status;
	}

	public int timesFired() {
		return timesFired;
	}

	public void setTimesFired(int timesFired) {
		this.timesFired = timesFired;
	}

	public String toString() {
		return "vol: " + volunteer.getUsername() + " - rules fired: " + timesFired;
	}
}
