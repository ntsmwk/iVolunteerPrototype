package at.jku.cis.iVolunteer.mapper.meta.core.class_;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyInstanceMapper;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.class_.dtos.ClassInstanceDTO;

@Mapper(uses = PropertyInstanceMapper.class)
public abstract class ClassInstanceMapper implements AbstractMapper<ClassInstance, ClassInstanceDTO> {

}
