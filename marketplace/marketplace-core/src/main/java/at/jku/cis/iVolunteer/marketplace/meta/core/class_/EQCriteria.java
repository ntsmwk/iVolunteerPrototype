package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.List;
import java.util.stream.Collectors;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

public class EQCriteria extends SingleCriteria {

	public EQCriteria(String propertyId, Object value) {
		super(propertyId, value);
	}

	@Override
	public List<ClassInstance> meetCriteria(List<ClassInstance> instances){
		System.out.println(" filter .... instances: " + instances.size());	
		List<ClassInstance> filtered = instances.stream().filter(p -> p.getProperty(propertyId).getValues().get(0).equals(value)).collect(Collectors.toList());
		System.out.println(" filtered .... instances: " + filtered.size());
		return instances.stream().filter(p -> p.getProperty(propertyId).getValues().get(0).equals(value)).collect(Collectors.toList());
	}
	
}
