package at.jku.cis.iVolunteer.model.property;

import java.util.List;

import at.jku.cis.iVolunteer.model.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.rule.Rule;

public class MultiplePropertyRet {

	
	String id;
	String name;
	
	PropertyKind kind;
	
	List<Rule> rules;
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
	
	public List<Rule> getRules() {
		return rules;
	}
	
	public void setRules(List<Rule> rules) {
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
		if (!(obj instanceof MultiplePropertyRet)) {
			return false;
		}
		return ((MultiplePropertyRet) obj).id.equals(id);
	
	}
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}


	
	
	
	

	
	
	
	
	
	
}
