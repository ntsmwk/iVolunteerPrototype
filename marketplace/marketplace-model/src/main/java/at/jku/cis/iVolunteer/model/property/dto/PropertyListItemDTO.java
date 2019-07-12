package at.jku.cis.iVolunteer.model.property.dto;

import java.util.List;

import at.jku.cis.iVolunteer.model.configurable.configurables.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;

public class PropertyListItemDTO<T> {
	String id;
	String name;
	List<ListEntryDTO<T>> values;
	List<ListEntryDTO<T>> defaultValues;
	PropertyKind kind;
	int order;
	boolean custom;
	
	
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
	
	public List<ListEntryDTO<T>> getValues() {
		return values;
	}
	public void setValues(List<ListEntryDTO<T>> values) {
		this.values = values;
	}

	public List<ListEntryDTO<T>> getDefaultValues() {
		return defaultValues;
	}
	public void setDefaultValues(List<ListEntryDTO<T>> defaultValues) {
		this.defaultValues = defaultValues;
	}
	
	public PropertyKind getKind() {
		return kind;
	}
	public void setKind(PropertyKind kind) {
		this.kind = kind;
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
	
	
	
}
