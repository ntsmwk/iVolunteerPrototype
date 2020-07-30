package at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria;

import java.util.Date;

import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

public class LTCriteria extends SingleCriteria {

	public LTCriteria(String propertyId, Object value) {
		super(propertyId, value);
	}

	protected boolean filterByCriteria(PropertyInstance<Object> pi) {
		switch (pi.getType()) {
		case DATE:
			return pi.getValues().stream().anyMatch(v -> convert((Date) v).isBefore(parse(value)));
		case FLOAT_NUMBER:
			return pi.getValues().stream().anyMatch(v -> (Double) v < (Double) value);
		case WHOLE_NUMBER:
			return pi.getValues().stream()
					.anyMatch(v -> Integer.parseInt((String) v) < Integer.parseInt((String) value));
		default:
			return false;
		}
	}
}
