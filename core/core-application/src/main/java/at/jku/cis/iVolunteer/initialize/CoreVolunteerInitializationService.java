package at.jku.cis.iVolunteer.initialize;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.tenant.TenantRepository;
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
	@Autowired private TenantRepository coreTenantRepository;

	public void initVolunteers(){

		createVolunteer(BROISER, RAW_PASSWORD, "Berthold", "Roiser", "07.09.1988", "", "");
		createVolunteer(PSTARZER, RAW_PASSWORD, "Philipp", "Starzer", "09.10.1995", "", "img/pstarzer.jpg");
		createVolunteer(MWEISSENBEK, RAW_PASSWORD, "Markus", "Weißenbek", "23.01.1994", "", "");
		createVolunteer(MWEIXLBAUMER, RAW_PASSWORD, "Markus", "Weixlbaumer", "24.05.1985", "", "img/weixlbaumer_small.png");

		createVolunteer("AKop", "passme", "Alexander", "Kopp", "09.07.1998", "Alex", "");
		createVolunteer("WRet", "passme", "Werner", "Retschitzegger", "04.11.1975", "", "");
		createVolunteer("WSch", "passme", "Wieland", "Schwinger", "09.06.1976", "", "");
		createVolunteer("BProe", "passme", "Birgit", "Pröll", "05.10.1976","", "");
		createVolunteer("KKof", "passme", "Katharina", "Kofler", "08.05.1998","Kati", "");
		createVolunteer("CVoj", "passme", "Claudia", "Vojinovic", "01.12.1981", "", "");
	}

	private CoreVolunteer createVolunteer(String username, String password, String firstName, String lastName, String birthDate,
			String nickName, String fileName){
		CoreVolunteer volunteer = coreVolunteerRepository.findByUsername(username);
		if (volunteer == null) {
			volunteer = new CoreVolunteer();
			volunteer.setUsername(username);
			volunteer.setPassword(bCryptPasswordEncoder.encode(password));
			volunteer.setFirstname(firstName);
			volunteer.setLastname(lastName);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy" ).withLocale(Locale.GERMANY);
			LocalDate date = LocalDate.parse(birthDate, formatter);
			volunteer.setBirthday(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			volunteer.setNickname(nickName);

			setImage(fileName, volunteer);
			volunteer = coreVolunteerRepository.insert(volunteer);
		}
		return volunteer;
	}

	private void setImage(String fileName, CoreVolunteer volunteer) {
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
		coreVolunteerRepository.findAll().forEach(volunteer -> registerVolunteer(volunteer, tenantIds));
	}

	private void registerVolunteer(CoreVolunteer volunteer, List<String> tenantIds) {
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