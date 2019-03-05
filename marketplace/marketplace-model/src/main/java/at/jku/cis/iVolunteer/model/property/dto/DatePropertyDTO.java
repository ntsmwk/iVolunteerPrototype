// TODO remove
//
//package at.jku.cis.iVolunteer.model.property.dto;
//
//import java.util.Date;
//import java.util.List;
//
//
//import at.jku.cis.iVolunteer.model.property.PropertyKind;
//import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;
//import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;
//
//
//public class DatePropertyDTO {
//
//	String id;
//	
//	String name;
//	Date value;
//	
//	Date defaultValue;
//	
//	List<ListEntryDTO<Date>> legalValues;
//	List<RuleDTO> rules;
//	
//	PropertyKind kind;
//
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
//	public Date getValue() {
//		return value;
//	}
//
//	public void setValue(Date value) {
//		this.value = value;
//	}
//
//	public Date getDefaultValue() {
//		return defaultValue;
//	}
//
//	public void setDefaultValue(Date defaultValue) {
//		this.defaultValue = defaultValue;
//	}
//
//	public List<ListEntryDTO<Date>> getLegalValues() {
//		return legalValues;
//	}
//	
//	public void setLegalValues(List<ListEntryDTO<Date>> legalValues) {
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
//	@Override
//	public boolean equals(Object obj) {
//		if (!(obj instanceof DatePropertyDTO)) {
//			return false;
//		}
//		return ((DatePropertyDTO) obj).id.equals(id);
//	}
//	
//	@Override
//	public int hashCode() {
//		return id.hashCode();
//	}
//
//	
//
//}
