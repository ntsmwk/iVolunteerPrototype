package at.jku.cis.iVolunteer.model.property;

import java.util.List;

public class MultipleProperty extends Property {

	List<Property> properties;
	

	public MultipleProperty() {
		kind = PropertyKind.MULTIPLE;
	}
	
	public MultipleProperty(Property p) {
		kind = PropertyKind.MULTIPLE;
		super.id = p.id;
		super.kind = p.kind;
		super.name = p.name;
		super.rules = p.rules;
	}
	
	public List<Property> getProperties() {
		return properties;
	}
	
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MultipleProperty)) {
			return false;
		}
		return ((MultipleProperty) obj).id.equals(id);
	}

	
	
	
}
