package at.jku.cis.iVolunteer.marketplace.meta.core.class_.criteria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

public abstract class SingleCriteria implements Criteria {
	
	protected String propertyId;
	protected Object value;
	protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
	
	public SingleCriteria(String propertyId, Object value) {
		this.setPropertyId(propertyId);
		this.setValue(value);
	}

	public List<ClassInstance> meetCriteria(List<ClassInstance> instances){
		// System.out.println(this.getClass().getSimpleName() + "- filter propertyId: " + propertyId + " .... " + instances.size());
		List<ClassInstance> filtered = instances.stream()
				.filter(p -> p.containsProperty(propertyId))
				.filter(p -> filterByCriteria(p.getProperty(propertyId)))
				.collect(Collectors.toList());
		// System.out.println(this.getClass().getSimpleName() + " - filtered .... " + filtered.size());
		return filtered;
	}
	
	protected abstract boolean filterByCriteria(PropertyInstance<Object> pi);

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
	
	protected LocalDateTime convert(Date dateValue) {
		return LocalDateTime.ofInstant(dateValue.toInstant(),
                ZoneId.systemDefault());
	}
	
	protected LocalDateTime parse(Object value) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
		return LocalDate.parse((CharSequence) value, formatter).atStartOfDay();
	}

}
