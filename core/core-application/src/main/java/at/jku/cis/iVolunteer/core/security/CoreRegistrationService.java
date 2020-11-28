package at.jku.cis.iVolunteer.core.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.core.security.activation.CoreActivationService;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.model._httpresponses.RegisterResponse;
import at.jku.cis.iVolunteer.model._httpresponses.RegisterResponseMessage;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.exception.NotAcceptableException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.registration.AccountType;

@Service
public class CoreRegistrationService {

	// @Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	// @Autowired private CoreVolunteerRepository coreVolunteerRepository;

	@Autowired
	private CoreUserRepository coreUserRepository;
	@Autowired
	CoreActivationService coreActivationService;
	@Autowired
	CoreUserService coreUserService;
	@Autowired
	MarketplaceService marketplaceService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public RegisterResponseMessage registerUser(CoreUser user, AccountType type) {

		CoreUser existingUser = this.coreUserRepository.findByUsernameOrLoginEmail(user.getUsername(),
				user.getLoginEmail());

		if (existingUser != null) {
			if (user.getUsername().equals(existingUser.getUsername())) {
				return new RegisterResponseMessage(RegisterResponse.USERNAME, "Benutzername existiert bereits");
			} else if (user.getLoginEmail().equals(existingUser.getLoginEmail())) {
				return new RegisterResponseMessage(RegisterResponse.EMAIL, "E-Mail existiert bereits");
			}
		}

		encryptPassword(user);

		Marketplace marketplace = marketplaceService.findFirst();
		user.setRegisteredMarketplaceIds(Collections.singletonList(marketplace.getId()));
		this.coreUserService.addNewUser(user, "", true);

		// TODO Philipp
		// TODO sp
		// deactivated for now
		// boolean sendSuccessful =
		// this.coreActivationService.createActivationAndSendLink(user, type);
		boolean sendSuccessful = true;

		if (!sendSuccessful) {
			return new RegisterResponseMessage(RegisterResponse.ACTIVATION,
					"Senden der Aktivierungs-E-Mail fehlgeschlagen");
		}

		return new RegisterResponseMessage(RegisterResponse.OK, "");

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
