package at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

public class SumCriteria  {
	
	protected String classPropertyId;
	protected PropertyType propertyType;
	protected Object value;
	

	public SumCriteria(String classPropertyId, PropertyType propertyType) {
		this.classPropertyId = classPropertyId;
		this.propertyType = propertyType;
	}

	public double sum(List<ClassInstance> instances) {
		double sum = 0.0;
		
		sum = instances.stream()
	    		.filter(i -> i.containsProperty(classPropertyId))
	    		.mapToDouble(i -> {
	    			if (i.getProperty(classPropertyId).getValues().get(0) instanceof Integer)
	    				return ((Integer)i.getProperty(classPropertyId).getValues().get(0)).doubleValue();
	    			else 
	    			   return (double) i.getProperty(classPropertyId).getValues().get(0);
	    			})
			    .sum();
	    return sum;
	}

}
