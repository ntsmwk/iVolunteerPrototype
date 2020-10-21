package at.jku.cis.iVolunteer.model._httprequests;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;

public class GetClassAndTaskInstancesRequest {
	
	Tenant tenant;
	CoreUser user;
	boolean onlyOpened;

	public GetClassAndTaskInstancesRequest() {
	}

	public GetClassAndTaskInstancesRequest(Tenant tenant, CoreUser user, boolean onlyOpened) {
		this.tenant = tenant;
		this.user = user;
		this.onlyOpened = onlyOpened;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public CoreUser getUser() {
		return user;
	}

	public void setUser(CoreUser user) {
		this.user = user;
	}

	public boolean isOnlyOpened() {
		return onlyOpened;
	}

	public void setOnlyOpened(boolean onlyOpened) {
		this.onlyOpened = onlyOpened;
	}
	
	



}
