package at.jku.cis.iVolunteer.model.task;

import at.jku.cis.iVolunteer.model.core.tenant.XTenantSerialized;
import at.jku.cis.iVolunteer.model.user.XUser;

public class XTaskCertificate {
	
	String id;
	XUser user;
	String taskId;
	XTenantSerialized tenantSerialized;
	XTaskSerialized taskSerialized;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public XUser getUser() {
		return user;
	}
	public void setUser(XUser user) {
		this.user = user;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public XTenantSerialized getTenantSerialized() {
		return tenantSerialized;
	}
	public void setTenantSerialized(XTenantSerialized tenantSerialized) {
		this.tenantSerialized = tenantSerialized;
	}
	public XTaskSerialized getTaskSerialized() {
		return taskSerialized;
	}
	public void setTaskSerialized(XTaskSerialized taskSerialized) {
		this.taskSerialized = taskSerialized;
	}

	

}
