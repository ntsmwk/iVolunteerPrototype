package at.jku.cis.iVolunteer.mapper;

import java.util.List;

import at.jku.cis.iVolunteer.model.property.Property;


public interface PropertyToTypePropertyMapper<T> {

	Property<?> toGenericProperty(T property);

	T toTypeProperty(Property<?> property);

	List<Property<?>> toGenericProperties(List<T> properties);

	List<T> toTypeProperties(List<Property<?>> properties);

	
}
