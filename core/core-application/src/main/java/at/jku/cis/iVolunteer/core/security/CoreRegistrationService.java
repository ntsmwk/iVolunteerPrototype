package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;

@Service
public class CoreRegistrationService {

	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;

	public void registerVolunteer(CoreVolunteer volunteer) {
		this.coreVolunteerRepository.save(volunteer);
	}

	public void registerHelpSeeker(CoreHelpSeeker volunteer) {
		this.coreHelpSeekerRepository.save(volunteer);
	}

}
