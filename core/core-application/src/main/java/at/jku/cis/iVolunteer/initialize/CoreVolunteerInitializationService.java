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

		createVolunteer(BROISER, RAW_PASSWORD, "Berthold", "Roiser", "1988-09-07T00:00:00", "", "");
		createVolunteer(PSTARZER, RAW_PASSWORD, "Philipp", "Starzer", "1995-10-09T00:00:00", "", "img/pstarzer.jpg");
		createVolunteer(MWEISSENBEK, RAW_PASSWORD, "Markus", "Weißenbek", "1994-01-23T00:00:00", "", "");
		createVolunteer(MWEIXLBAUMER, RAW_PASSWORD, "Markus", "Weixlbaumer", "1985-05-24T00:00:00", "", "img/weixlbaumer_small.png");

		createVolunteer("AKop", "passme", "Alexander", "Kopp", "1998-07-09T00:00:00", "Alex", "");
		createVolunteer("WRet", "passme", "Werner", "Retschitzegger", "1975-11-04T00:00:00", "", "");
		createVolunteer("WSch", "passme", "Wieland", "Schwinger", "1976-06-09T00:00:00", "", "");
		createVolunteer("BProe", "passme", "Birgit", "Pröll", "1976-10-05T00:00:00","", "");
		createVolunteer("KKof", "passme", "Katharina", "Kofler", "1998-05-08T00:00:00","Kati", "");
		createVolunteer("CVoj", "passme", "Claudia", "Vojinovic", "1981-12-01T00:00:00", "", "");
		createVolunteer("KBauer", "passme", "Kerstin", "Bauer", "1960-02-17T00:00:00", "", "");
		createVolunteer("EWagner", "passme", "Erich", "Wagner", "1980-07-11T00:00:00", "", "");
		createVolunteer("WHaube", "passme", "Werner", "Haube", "1970-08-08T00:00:00", "", "");
		createVolunteer("MJachs", "passme", "Melanie", "Jachs", "1970-07-08T00:00:00", "", "");		
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
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm" ).withLocale(Locale.GERMANY);
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