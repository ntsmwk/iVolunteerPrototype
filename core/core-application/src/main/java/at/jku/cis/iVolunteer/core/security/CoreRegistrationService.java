package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;

@Service
public class CoreRegistrationService {

	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void registerVolunteer(CoreVolunteer volunteer) {
		volunteer.setPassword(bCryptPasswordEncoder.encode(volunteer.getPassword()));
		this.coreVolunteerRepository.save(volunteer);
	}

	public void registerHelpSeeker(CoreHelpSeeker helpSeeker) {
		helpSeeker.setPassword(bCryptPasswordEncoder.encode(helpSeeker.getPassword()));
		this.coreHelpSeekerRepository.save(helpSeeker);
	}

}
