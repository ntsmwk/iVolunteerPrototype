package at.jku.cis.iVolunteer.model.property;

import java.util.LinkedList;
import java.util.List;

import at.jku.cis.iVolunteer.model.property.rule.MultiRule;


public class MultipleProperty extends Property {

	List<Property> properties;
	
	List<MultiRule> rules;
	

	public MultipleProperty() {
		kind = PropertyKind.MULTIPLE;
	}
	
	public MultipleProperty(Property p) {
		kind = PropertyKind.MULTIPLE;
		super.id = p.id;
		super.kind = p.kind;
		super.name = p.name;
//		super.rules = p.rules;
	}
	
	public MultipleProperty(MultiplePropertyRet p) {
		this.id = p.id;
		this.kind = p.kind;
		this.name = p.name;
		this.order = p.order;
//		this.rules = p.rules;
		this.custom = true;
		this.properties = new LinkedList<>();
	}

	public List<Property> getProperties() {
		return properties;
	}
	
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	
	public List<MultiRule> getRules() {
		return rules;
	}
	
	public void setRules(List<MultiRule> rules) {
		this.rules = rules;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MultipleProperty)) {
			return false;
		}
		return ((MultipleProperty) obj).id.equals(id);
	}

	@Override
	public String toString() {
		return "MultipleProperty [properties=" + properties + ", id=" + id + ", name=" + name + ", rules=" + rules
				+ ", kind=" + kind + ", order=" + order + ", custom=" + custom + "]";
	}
	
	

	
	
	
}
