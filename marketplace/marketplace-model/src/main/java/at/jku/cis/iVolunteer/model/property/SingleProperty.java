package at.jku.cis.iVolunteer.model.property;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.rule.SinglePropertyRule;

@Document
public class SingleProperty<T> extends Property {

	//T value;
	//Value Displayed of the Property in the Task/Template/etc.
	List<ListEntry<T>> values;
	
	//Default Value of the Property, can be changed when editing property, cannot be changed from inside a template/task/etc.
	List<ListEntry<T>> defaultValues;
	
	//Allowed Values
	List<ListEntry<T>> legalValues;
	
	List<SinglePropertyRule> rules;

	public SingleProperty() {
		
	}
	
	
	public SingleProperty(Property p) {
		super();
		super.id = p.id;
		super.kind = p.kind;
		super.name = p.name;
		super.order = p.order;
//		super.rules = p.rules;
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

	public List<ListEntry<T>> getValues() {
		return values;
	}
	
	public void setValues(List<ListEntry<T>> values) {
		this.values = values;
	}

	public List<ListEntry<T>> getDefaultValues() {
		return defaultValues;
	}
	
	public void setDefaultValues(List<ListEntry<T>> defaultValues) {
		this.defaultValues = defaultValues;
	}

	public List<ListEntry<T>> getLegalValues() {
		return legalValues;
	}
	
	public void setLegalValues(List<ListEntry<T>> legalValues) {
		this.legalValues = legalValues;
	}
		

	public List<SinglePropertyRule> getRules() {
		return rules;
	}
	
	public void setRules(List<SinglePropertyRule> rules) {
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
