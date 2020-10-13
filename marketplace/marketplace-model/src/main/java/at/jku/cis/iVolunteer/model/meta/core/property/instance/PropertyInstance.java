package at.jku.cis.iVolunteer.model.meta.core.property.instance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.IVolunteerObject;
import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@Document
public class PropertyInstance<T> {

	@Id String id;
	String name;
	String description;
	
	List<T> values = new ArrayList<>();
	List<T> allowedValues = new ArrayList<>();
	
	private String unit;
	
	PropertyType type;

	int position;

	boolean immutable;
	boolean updateable;
	boolean required;

	List<PropertyConstraint<T>> propertyConstraints = new ArrayList<>();
	
	boolean visible;
	int tabId;
	int level;
	
	Date timestamp;

	public PropertyInstance() {
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<T> getValues() {
		return values;
	}

	public void setValues(List<T> values) {
		this.values = values;
	}

	public List<T> getAllowedValues() {
		return allowedValues;
	}

	public void setAllowedValues(List<T> allowedValues) {
		this.allowedValues = allowedValues;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public PropertyType getType() {
		return type;
	}

	public void setType(PropertyType type) {
		this.type = type;
	}

	public boolean isImmutable() {
		return immutable;
	}

	public void setImmutable(boolean immutable) {
		this.immutable = immutable;
	}

	public boolean isUpdateable() {
		return updateable;
	}

	public void setUpdateable(boolean updateable) {
		this.updateable = updateable;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public List<PropertyConstraint<T>> getPropertyConstraints() {
		return propertyConstraints;
	}

	public void setPropertyConstraints(List<PropertyConstraint<T>> propertyConstraints) {
		this.propertyConstraints = propertyConstraints;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getTabId() {
		return tabId;
	}

	public void setTabId(int tabId) {
		this.tabId = tabId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PropertyInstance<?>)) {
			return false;
		}
		return ((PropertyInstance<?>) obj).id.equals(id);
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	public void resetValues() {
		values = new ArrayList<T>();
	}

}
