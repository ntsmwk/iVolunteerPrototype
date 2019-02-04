package at.jku.cis.iVolunteer.model.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.rule.Rule;

@Document
public class TextProperty extends Property<String> {
	
//	private final String EMPTY = "";
//	
//	@Id
//	String id = super.id;
//	
//	String name = EMPTY;
//	String value = EMPTY;
//	
//	String defaultValue = EMPTY;
//	
////	List<ListEntryString> legalValues;
////	List<ListEntryString> values;
//	List<Rule> rules;
//	
//	PropertyKind kind;
	
//	public TextProperty() {
//		kind = PropertyKind.TEXT;
//	}
//	
//	public TextProperty(String defaultValue) {
//		kind = PropertyKind.TEXT;
//		this.defaultValue = defaultValue;
//		this.value = defaultValue;
//	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public List<ListEntry<String>> getValues() {
		return values;
	}

	@Override
	public void setValues(List<ListEntry<String>> values) {
		this.values = values;
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

	@Override
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public List<ListEntry<String>> getLegalValues() {
		return legalValues;
	}
	
	@Override
	public void setLegalValues(List<ListEntry<String>> legalValues) {
		this.legalValues = legalValues;
		
	}
	
	@Override
	public PropertyKind getKind() {
		return kind;
	}
	@Override
	public void setKind(PropertyKind kind) {
		this.kind = kind;
	}
	
	@Override
	public List<Rule> getRules() {
		return rules;
	}
	
	@Override
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TextProperty)) {
			return false;
		}
		return ((TextProperty) obj).id.equals(id);
	}

	

}
