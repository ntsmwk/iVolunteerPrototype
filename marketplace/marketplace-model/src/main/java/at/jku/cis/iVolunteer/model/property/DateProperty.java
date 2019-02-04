package at.jku.cis.iVolunteer.model.property;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.rule.Rule;

@Document
public class DateProperty extends Property<Date> {

//	@Id
//	String id;
//	
//	String name;
//	Date value;
//	
//	Date defaultValue;
//	
////	List<Date> legalValues;
//	List<Rule> rules;
//	
//	PropertyKind kind;
	
	public DateProperty() {
		kind = PropertyKind.DATE;
	}
	
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
	public Date getValue() {
		return value;
	}

	@Override
	public void setValue(Date value) {
		this.value = value;
	}
	
	@Override
	public List<ListEntry<Date>> getValues() {
		return values;
	}

	@Override
	public void setValues(List<ListEntry<Date>> values) {
		this.values = values;
	}

	@Override
	public Date getDefaultValue() {
		return defaultValue;
	}

	@Override
	public void setDefaultValue(Date defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public List<ListEntry<Date>> getLegalValues() {
		return legalValues;
	}
	
	@Override
	public void setLegalValues(List<ListEntry<Date>> legalValues) {
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
		if (!(obj instanceof DateProperty)) {
			return false;
		}
		return ((DateProperty) obj).id.equals(id);
	}

	

}
