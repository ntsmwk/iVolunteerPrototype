package at.jku.cis.iVolunteer.model.property.dto;

import java.util.List;

import at.jku.cis.iVolunteer.model.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;


public class TransferPropertyDTO {
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
	
	public List<RuleDTO> getRules() {
		return rules;
	}
	public void setRules(List<RuleDTO> rules) {
		this.rules = rules;
	}
	
	public PropertyKind getKind() {
		return kind;
	}
	public void setKind(PropertyKind kind) {
		this.kind = kind;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TransferPropertyDTO)) {
			return false;
		}
		return ((TransferPropertyDTO) obj).id.equals(id);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + ": " + value + " Kind: " + kind +  " DEFAULT: " + defaultValue + "\n RULES \n");
		if (rules != null) {
			for (RuleDTO r : rules) {
				sb.append("\tRULE " + r.getKind());
			}
		}
		return sb.toString();
		
	}
	
	
	
	
	
	
}
