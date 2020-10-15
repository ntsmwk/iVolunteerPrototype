package at.jku.cis.iVolunteer.initialize;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.file.StorageService;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.tenant.TenantRepository;
import at.jku.cis.iVolunteer.core.user.CoreUserRepository;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.registration.AccountType;
import at.jku.cis.iVolunteer.model.user.TenantSubscription;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class CoreVolunteerInitializationService {

	private static final String PSTARZER = "pstarzer";
	private static final String BROISER = "broiser";
	private static final String MWEISSENBEK = "mweissenbek";
	private static final String MWEIXLBAUMER = "mweixlbaumer";

	private static final String RAW_PASSWORD = "passme";

	private static final String[] USERNAMES = { BROISER, PSTARZER, MWEISSENBEK, MWEIXLBAUMER, "AKop", "WRet", "WSch",
			"BProe", "KKof", "CVoj", "KBauer", "EWagner", "WHaube", "MJacks" };

	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private CoreUserRepository coreUserRepository;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired private TenantRepository coreTenantRepository;
	@Autowired private CoreUserService coreUserService;
	@Autowired private StorageService storageService;
	@Value("${spring.data.server.uri}") private String serverUrl;

	public void initVolunteers() {

		createVolunteer(USERNAMES[0], RAW_PASSWORD, "Berthold", "Roiser", LocalDate.of(1988, 9, 7), "", "");
		createVolunteer(USERNAMES[1], RAW_PASSWORD, "Philipp", "Starzer", LocalDate.of(1995, 10, 9), "",
				"/img/pstarzer.jpg");
		createVolunteer(USERNAMES[2], RAW_PASSWORD, "Markus", "Weißenbek", LocalDate.of(1994, 1, 23), "", "");
		createVolunteer(USERNAMES[3], RAW_PASSWORD, "Markus", "Weixlbaumer", LocalDate.of(1985, 5, 24), "",
				"/img/weixlbaumer_small.png");

		createVolunteer(USERNAMES[4], RAW_PASSWORD, "Alexander", "Kopp", LocalDate.of(1989, 11, 29), "Alex", "");
		createVolunteer(USERNAMES[5], RAW_PASSWORD, "Werner", "Retschitzegger", LocalDate.of(1975, 11, 4), "", "");
		createVolunteer(USERNAMES[6], RAW_PASSWORD, "Wieland", "Schwinger", LocalDate.of(1976, 6, 9), "", "");
		createVolunteer(USERNAMES[7], RAW_PASSWORD, "Birgit", "Pröll", LocalDate.of(1976, 10, 5), "", "");
		createVolunteer(USERNAMES[8], RAW_PASSWORD, "Katharina", "Kofler", LocalDate.of(1998, 5, 8), "Kati", "");
		createVolunteer(USERNAMES[9], RAW_PASSWORD, "Claudia", "Vojinovic", LocalDate.of(1981, 12, 1), "", "");
		createVolunteer(USERNAMES[10], RAW_PASSWORD, "Kerstin", "Bauer", LocalDate.of(1960, 2, 17), "", "");
		createVolunteer(USERNAMES[11], RAW_PASSWORD, "Erich", "Wagner", LocalDate.of(1980, 07, 11), "", "");
		createVolunteer(USERNAMES[12], RAW_PASSWORD, "Werner", "Haube", LocalDate.of(1970, 8, 8), "", "");
		createVolunteer(USERNAMES[13], RAW_PASSWORD, "Melanie", "Jachs", LocalDate.of(1970, 7, 8), "", "");
	}

	private CoreUser createVolunteer(String username, String password, String firstName, String lastName,
			LocalDate birthDate, String nickName, String fileName) {
		CoreUser volunteer = coreUserRepository.findByUsername(username);
		if (volunteer == null) {
			volunteer = new CoreUser();
			volunteer.setUsername(username);
			volunteer.setPassword(bCryptPasswordEncoder.encode(password));
			volunteer.setFirstname(firstName);
			volunteer.setLastname(lastName);
			ZoneId defaultZoneId = ZoneId.systemDefault();
			Date date = Date.from(birthDate.atStartOfDay(defaultZoneId).toInstant());
			volunteer.setBirthday(date);
			volunteer.setNickname(nickName);

			volunteer.setRegisteredMarketplaceIds(
					marketplaceRepository.findAll().stream().map(mp -> mp.getId()).collect(Collectors.toList()));

			volunteer.setEmails(Collections.singletonList("iVolunteerTest@gmx.at"));

			volunteer.setActivated(true);
			volunteer.setAccountType(AccountType.PERSON);
			setImage(fileName, volunteer);
			volunteer = coreUserService.addNewUser(volunteer, "", false);
		}
		return volunteer;
	}

	private void setImage(String fileName, CoreUser volunteer) {
		if (fileName != null && !fileName.equals("")) {
			ClassPathResource classPathResource = new ClassPathResource(fileName);
			String fileUrl = storageService.store(classPathResource);
			volunteer.setProfileImagePath(fileUrl);
		}
	}

	protected void subscribeVolunteersToAllTenants() {
		ArrayList<CoreUser> volunteers = new ArrayList<>();
		coreUserRepository.findByUsernameIn(Arrays.asList(USERNAMES)).forEach(volunteers::add);

		List<Tenant> tenants = coreTenantRepository.findAll();

		Marketplace mp = marketplaceRepository.findByName("Marketplace 1");

		for (int i = 0; i < volunteers.size(); i++) {
			CoreUser volunteer = volunteers.get(i);
			volunteer.setSubscribedTenants(new ArrayList<TenantSubscription>());
			for (Tenant t : tenants) {
				volunteer.addSubscribedTenant(mp.getId(), t.getId(), UserRole.VOLUNTEER);
			}
			coreUserService.updateUser(volunteer, "", false);
		}

	}

	protected void registerVolunteers() {
		Marketplace mp = marketplaceRepository.findByName("Marketplace 1");

		this.coreUserService.getAllByUserRole(UserRole.VOLUNTEER)
				.forEach(v -> coreUserService.registerToMarketplace(v.getId(), mp.getId(), ""));
	}

}