package at.jku.cis.iVolunteer.mapper.meta.constraint.property;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.constraint.property.dto.PropertyConstraintDTO;

@Mapper
public abstract class PropertyConstraintMapper
		implements AbstractMapper<PropertyConstraint<Object>, PropertyConstraintDTO<Object>> {

}
