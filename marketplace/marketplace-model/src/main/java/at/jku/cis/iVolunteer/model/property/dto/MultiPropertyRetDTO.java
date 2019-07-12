package at.jku.cis.iVolunteer.model.property.dto;

import java.util.List;

import com.mongodb.util.JSON;

import at.jku.cis.iVolunteer.model.configurable.configurables.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;

public class MultiPropertyRetDTO {

	
	String id;
	String name;
	
	PropertyKind kind;
	
	List<RuleDTO> rules;
	List<String> propertyIDs;
	
	int order;
	
	
	
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

	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
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
	
	public List<String> getPropertyIDs() {
		return propertyIDs;
	}
	
	public void setPropertyIDs(List<String> propertyIDs) {
		this.propertyIDs = propertyIDs;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MultiPropertyRetDTO)) {
			return false;
		}
		return ((MultiPropertyRetDTO) obj).id.equals(id);
	
	}
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	@Override
	public String toString() {
		return "MultiplePropertyRetDTO [id=" + id + ", name=" + name + ", kind=" + kind + ",\nrules=" + rules
				+ ",\npropertyIDs=" + propertyIDs + ", order=" + order + "]";
	}

	
	
	
	

	
	
	
	
	
	
}
