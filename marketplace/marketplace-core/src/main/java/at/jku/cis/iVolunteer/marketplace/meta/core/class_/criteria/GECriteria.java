package at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria;

import java.time.format.DateTimeFormatter;
import java.util.Date;

import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

public class GECriteria extends SingleCriteria {

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");

	public GECriteria(String propertyId, Object value) {
		super(propertyId, value);
	}

	protected boolean filterByCriteria(PropertyInstance<Object> pi) {
		switch (pi.getType()) {
		case DATE:
			return pi.getValues().stream()
					.anyMatch(v -> convert((Date) v).isAfter(parse(value)) || convert((Date) v).isEqual(parse(value)));
		case FLOAT_NUMBER:
			return pi.getValues().stream().anyMatch(v -> (Double) v >= (Double) value);
		case WHOLE_NUMBER:
			return pi.getValues().stream()
					.anyMatch(v -> Integer.parseInt((String) v) >= Integer.parseInt((String) value));
		default:
			return false;
		}
	}
}
