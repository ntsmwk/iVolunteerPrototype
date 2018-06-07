package at.jku.cis.iVolunteer.lib.mapper.participant;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.lib.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.participant.Volunteer;
import at.jku.cis.iVolunteer.model.participant.dto.VolunteerDTO;

@Mapper
public abstract class VolunteerMapper implements AbstractMapper<Volunteer, VolunteerDTO> {


}
