package at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria;

import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

public class NECriteria extends SingleCriteria {

	public NECriteria(String propertyId, Object value) {
		super(propertyId, value);
	}

	@Override
	protected boolean filterByCriteria(PropertyInstance<Object> pi) {
		return pi.getValues().stream().noneMatch(v -> v.equals(value));
	}

}
