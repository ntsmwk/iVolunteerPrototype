package at.jku.cis.iVolunteer.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerService;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@Service
public class CoreVolunteerInitializationService {

	private static final String PSTARZER = "pstarzer";
	private static final String BROISER = "broiser";
	private static final String MWEISSENBEK = "mweissenbek";
	private static final String MWEIXLBAUMER = "mweixlbaumer";

	private static final String RAW_PASSWORD = "passme";

	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private CoreVolunteerService coreVolunteerService;
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void registerVolunteers() {
		coreVolunteerRepository.findAll().forEach(volunteer -> registerVolunteer(volunteer));
	}

	private void registerVolunteer(CoreVolunteer volunteer) {
		Marketplace mp = marketplaceRepository.findAll().stream().filter(m -> m.getName().equals("Marketplace 1"))
				.findFirst().orElse(null);
		if (mp != null) {
			try {
				coreVolunteerService.registerMarketplace(volunteer.getId(), mp.getId(), "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void initVolunteers() {

		createVolunteer(BROISER, RAW_PASSWORD, "Berthold", "Roiser", "");
		createVolunteer(PSTARZER, RAW_PASSWORD, "Philipp", "Starzer", "");
		createVolunteer(MWEISSENBEK, RAW_PASSWORD, "Markus", "Weißenbek", "");
		createVolunteer(MWEIXLBAUMER, RAW_PASSWORD, "Markus", "Weixlbaumer", "");

		createVolunteer("AKop", "passme", "Alexander", "Kopp", "Alex");
		createVolunteer("WRet", "passme", "Werner", "Retschitzegger", "");
		createVolunteer("WSch", "passme", "Wieland", "Schwinger", "");
		createVolunteer("BProe", "passme", "Birgit", "Pröll", "");
		createVolunteer("KKof", "passme", "Katharina", "Kofler", "Kati");
	}

	private CoreVolunteer createVolunteer(String username, String password, String firstName, String lastName,
			String nickName) {
		CoreVolunteer volunteer = coreVolunteerRepository.findByUsername(username);
		if (volunteer == null) {
			volunteer = new CoreVolunteer();
			volunteer.setUsername(username);
			volunteer.setPassword(bCryptPasswordEncoder.encode(password));
			volunteer.setFirstname(firstName);
			volunteer.setLastname(lastName);
			volunteer.setNickname(nickName);
			volunteer = coreVolunteerRepository.insert(volunteer);
		}
		return volunteer;
	}
}
