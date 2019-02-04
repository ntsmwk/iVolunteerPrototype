package at.jku.cis.iVolunteer.model.property.dto;

import java.util.List;

import at.jku.cis.iVolunteer.model.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;

public class TextPropertyDTO {
	

	String id;
	
	String name;
	String value;
	
	String defaultValue;
	
	List<ListEntryDTO<String>> legalValues;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public List<ListEntryDTO<String>> getLegalValues() {
		return legalValues;
	}
	
	public void setLegalValues(List<ListEntryDTO<String>> legalValues) {
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
		if (!(obj instanceof TextPropertyDTO)) {
			return false;
		}
		return ((TextPropertyDTO) obj).id.equals(id);
	}

	

}
