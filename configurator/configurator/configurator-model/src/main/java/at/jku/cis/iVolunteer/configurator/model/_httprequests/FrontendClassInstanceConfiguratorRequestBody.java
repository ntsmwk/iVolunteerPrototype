package at.jku.cis.iVolunteer.configurator.model._httprequests;

import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassInstance;

public class FrontendClassInstanceConfiguratorRequestBody {
	ClassInstance classInstance;
	String url;
	
	public ClassInstance getClassInstance() {
		return classInstance;
	}
	public void setClassInstance(ClassInstance classInstance) {
		this.classInstance = classInstance;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
