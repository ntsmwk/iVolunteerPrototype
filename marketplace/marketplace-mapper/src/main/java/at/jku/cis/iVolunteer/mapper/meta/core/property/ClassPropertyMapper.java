package at.jku.cis.iVolunteer.mapper.meta.core.property;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.meta.constraint.property.PropertyConstraintMapper;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.ClassPropertyDTO;

@Mapper(uses = PropertyConstraintMapper.class)
public abstract class ClassPropertyMapper implements AbstractMapper<ClassProperty<Object>, ClassPropertyDTO<Object>> {

}
