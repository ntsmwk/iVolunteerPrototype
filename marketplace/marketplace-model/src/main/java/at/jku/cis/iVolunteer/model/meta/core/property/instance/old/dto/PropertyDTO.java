package at.jku.cis.iVolunteer.model.meta.core.property.instance.old.dto;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;

public class PropertyDTO<T> {
	String id;
	String name;
	
	List<RuleDTO> rules;
	
	PropertyType type;

	int order;
	boolean custom;
	
	List<ListEntryDTO<T>> values;
	List<ListEntryDTO<T>> defaultValues;
	List<ListEntryDTO<T>> legalValues;
	
	List<PropertyDTO<T>> properties;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<ListEntryDTO<T>> getDefaultValues() {
		return defaultValues;
	}
	public void setDefaultValues(List<ListEntryDTO<T>> defaultValues) {
		this.defaultValues = defaultValues;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public boolean isCustom() {
		return custom;
	}
	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	public List<ListEntryDTO<T>> getValues() {
		return values;
	}
	public void setValues(List<ListEntryDTO<T>> values) {
		this.values = values;
	}
	public List<ListEntryDTO<T>> getLegalValues() {
		return legalValues;
	}
	public void setLegalValues(List<ListEntryDTO<T>> legalValues) {
		this.legalValues = legalValues;
	}

	
	public List<PropertyDTO<T>> getProperties() {
		return properties;
	}
	public void setProperties(List<PropertyDTO<T>> properties) {
		this.properties = properties;
	}
	public List<RuleDTO> getRules() {
		return rules;
	}
	public void setRules(List<RuleDTO> rules) {
		this.rules = rules;
	}

	public PropertyType getType() {
		return type;
	}
	public void setType(PropertyType type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PropertyDTO<?>)) {
			return false;
		}
		return ((PropertyDTO<?>) obj).id.equals(id);
	
	}
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	@Override
	public String toString() {
		return "PropertyDTO [id=" + id + ", name=" + name + ", rules=" + rules + ", kind=" + type + ", defaultValue="
				+ defaultValues + ", order=" + order + ", custom=" + custom + ", values=" + values + ", legalValues="
				+ legalValues + ", properties=" + properties + "]";
	}
	
	
	
	
	
}
