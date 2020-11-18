package at.jku.cis.iVolunteer.configurator.model._httprequests;

import java.util.List;

public class FrontendClassConfiguratorRequestBody {
	private String action;
	private List<String> idsToDelete;
//	private String idToSave;
	private SaveClassConfigurationRequest saveRequest;
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
//	public String getIdToSave() {
//		return idToSave;
//	}
//	public void setIdToSave(String idToSave) {
//		this.idToSave = idToSave;
//	}
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
