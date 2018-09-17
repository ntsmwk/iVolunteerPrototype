package at.jku.cis.iVolunteer.mapper.user;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.user.Volunteer;
import at.jku.cis.iVolunteer.model.user.dto.VolunteerDTO;

@Mapper
public abstract class VolunteerMapper implements AbstractMapper<Volunteer, VolunteerDTO> {


}
