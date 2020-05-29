package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.List;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

public class LTCriteria extends SingleCriteria {

	public LTCriteria(String propertyId, Object value) {
		super(propertyId, value);
	}

	@Override
	public List<ClassInstance> meetCriteria(List<ClassInstance> instances){
		List<ClassInstance> filtered = instances.stream().filter(p -> {
			PropertyInstance<Object> pi = p.getProperty(propertyId);
			switch (pi.getType()) {
			case DATE:
				LocalDateTime d = LocalDateTime.ofInstant(((Date)pi.getValues().get(0)).toInstant(),
                        ZoneId.systemDefault());
				return d.isBefore(LocalDateTime.parse((CharSequence) value));
			case FLOAT_NUMBER:
				return (Double)pi.getValues().get(0) < (Double)value;
			case WHOLE_NUMBER:
				return Integer.parseInt((String) pi.getValues().get(0)) < Integer.parseInt((String) value);
			default:
				return false;
			}
		}).collect(Collectors.toList());
		System.out.println(" filtered .... " + filtered.size());
		return filtered;
	}
}
	