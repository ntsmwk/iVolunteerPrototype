package at.jku.cis.iVolunteer.model.configurable.configurables.property;

import java.util.LinkedList;
import java.util.List;

import at.jku.cis.iVolunteer.model.property.rule.MultiPropertyRule;


public class MultiProperty extends Property {

	List<Property> properties;
	
	List<MultiPropertyRule> rules;
	

	public MultiProperty() {
		kind = PropertyKind.MULTI;
	}
	
	public MultiProperty(Property p) {
		kind = PropertyKind.MULTI;
		super.id = p.getId();
		super.kind = p.kind;
		super.name = p.name;
		super.order = p.order;
	}
	
	public MultiProperty(MultiPropertyRet p) {
		this.id = p.id;
		this.kind = p.kind;
		this.name = p.name;
		this.order = p.order;
		this.custom = true;
		this.properties = new LinkedList<>();
	}

	public List<Property> getProperties() {
		return properties;
	}
	
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	
	public List<MultiPropertyRule> getRules() {
		return rules;
	}
	
	public void setRules(List<MultiPropertyRule> rules) {
		this.rules = rules;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MultiProperty)) {
			return false;
		}
		return ((MultiProperty) obj).id.equals(id);
	}

	@Override
	public String toString() {
		return "MultiProperty [properties=" + properties + ", id=" + id + ", name=" + name + ", rules=" + rules
				+ ", kind=" + kind + ", order=" + order + ", custom=" + custom + "]";
	}
	
	

	
	
	
}
