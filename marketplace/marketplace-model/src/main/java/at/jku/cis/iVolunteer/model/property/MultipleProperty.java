package at.jku.cis.iVolunteer.model.property;

import java.util.LinkedList;

public class MultipleProperty extends Property<Object> {

	LinkedList<Property<?>> properties;
	

	public MultipleProperty() {
		kind = PropertyKind.MULTIPLE;
	}
	
	public LinkedList<Property<?>> getProperties() {
		return properties;
	}
	
	public void setProperties(LinkedList<Property<?>> properties) {
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
