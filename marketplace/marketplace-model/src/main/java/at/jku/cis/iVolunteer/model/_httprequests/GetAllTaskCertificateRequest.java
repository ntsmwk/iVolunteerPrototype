package at.jku.cis.iVolunteer.model._httprequests;

public class GetAllTaskCertificateRequest {

	//	taskType: 'SUBSCRIBED', 'UNSUBSCRIBED' (OHNE PARAMETER IST: DEFAULT ALL)
	//	onlyOpened: boolean (OHNE PARAMTER IST: DEFAULT true)
	
	String taskType = "ALL";
	boolean onlyOpened = true;
	
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public boolean isOnlyOpened() {
		return onlyOpened;
	}
	public void setOnlyOpened(boolean onlyOpened) {
		this.onlyOpened = onlyOpened;
	}
	
	
}
