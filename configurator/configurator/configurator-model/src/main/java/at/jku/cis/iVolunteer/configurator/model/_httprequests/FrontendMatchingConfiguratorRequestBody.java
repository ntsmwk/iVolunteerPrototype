package at.jku.cis.iVolunteer.configurator.model._httprequests;

import java.util.List;

public class FrontendMatchingConfiguratorRequestBody {
	private String action;
	private List<String> idsToDelete;
	private SaveMatchingConfigurationRequest saveRequest;
	private String url;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<String> getIdsToDelete() {
		return idsToDelete;
	}
	public void setIdsToDelete(List<String> idsToDelete) {
		this.idsToDelete = idsToDelete;
	}
	public SaveMatchingConfigurationRequest getSaveRequest() {
		return saveRequest;
	}
	public void setSaveRequest(SaveMatchingConfigurationRequest saveRequest) {
		this.saveRequest = saveRequest;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
