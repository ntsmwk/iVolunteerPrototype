package at.jku.cis.iVolunteer.model.configurable.configurables.property;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.configurable.ConfigurableObject;


@Document(collection = "configurableObject")
//@TypeAlias("property")
public class Property extends ConfigurableObject{
//public class Property {
	
//	String id;
	String name;
	
	PropertyKind kind;
	
	int order;
	
	boolean custom;
	
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
