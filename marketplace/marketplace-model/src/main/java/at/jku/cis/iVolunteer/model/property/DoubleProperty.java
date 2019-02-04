package at.jku.cis.iVolunteer.model.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.rule.Rule;

@Document
public class DoubleProperty extends Property<Double> {
	
//	@Id
//	String id = super.id;
//	
//	String name;
//	double value;
//	
//	double defaultValue;
//	
//	//List<Double> legalValues;
//	List<Rule> rules;
//	
//	PropertyKind kind;
	
	public DoubleProperty() {
		kind = PropertyKind.FLOAT_NUMBER;
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
	public Double getValue() {
		return value;
	}

	@Override
	public void setValue(Double value) {
		this.value = value;
	}
	
	@Override
	public List<ListEntry<Double>> getValues() {
		return values;
	}

	@Override
	public void setValues(List<ListEntry<Double>> values) {
		this.values = values;
	}

	@Override
	public Double getDefaultValue() {
		return defaultValue;
	}

	@Override
	public void setDefaultValue(Double defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public List<ListEntry<Double>> getLegalValues() {
		return legalValues;
	}
	
	@Override
	public void setLegalValues(List<ListEntry<Double>> legalValues) {
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
		if (!(obj instanceof DoubleProperty)) {
			return false;
		}
		return ((DoubleProperty) obj).id.equals(id);
	}

}
