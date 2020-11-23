package at.jku.cis.iVolunteer.configurator.model._httprequests;

import java.util.List;

public class FrontendClassConfiguratorRequestBody {
	private String action;
	private String actionContext;
	private List<String> idsToDelete;
	private SaveClassConfigurationRequest saveRequest;
	private String url;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getActionContext() {
		return actionContext;
	}
	public void setActionContext(String actionContext) {
		this.actionContext = actionContext;
	}
	public List<String> getIdsToDelete() {
		return idsToDelete;
	}
	public void setIdsToDelete(List<String> idsToDelete) {
		this.idsToDelete = idsToDelete;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public SaveClassConfigurationRequest getSaveRequest() {
		return saveRequest;
	}
	public void setSaveRequest(SaveClassConfigurationRequest saveRequest) {
		this.saveRequest = saveRequest;
	}
	
	
}
