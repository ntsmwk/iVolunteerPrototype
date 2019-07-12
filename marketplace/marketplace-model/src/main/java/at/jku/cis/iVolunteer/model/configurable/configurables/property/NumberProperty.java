package at.jku.cis.iVolunteer.model.configurable.configurables.property;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class NumberProperty extends SingleProperty<Integer> {
	
	public NumberProperty() {
		super();
		kind = PropertyKind.WHOLE_NUMBER;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NumberProperty)) {
			return false;
		}
		return ((NumberProperty) obj).id.equals(id);
	}
	

}
