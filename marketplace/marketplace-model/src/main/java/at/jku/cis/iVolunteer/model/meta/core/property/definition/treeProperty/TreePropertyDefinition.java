package at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.IVolunteerObject;

@Document
public class TreePropertyDefinition extends IVolunteerObject {

	private String name;

	private List<TreePropertyEntry> enumEntries;
	private List<TreePropertyRelationship> enumRelationships;
	
	private boolean multiple;
	private boolean required;

	public TreePropertyDefinition() {
		this.enumEntries = new ArrayList<TreePropertyEntry>();
		this.enumRelationships = new ArrayList<TreePropertyRelationship>();
		this.setTimestamp(new Date());
	}

	public TreePropertyDefinition(String name, String tenantId) {
		this.name = name;
		this.enumEntries = new ArrayList<TreePropertyEntry>();
		this.enumRelationships = new ArrayList<TreePropertyRelationship>();
		this.setTenantId(tenantId);
		this.setTimestamp(new Date());
	}

	public TreePropertyDefinition(String name, String description, boolean multiple, String tenantId) {
		this.name = name;
		this.description = description;
		this.multiple = multiple;
		this.enumEntries = new ArrayList<TreePropertyEntry>();
		this.enumRelationships = new ArrayList<TreePropertyRelationship>();
		this.setTenantId(tenantId);
		this.setTimestamp(new Date());
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TreePropertyEntry> getEnumEntries() {
		return enumEntries;
	}

	public void setEnumEntries(List<TreePropertyEntry> enumEntries) {
		this.enumEntries = enumEntries;
	}

	public List<TreePropertyRelationship> getEnumRelationships() {
		return enumRelationships;
	}

	public void setEnumRelationships(List<TreePropertyRelationship> enumRelationships) {
		this.enumRelationships = enumRelationships;
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

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
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
