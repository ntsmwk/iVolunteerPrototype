package at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria;

import java.util.List;
import java.util.stream.Collectors;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

public class EQCriteria extends SingleCriteria {

	public EQCriteria(String propertyId, Object value) {
		super(propertyId, value);
	}

	@Override
	public List<ClassInstance> meetCriteria(List<ClassInstance> instances){
		return instances.stream().filter(p -> p.getProperty(propertyId).getValues().get(0).equals(value)).collect(Collectors.toList());
	}
	
}
