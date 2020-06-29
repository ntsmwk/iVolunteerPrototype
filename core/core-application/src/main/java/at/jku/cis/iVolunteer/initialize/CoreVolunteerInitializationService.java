package at.jku.cis.iVolunteer.initialize;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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
	
	private static final String FF_EIDENBERG = "FF Eidenberg";
	private static final String MV_SCHWERTBERG = "MV Schwertberg";
	private static final String RK_WILHERING = "RK Wilhering";


	private static final String RAW_PASSWORD = "passme";

	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private CoreVolunteerService coreVolunteerService;
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired private TenantRepository coreTenantRepository;

	public void initVolunteers(){

		createVolunteer(BROISER, RAW_PASSWORD, "Berthold", "Roiser", LocalDate.of(1988,9,7), "", "");
		createVolunteer(PSTARZER, RAW_PASSWORD, "Philipp", "Starzer", LocalDate.of(1995,10,9), "", "img/pstarzer.jpg");
		createVolunteer(MWEISSENBEK, RAW_PASSWORD, "Markus", "Weißenbek", LocalDate.of(1994,1,23), "", "");
		createVolunteer(MWEIXLBAUMER, RAW_PASSWORD, "Markus", "Weixlbaumer", LocalDate.of(1985,5,24), "", "img/weixlbaumer_small.png");

		createVolunteer("AKop", "passme", "Alexander", "Kopp", LocalDate.of(1998,7,9), "Alex", "");
		createVolunteer("WRet", "passme", "Werner", "Retschitzegger", LocalDate.of(1975,11,4), "", "");
		createVolunteer("WSch", "passme", "Wieland", "Schwinger", LocalDate.of(1976,6,9), "", "");
		createVolunteer("BProe", "passme", "Birgit", "Pröll", LocalDate.of(1976,10,5),"", "");
		createVolunteer("KKof", "passme", "Katharina", "Kofler", LocalDate.of(1998,5,8),"Kati", "");
		
		List<String> tenantIds = new ArrayList<String>();
		tenantIds.add(coreTenantRepository.findByName(FF_EIDENBERG).getId());
		tenantIds.add(coreTenantRepository.findByName(RK_WILHERING).getId());
		tenantIds.add(coreTenantRepository.findByName(MV_SCHWERTBERG).getId());
		CoreVolunteer vol = createVolunteer("CVoj", "passme", "Claudia", "Vojinovic", LocalDate.of(1981, 12, 1), "", "");
		registerVolunteer(vol, tenantIds);
		vol = createVolunteer("KBauer", "passme", "Kerstin", "Bauer", LocalDate.of(1960, 2, 17),"", "");
		registerVolunteer(vol, tenantIds);
		vol = createVolunteer("EWagner", "passme", "Erich", "Wagner", LocalDate.of(1980, 07, 11), "", "");
		registerVolunteer(vol, tenantIds);
		vol = createVolunteer("WHaube", "passme", "Werner", "Haube", LocalDate.of(1970,8,8), "", "");
		registerVolunteer(vol, tenantIds);
		vol = createVolunteer("MJachs", "passme", "Melanie", "Jachs", LocalDate.of(1970, 7, 8), "", "");	
		registerVolunteer(vol, tenantIds);
	}

	private CoreVolunteer createVolunteer(String username, String password, String firstName, String lastName, LocalDate birthDate,
			String nickName, String fileName){
		CoreVolunteer volunteer = coreVolunteerRepository.findByUsername(username);
		if (volunteer == null) {
			volunteer = new CoreVolunteer();
			volunteer.setUsername(username);
			volunteer.setPassword(bCryptPasswordEncoder.encode(password));
			volunteer.setFirstname(firstName);
			volunteer.setLastname(lastName);
			ZoneId defaultZoneId = ZoneId.systemDefault();
			Date date = Date.from(birthDate.atStartOfDay(defaultZoneId).toInstant());
			volunteer.setBirthday(date);
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