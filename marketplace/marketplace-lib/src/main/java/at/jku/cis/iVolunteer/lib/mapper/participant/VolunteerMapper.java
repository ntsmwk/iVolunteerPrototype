package at.jku.cis.iVolunteer.lib.mapper.participant;

import at.jku.cis.iVolunteer.lib.mapper.AbstractSpringMapper;
import at.jku.cis.iVolunteer.model.participant.Volunteer;
import at.jku.cis.iVolunteer.model.participant.dto.VolunteerDTO;

public class VolunteerMapper extends AbstractSpringMapper<Volunteer, VolunteerDTO> {

	public final static VolunteerMapper INSTANCE = new VolunteerMapper();

	public VolunteerMapper() {
		super(Volunteer.class, VolunteerDTO.class);
	}
}
