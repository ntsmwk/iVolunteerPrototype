package at.jku.cis.iVolunteer.mapper.core.user;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreVolunteerDTO;

@Mapper
public abstract class CoreVolunteerMapper implements AbstractMapper<CoreVolunteer, CoreVolunteerDTO> {

}
