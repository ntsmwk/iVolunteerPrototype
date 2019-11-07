//package at.jku.cis.iVolunteer.model.meta.core.property.instance.old;
//
//import java.util.List;
//
//import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
//import at.jku.cis.iVolunteer.model.property.rule.MultiPropertyRule;
//
//
//public class MultiProperty extends Property {
//
//	List<Property> properties;
//	
//	List<MultiPropertyRule> rules;
//	
//
//	public MultiProperty() {
//		type = PropertyType.MULTI;
//	}
//	
//	public MultiProperty(Property p) {
//		type = PropertyType.MULTI;
//		super.id = p.getId();
//		super.type = p.type;
//		super.name = p.name;
//		super.order = p.order;
//		super.custom = p.custom;
//	}
//	
////	public MultiProperty(MultiPropertyRet p) {
////		this.id = p.id;
////		this.type = p.type;
////		this.name = p.name;
////		this.order = p.order;
////		this.custom = true;
////		this.properties = new LinkedList<>();
////	}
//
//	public List<Property> getProperties() {
//		return properties;
//	}
//	
//	public void setProperties(List<Property> properties) {
//		this.properties = properties;
//	}
//	
//	public List<MultiPropertyRule> getRules() {
//		return rules;
//	}
//	
//	public void setRules(List<MultiPropertyRule> rules) {
//		this.rules = rules;
//	}
//	
//	@Override
//	public boolean equals(Object obj) {
//		if (!(obj instanceof MultiProperty)) {
//			return false;
//		}
//		return ((MultiProperty) obj).id.equals(id);
//	}
//
//	@Override
//	public String toString() {
//		return "MultiProperty [properties=" + properties + ", id=" + id + ", name=" + name + ", rules=" + rules
//				+ ", kind=" + type + ", order=" + order + ", custom=" + custom + "]";
//	}
//	
//	
//
//	
//	
//	
//}
