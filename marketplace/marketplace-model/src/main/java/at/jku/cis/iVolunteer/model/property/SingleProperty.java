package at.jku.cis.iVolunteer.model.property;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.rule.Rule;

@Document
public class SingleProperty<T> extends Property {

	T value;
	List<ListEntry<T>> values;
	
	T defaultValue;
	
	List<ListEntry<T>> legalValues;
	
	public SingleProperty() {
		
	}
	
	public SingleProperty(Property p) {
		super();
		super.id = p.id;
		super.kind = p.kind;
		super.name = p.name;
		super.rules = p.rules;
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

	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public List<ListEntry<T>> getValues() {
		return values;
	}
	
	public void setValues(List<ListEntry<T>> values) {
		this.values = values;
	}

	public T getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	public List<ListEntry<T>> getLegalValues() {
		return legalValues;
	}
	
	public void setLegalValues(List<ListEntry<T>> legalValues) {
		this.legalValues = legalValues;
	}

	public List<Rule> getRules() {
		return rules;
	}
	
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public PropertyKind getKind() {
		return kind;
	}
	
	public void setKind(PropertyKind kind) {
		this.kind = kind;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SingleProperty<?>)) {
			return false;
		}
		return ((SingleProperty<?>) obj).id.equals(id);
	
	}
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	
}
