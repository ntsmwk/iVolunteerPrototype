package at.jku.cis.iVolunteer.mapper.meta.core.property;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.meta.constraint.property.PropertyConstraintMapper;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.dto.PropertyInstanceDTO;

@Mapper(uses = PropertyConstraintMapper.class)
public abstract class PropertyInstanceMapper
		implements AbstractMapper<PropertyInstance<Object>, PropertyInstanceDTO<Object>> {

}
