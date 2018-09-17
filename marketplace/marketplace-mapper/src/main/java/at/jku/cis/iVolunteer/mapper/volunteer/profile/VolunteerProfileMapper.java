package at.jku.cis.iVolunteer.mapper.volunteer.profile;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.user.VolunteerMapper;
import at.jku.cis.iVolunteer.model.volunteer.profile.VolunteerProfile;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.VolunteerProfileDTO;

@Mapper(uses = { CompetenceEntryMapper.class, TaskEntryMapper.class, VolunteerMapper.class })
public abstract class VolunteerProfileMapper implements AbstractMapper<VolunteerProfile, VolunteerProfileDTO> {

}
