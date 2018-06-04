package at.jku.cis.iVolunteer.lib.mapper.participant.profile;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.lib.mapper.AbstractSpringMapper;
import at.jku.cis.iVolunteer.model.participant.profile.VolunteerProfile;
import at.jku.cis.iVolunteer.model.participant.profile.dto.VolunteerProfileDTO;

@Service
public class VolunteerProfileMapper extends AbstractSpringMapper<VolunteerProfile, VolunteerProfileDTO> {

	public VolunteerProfileMapper() {
		super(VolunteerProfile.class, VolunteerProfileDTO.class);
	}
}
