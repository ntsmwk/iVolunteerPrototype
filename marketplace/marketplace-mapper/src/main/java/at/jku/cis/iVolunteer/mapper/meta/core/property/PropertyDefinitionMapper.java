package at.jku.cis.iVolunteer.mapper.meta.core.property;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.meta.constraint.property.PropertyConstraintMapper;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.PropertyDefinitionDTO;

@Mapper(uses = PropertyConstraintMapper.class)
public abstract class PropertyDefinitionMapper
		implements AbstractMapper<PropertyDefinition<Object>, PropertyDefinitionDTO<Object>> {

}
