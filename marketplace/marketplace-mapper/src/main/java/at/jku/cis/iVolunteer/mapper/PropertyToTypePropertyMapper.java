package at.jku.cis.iVolunteer.mapper;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.SingleProperty;


public interface PropertyToTypePropertyMapper<T> {

	SingleProperty<?> toGenericProperty(T property);

	T toTypeProperty(SingleProperty<?> property);

	List<SingleProperty<?>> toGenericProperties(List<T> properties);

	List<T> toTypeProperties(List<SingleProperty<?>> properties);

	
}
