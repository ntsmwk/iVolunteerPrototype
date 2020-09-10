package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.security.activation.CoreActivationService;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.registration.AccountType;

@Service
public class CoreRegistrationService {

	// @Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	// @Autowired private CoreVolunteerRepository coreVolunteerRepository;

	@Autowired private CoreUserRepository coreUserRepository;
	@Autowired CoreActivationService coreActivationService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public CoreUser registerUser(CoreUser user, AccountType type) {
		
		CoreUser existingUser = this.coreUserRepository.findByUsernameOrLoginEmail(user.getUsername(), user.getLoginEmail());
		if (existingUser == null) {
			encryptPassword(user);
			this.coreUserRepository.save(user);
			this.coreActivationService.createActivationAndSendLink(user, type);
		}
		return existingUser; 
	}

	// public void registerVolunteer(CoreVolunteer volunteer) {
	// encryptPassword(volunteer);
	// this.coreVolunteerRepository.save(volunteer);
	// }

	// public void registerHelpSeeker(CoreHelpSeeker helpSeeker) {
	// encryptPassword(helpSeeker);
	// this.coreHelpSeekerRepository.save(helpSeeker);
	// }

	private void encryptPassword(CoreUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	}

}
