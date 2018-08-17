package at.jku.cis.iVolunteer.mapper.participant.profile;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.participant.VolunteerMapper;
import at.jku.cis.iVolunteer.model.participant.profile.VolunteerProfile;
import at.jku.cis.iVolunteer.model.participant.profile.dto.VolunteerProfileDTO;

@Mapper(uses = { CompetenceEntryMapper.class, TaskEntryMapper.class, VolunteerMapper.class })
public abstract class VolunteerProfileMapper implements AbstractMapper<VolunteerProfile, VolunteerProfileDTO> {

}
