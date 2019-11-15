package at.jku.cis.iVolunteer.mapper.meta.core.class_;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.property.ClassPropertyMapper;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.class_.dtos.ClassDefinitionDTO;

@Mapper(uses = ClassPropertyMapper.class)
public abstract class ClassDefinitionMapper implements AbstractMapper<ClassDefinition, ClassDefinitionDTO> {

}
