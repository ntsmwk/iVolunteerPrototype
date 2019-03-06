
//TODO dead code - remove

//package at.jku.cis.iVolunteer.model.property.dto;
//
//import java.util.List;
//
//import at.jku.cis.iVolunteer.model.property.PropertyKind;
//import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;
//import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;
//
//public class BooleanPropertyDTO{
//	
//	String id;
//	
//	String name;
//	boolean value;
//	
//	boolean defaultValue;
//	
//	List<ListEntryDTO<Boolean>> legalValues;
//	List<RuleDTO> rules;
//	
//	PropertyKind kind;
//	
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public Boolean getValue() {
//		return value;
//	}
//
//	public void setValue(Boolean value) {
//		this.value = value;
//	}
//
//	public Boolean getDefaultValue() {
//		return defaultValue;
//	}
//
//	public void setDefaultValue(Boolean defaultValue) {
//		this.defaultValue = defaultValue;
//	}
//
//	public List<ListEntryDTO<Boolean>> getLegalValues() {
//		return legalValues;
//	}
//	
//	public void setLegalValues(List<ListEntryDTO<Boolean>> legalValues) {
//		this.legalValues = legalValues;
//		
//	}
//	
//	public PropertyKind getKind() {
//		return kind;
//	}
//	public void setKind(PropertyKind kind) {
//		this.kind = kind;
//	}
//	
//	public List<RuleDTO> getRules() {
//		return rules;
//	}
//	
//	public void setRules(List<RuleDTO> rules) {
//		this.rules = rules;
//	}
//	
//
//	public boolean equals(Object obj) {
//		if (!(obj instanceof BooleanPropertyDTO)) {
//			return false;
//		}
//		return ((BooleanPropertyDTO) obj).id.equals(id);
//	}
//
//	
//
//}
