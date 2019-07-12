package at.jku.cis.iVolunteer.model.configurable.configurables.property;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TextProperty extends SingleProperty<String> {

	
	public TextProperty() {
		kind = PropertyKind.TEXT;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TextProperty)) {
			return false;
		}
		return ((TextProperty) obj).id.equals(id);
	}

	

}
