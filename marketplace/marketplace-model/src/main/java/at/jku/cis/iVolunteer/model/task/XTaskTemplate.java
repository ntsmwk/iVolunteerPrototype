package at.jku.cis.iVolunteer.model.task;

import java.util.ArrayList;
import java.util.List;

public class XTaskTemplate {

	String id;
	String tenant;
	List<XDynamicFieldBlock> dynamicFields = new ArrayList<>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTenant() {
		return tenant;
	}
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
	public List<XDynamicFieldBlock> getDynamicFields() {
		return dynamicFields;
	}
	public void setDynamicFields(List<XDynamicFieldBlock> dynamicFields) {
		this.dynamicFields = dynamicFields;
	}
	

	
	

}
