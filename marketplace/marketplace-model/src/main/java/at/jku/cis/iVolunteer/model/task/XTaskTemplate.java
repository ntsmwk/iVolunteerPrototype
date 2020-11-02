package at.jku.cis.iVolunteer.model.task;

import java.util.ArrayList;
import java.util.List;

public class XTaskTemplate {

	private String id;
	private String tenantId;
	private String title;
	private String description;
	private String configurationName;
	private List<XDynamicFieldBlock> dynamicBlocks = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<XDynamicFieldBlock> getDynamicBlocks() {
		return dynamicBlocks;
	}

	public void setDynamicBlocks(List<XDynamicFieldBlock> dynamicBlocks) {
		this.dynamicBlocks = dynamicBlocks;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

}
