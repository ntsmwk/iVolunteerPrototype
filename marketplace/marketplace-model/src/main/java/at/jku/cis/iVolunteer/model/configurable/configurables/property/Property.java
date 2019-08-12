package at.jku.cis.iVolunteer.model.configurable.configurables.property;

import java.util.LinkedList;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.configurable.ConfigurableObject;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;


//@Document(collection = "configurableObject")
@Document
public class Property extends ConfigurableObject{
	String name;
	PropertyKind kind;
	int order;
	boolean custom;

	//TODO Matching Priority
	
	public Property() {
		super.setConfigurableType("property");
	}

	public String getConfigurableType() {
		return super.getConfigurableType();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public PropertyKind getKind() {
		return kind;
	}
	
	public void setKind(PropertyKind kind) {
		this.kind = kind;
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
		return "\nProperty [id=" + id + ", name=" + name + /*", rules=" + rules +*/ ", kind=" + kind + ", order=" + order
				+ ", custom=" + custom + "]\n";
	}


	
	

	
	

	
}
