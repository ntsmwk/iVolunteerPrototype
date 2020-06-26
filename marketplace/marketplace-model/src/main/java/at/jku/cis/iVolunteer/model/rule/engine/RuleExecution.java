package at.jku.cis.iVolunteer.model.rule.engine;

import at.jku.cis.iVolunteer.model.user.Volunteer;

public class RuleExecution {
	
	private Volunteer volunteer;
	private RuleStatus status;
	private int timesFired;
	
	public RuleExecution() {
		status = RuleStatus.NOT_FIRED;
		
	}
	public RuleExecution(Volunteer volunteer) {
		this();
		this.volunteer = volunteer;
		timesFired = 0;
	}
	
	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}
	
	public Volunteer getVolunteer() {
		return volunteer;
	}
	
	public void setStatus(String status) {
		System.out.println("Status: " + status);
		this.status = RuleStatus.valueOf(status);
		if (this.status == RuleStatus.FIRED)
			timesFired++;
	}
	
	public void setStatus(RuleStatus status) {
		System.out.println("RuleStatus: " + status);
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
