package at.jku.cis.iVolunteer.model._httprequests;

public class GetAllTaskCertificateRequest {

	// taskType: 'SUBSCRIBED', 'UNSUBSCRIBED' (OHNE PARAMETER IST: DEFAULT ALL)
	// status: 'OPEN', 'CLOSED', (OHNE PARAMETER IST: DEFAULT ALL)

	String taskType = "ALL";
	String status = "ALL";
	
	public GetAllTaskCertificateRequest() {
	}


	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
