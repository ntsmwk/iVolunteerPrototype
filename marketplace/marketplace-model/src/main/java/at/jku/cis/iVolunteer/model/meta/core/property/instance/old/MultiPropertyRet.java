//package at.jku.cis.iVolunteer.model.meta.core.property.instance.old;
//
//import java.util.List;
//
//import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
//import at.jku.cis.iVolunteer.model.property.rule.MultiPropertyRule;
//
//public class MultiPropertyRet {
//
//	
//	String id;
//	String name;
//	
//	PropertyType type;
//	
//	List<MultiPropertyRule> rules;
//	List<String> propertyIDs;
//	
//	int order;
//	
//	
//	
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public int getOrder() {
//		return order;
//	}
//	
//	public void setOrder(int order) {
//		this.order = order;
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
//	public PropertyType getType() {
//		return type;
//	}
//	
//	public void setType(PropertyType type) {
//		this.type = type;
//	}
//	
//	public List<String> getPropertyIDs() {
//		return propertyIDs;
//	}
//	
//	public void setPropertyIDs(List<String> propertyIDs) {
//		this.propertyIDs = propertyIDs;
//	}
//	
//	@Override
//	public boolean equals(Object obj) {
//		if (!(obj instanceof MultiPropertyRet)) {
//			return false;
//		}
//		return ((MultiPropertyRet) obj).id.equals(id);
//	
//	}
//	@Override
//	public int hashCode() {
//		return this.id.hashCode();
//	}
//
//
//	
//	
//	
//	
//
//	
//	
//	
//	
//	
//	
//}
