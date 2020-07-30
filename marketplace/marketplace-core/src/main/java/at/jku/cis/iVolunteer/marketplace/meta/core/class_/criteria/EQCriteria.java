package at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria;

import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

public class EQCriteria extends SingleCriteria {

	public EQCriteria(String propertyId, Object value) {
		super(propertyId, value);
	}

	@Override
	protected boolean filterByCriteria(PropertyInstance<Object> pi) {
		return pi.getValues().stream().anyMatch(v -> v.equals(value));
	}

}
