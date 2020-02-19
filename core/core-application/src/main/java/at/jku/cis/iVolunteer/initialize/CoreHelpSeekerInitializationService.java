package at.jku.cis.iVolunteer.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerRepository;
import at.jku.cis.iVolunteer.core.tenant.CoreTenantRepository;
import at.jku.cis.iVolunteer.core.user.UserImagePathRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.user.UserImagePath;

@Service
public class CoreHelpSeekerInitializationService {

	private static final String MMUSTERMANN = "mmustermann";
	private static final String RAW_PASSWORD = "passme";

	private static final String FFEIDENBERG = "FF_Eidenberg";
	private static final String MUSIKVEREINSCHWERTBERG = "Musikverein_Schwertberg";
	private static final String RKWILHERING = "RK_Wilhering";

	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private CoreTenantRepository coreTenantRepository;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired private UserImagePathRepository userImagePathRepository;

	public void initHelpSeekers() {
		String tenantIdFF = coreTenantRepository.findByName(FFEIDENBERG).getId();
		String tenantIdRK = coreTenantRepository.findByName(RKWILHERING).getId();
		String tenantIdMV = coreTenantRepository.findByName(MUSIKVEREINSCHWERTBERG).getId();

		// @formatter:off
		createHelpSeeker(MMUSTERMANN, RAW_PASSWORD, "M", "Mustermann", "", "HelpSeeker", "FFA", tenantIdFF);

		CoreHelpSeeker helpseeker = createHelpSeeker("FFA", "passme", "Wolfgang", "Kronsteiner", "", "Feuerwehr Kommandant", "FFA", tenantIdFF);
		userImagePathRepository.save(new UserImagePath(helpseeker.getId(), "/assets/images/avatars/FF_Altenberg.jpg"));

		helpseeker = createHelpSeeker("OERK", "passme", "Sandra", "Horvatis", "Rotes Kreuz", "Freiwilligenmanagement", "OERK", tenantIdRK);
		userImagePathRepository.save(new UserImagePath(helpseeker.getId(), "/assets/images/avatars/OERK_Sonderlogo_rgb_cropped.jpg"));

		helpseeker = createHelpSeeker("MVS", "passme", "Johannes", "Schönböck", "", "Musikverein Obmann", "MVS", tenantIdMV);
		userImagePathRepository.save(new UserImagePath(helpseeker.getId(), "/assets/images/avatars/musikvereinschwertberg.jpeg"));
		// @formatter:on
	}

	private CoreHelpSeeker createHelpSeeker(String username, String password, String firstName, String lastName,
			String nickName, String position, String id, String tenantId) {
		CoreHelpSeeker helpSeeker = coreHelpSeekerRepository.findByUsername(username);
		if (helpSeeker == null) {
			helpSeeker = new CoreHelpSeeker();
			helpSeeker.setUsername(username);
			helpSeeker.setPassword(bCryptPasswordEncoder.encode(password));
			helpSeeker.setId(username);
			helpSeeker.setFirstname(firstName);
			helpSeeker.setLastname(lastName);
			helpSeeker.setNickname(nickName);
			helpSeeker.setPosition(position);
			helpSeeker.setTenantId(tenantId);
			helpSeeker = coreHelpSeekerRepository.insert(helpSeeker);
		}
		return helpSeeker;
	}
}
