package at.jku.cis.iVolunteer.mapper.iVolunteerObject;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.property.PropertyMapper;
import at.jku.cis.iVolunteer.model.iVolunteerObject.IVolunteerObject;
import at.jku.cis.iVolunteer.model.iVolunteerObject.dto.IVolunteerObjectDTO;


@Mapper(uses = { PropertyMapper.class})
public abstract class IVolunteerObjectMapper implements AbstractMapper<IVolunteerObject, IVolunteerObjectDTO> {

}
