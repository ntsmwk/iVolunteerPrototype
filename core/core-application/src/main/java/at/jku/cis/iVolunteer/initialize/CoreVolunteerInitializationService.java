package at.jku.cis.iVolunteer.initialize;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.tenant.TenantRepository;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerService;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class CoreVolunteerInitializationService {

	private static final String PSTARZER = "pstarzer";
	private static final String BROISER = "broiser";
	private static final String MWEISSENBEK = "mweissenbek";
	private static final String MWEIXLBAUMER = "mweixlbaumer";

	private static final String RAW_PASSWORD = "passme";

	private static final String FF_EIDENBERG = "FF Eidenberg";
	private static final String MV_SCHWERTBERG = "MV Schwertberg";
	private static final String RK_WILHERING = "RK Wilhering";

	@Autowired
	private MarketplaceRepository marketplaceRepository;
	@Autowired
	private CoreVolunteerService coreVolunteerService;
	@Autowired
	private CoreUserRepository coreUserRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private TenantRepository coreTenantRepository;
	@Autowired
	private CoreUserService coreUserService;

	public void initVolunteers() {

		createVolunteer(BROISER, RAW_PASSWORD, "Berthold", "Roiser", "", "");
		createVolunteer(PSTARZER, RAW_PASSWORD, "Philipp", "Starzer", "", "img/pstarzer.jpg");
		createVolunteer(MWEISSENBEK, RAW_PASSWORD, "Markus", "Weißenbek", "", "");
		createVolunteer(MWEIXLBAUMER, RAW_PASSWORD, "Markus", "Weixlbaumer", "", "img/weixlbaumer_small.png");

		createVolunteer("AKop", "passme", "Alexander", "Kopp", "Alex", "");
		createVolunteer("WRet", "passme", "Werner", "Retschitzegger", "", "");
		createVolunteer("WSch", "passme", "Wieland", "Schwinger", "", "");
		createVolunteer("BProe", "passme", "Birgit", "Pröll", "", "");
		createVolunteer("KKof", "passme", "Katharina", "Kofler", "Kati", "");
	}

	private CoreUser createVolunteer(String username, String password, String firstName, String lastName,
			String nickName, String fileName) {
		CoreUser volunteer = coreUserRepository.findByUsername(username);
		if (volunteer == null) {
			volunteer = new CoreUser();
			volunteer.setUsername(username);
			volunteer.setPassword(bCryptPasswordEncoder.encode(password));
			volunteer.setFirstname(firstName);
			volunteer.setLastname(lastName);
			volunteer.setNickname(nickName);

			setImage(fileName, volunteer);

			// TODO
			volunteer.setRegisteredMarketplaces(marketplaceRepository.findAll());

			volunteer = coreUserRepository.insert(volunteer);

			List<String> tenantIds = new ArrayList<String>();
			tenantIds.add(coreTenantRepository.findByName(FF_EIDENBERG).getId());
			tenantIds.add(coreTenantRepository.findByName(RK_WILHERING).getId());
			tenantIds.add(coreTenantRepository.findByName(MV_SCHWERTBERG).getId());
			// registerVolunteer(volunteer, tenantIds);
		}
		return volunteer;
	}

	private void setImage(String fileName, CoreUser volunteer) {
		if (fileName != null && !fileName.equals("")) {
			try {
				Resource resource = new ClassPathResource(fileName);
				File file = resource.getFile();
				volunteer.setImage(Files.readAllBytes(file.toPath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void registerVolunteers() {
		List<String> tenantIds = coreTenantRepository.findAll().stream().map(tenant -> tenant.getId())
				.collect(Collectors.toList());
		this.coreUserService.getCoreUsersByRole(UserRole.VOLUNTEER)
				.forEach(volunteer -> registerVolunteer(volunteer, tenantIds));
	}

	private void registerVolunteer(CoreUser volunteer, List<String> tenantIds) {
		Marketplace mp = marketplaceRepository.findAll().stream().filter(m -> m.getName().equals("Marketplace 1"))
				.findFirst().orElse(null);
		if (mp != null) {
			try {
				coreVolunteerService.subscribeTenant(volunteer.getId(), mp.getId(), tenantIds, "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}