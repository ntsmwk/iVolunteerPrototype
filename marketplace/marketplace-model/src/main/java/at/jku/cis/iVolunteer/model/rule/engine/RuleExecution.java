package at.jku.cis.iVolunteer.model.rule.engine;

import at.jku.cis.iVolunteer.model.user.Volunteer;

public class RuleExecution {
	
	private Volunteer volunteer;
	private RuleStatus status;
	private int timesFired;
	
	public RuleExecution(Volunteer volunteer) {
		this.volunteer = volunteer;
		status = RuleStatus.NOT_FIRED;
		timesFired = 0;
	}
	
	public void setStatus(String status) {
		this.status = RuleStatus.valueOf(status);
		timesFired++;
	}
	
	public void setStatus(RuleStatus status) {
		this.status = status;
	}
	
	public RuleStatus getStatus() {
		return status;
	}
	
	public void setFired() {
		status = RuleStatus.FIRED;
		timesFired++;
	}
	
	public int timesFired() {
		return timesFired;
	}
	
	public String toString() {
		return "vol: " + volunteer.getUsername() + " - rules fired: " + timesFired;
	}
	

}
