package at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

public abstract class SingleCriteria implements Criteria {
	
	protected String propertyId;
	protected Object value;
	
	public SingleCriteria(String propertyId, Object value) {
		this.setPropertyId(propertyId);
		this.setValue(value);
	}

	public abstract List<ClassInstance> meetCriteria(List<ClassInstance> instances);

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
