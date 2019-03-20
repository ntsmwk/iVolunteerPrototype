package at.jku.cis.iVolunteer.model.property;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DoubleProperty extends SingleProperty<Double> {
	
	public DoubleProperty() {
		kind = PropertyKind.FLOAT_NUMBER;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DoubleProperty)) {
			return false;
		}
		return ((DoubleProperty) obj).id.equals(id);
	}

}
