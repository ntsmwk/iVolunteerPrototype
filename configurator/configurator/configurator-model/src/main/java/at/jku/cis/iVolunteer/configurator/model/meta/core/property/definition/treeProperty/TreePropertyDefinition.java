package at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.configurator.model.IVolunteerObject;

@Document
public class TreePropertyDefinition extends IVolunteerObject {

	private String name;

	private List<TreePropertyEntry> entries;
	private List<TreePropertyRelationship> relationships;
	
	private boolean multiple;
	private boolean required;
	private String requiredMessage;
	private boolean custom;

	public TreePropertyDefinition() {
		this.entries = new ArrayList<TreePropertyEntry>();
		this.relationships = new ArrayList<TreePropertyRelationship>();
		this.setTimestamp(new Date());
	}

	public TreePropertyDefinition(String name, String tenantId) {
		this.name = name;
		this.entries = new ArrayList<TreePropertyEntry>();
		this.relationships = new ArrayList<TreePropertyRelationship>();
		this.setTenantId(tenantId);
		this.setTimestamp(new Date());
	}

	public TreePropertyDefinition(String name, String description, boolean multiple, String tenantId) {
		this.name = name;
		this.description = description;
		this.multiple = multiple;
		this.entries = new ArrayList<TreePropertyEntry>();
		this.relationships = new ArrayList<TreePropertyRelationship>();
		this.setTenantId(tenantId);
		this.setTimestamp(new Date());
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TreePropertyEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<TreePropertyEntry> entries) {
		this.entries = entries;
	}

	public List<TreePropertyRelationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<TreePropertyRelationship> relationships) {
		this.relationships = relationships;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public String getRequiredMessage() {
		return requiredMessage;
	}

	public void setRequiredMessage(String requiredMessage) {
		this.requiredMessage = requiredMessage;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isCustom() {
		return custom;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TreePropertyDefinition)) {
			return false;
		}
		return ((TreePropertyDefinition) obj).id.equals(id);
	}

}
