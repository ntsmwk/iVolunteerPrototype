package at.jku.cis.iVolunteer.lib.mapper.participant;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.lib.mapper.AbstractSpringMapper;
import at.jku.cis.iVolunteer.model.participant.Volunteer;
import at.jku.cis.iVolunteer.model.participant.dto.VolunteerDTO;

@Service
public class VolunteerMapper extends AbstractSpringMapper<Volunteer, VolunteerDTO> {

	public VolunteerMapper() {
		super(Volunteer.class, VolunteerDTO.class);
	}
}
