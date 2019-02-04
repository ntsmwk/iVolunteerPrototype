package at.jku.cis.iVolunteer.model.property.dto;

import at.jku.cis.iVolunteer.model.property.PropertyKind;

public class PropertyListItemDTO<T> {
	String id;
	String name;
	T value;
	PropertyKind kind;
	
	
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
	
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	
	public PropertyKind getKind() {
		return kind;
	}
	public void setKind(PropertyKind kind) {
		this.kind = kind;
	}
	
	
	
}
