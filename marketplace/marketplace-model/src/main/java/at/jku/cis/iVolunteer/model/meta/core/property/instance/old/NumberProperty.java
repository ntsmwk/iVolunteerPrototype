package at.jku.cis.iVolunteer.model.meta.core.property.instance.old;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@Document
public class NumberProperty extends SingleProperty<Integer> {
	
	public NumberProperty() {
		super();
		type = PropertyType.WHOLE_NUMBER;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NumberProperty)) {
			return false;
		}
		return ((NumberProperty) obj).id.equals(id);
	}
	

}
