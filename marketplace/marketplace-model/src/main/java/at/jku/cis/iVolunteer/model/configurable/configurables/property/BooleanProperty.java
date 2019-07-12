package at.jku.cis.iVolunteer.model.configurable.configurables.property;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BooleanProperty extends SingleProperty<Boolean> {
	
	public BooleanProperty() {
		super();
		kind = PropertyKind.BOOL;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BooleanProperty)) {
			return false;
		}
		return ((BooleanProperty) obj).id.equals(id);
	}

	

}
