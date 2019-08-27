package at.jku.cis.iVolunteer.model.meta.core.property.instance.old;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;


//@Document(collection = "configurableObject")
@Document
public class Property {
	@Id
	String id;
	String name;
	PropertyType type;
	int order;
	boolean custom;

	//TODO Matching Priority
	
	public Property() {
//		super.setConfigurableType("property");
	}
//
//	public String getConfigurableType() {
//		return super.getConfigurableType();
//	}
	
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

	public PropertyType getType() {
		return type;
	}
	
	public void setType(PropertyType type) {
		this.type = type;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public boolean isCustom() {
		return custom;
	}
	
	public void setCustom(boolean custom) {
		this.custom = custom;
	}
	
	public static void clearValues(Property p) {
		if (p instanceof SingleProperty) {
			((SingleProperty<?>) p).values.clear();
		} else if (p instanceof MultiProperty) {
			clearValues(p);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Property)) {
			return false;
		}
		return ((Property) obj).id.equals(id);
	
	}
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public String toString() {
		return "\nProperty [id=" + id + ", name=" + name + /*", rules=" + rules +*/ ", type=" + type + ", order=" + order
				+ ", custom=" + custom + "]\n";
	}


	
	

	
	

	
}
