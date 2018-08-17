package at.jku.cis.iVolunteer.mapper.core.participant;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.core.participant.CoreVolunteer;
import at.jku.cis.iVolunteer.model.core.participant.dto.CoreVolunteerDTO;

@Mapper
public abstract class CoreVolunteerMapper implements AbstractMapper<CoreVolunteer, CoreVolunteerDTO> {

}
