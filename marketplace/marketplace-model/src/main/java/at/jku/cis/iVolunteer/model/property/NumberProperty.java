package at.jku.cis.iVolunteer.model.property;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class NumberProperty extends SingleProperty<Integer> {
	
	public NumberProperty() {
		kind = PropertyKind.WHOLE_NUMBER;
		defaultValue = 0;
		value = defaultValue;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NumberProperty)) {
			return false;
		}
		return ((NumberProperty) obj).id.equals(id);
	}
	

}
