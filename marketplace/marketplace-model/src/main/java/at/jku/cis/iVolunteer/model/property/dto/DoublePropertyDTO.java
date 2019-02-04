package at.jku.cis.iVolunteer.model.property.dto;

import java.util.List;

import at.jku.cis.iVolunteer.model.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;

public class DoublePropertyDTO {
	
	String id;
	
	String name;
	double value;
	
	double defaultValue;
	
	List<ListEntryDTO<Double>> legalValues;
	List<RuleDTO> rules;
	
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

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Double defaultValue) {
		this.defaultValue = defaultValue;
	}

	public List<ListEntryDTO<Double>> getLegalValues() {
		return legalValues;
	}
	
	public void setLegalValues(List<ListEntryDTO<Double>> legalValues) {
		this.legalValues = legalValues;
		
	}
	
	public PropertyKind getKind() {
		return kind;
	}
	public void setKind(PropertyKind kind) {
		this.kind = kind;
	}
	
	public List<RuleDTO> getRules() {
		return rules;
	}
	
	public void setRules(List<RuleDTO> rules) {
		this.rules = rules;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DoublePropertyDTO)) {
			return false;
		}
		return ((DoublePropertyDTO) obj).id.equals(id);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
