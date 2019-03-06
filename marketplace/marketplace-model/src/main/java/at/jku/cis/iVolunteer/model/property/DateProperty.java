package at.jku.cis.iVolunteer.model.property;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DateProperty extends Property<Date> {
	
	public DateProperty() {
		kind = PropertyKind.DATE;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DateProperty)) {
			return false;
		}
		return ((DateProperty) obj).id.equals(id);
	}

	

}
