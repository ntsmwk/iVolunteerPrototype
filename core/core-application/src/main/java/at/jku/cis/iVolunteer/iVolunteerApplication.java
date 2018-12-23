package at.jku.cis.iVolunteer;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import at.jku.cis.iVolunteer.core.helpseeker.CoreHelpSeekerRepository;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.volunteer.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.TaskEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.CompetenceEntryDTO;
import at.jku.cis.iVolunteer.trustifier.blockchain.BlockchainRestClient;

@SpringBootApplication
public class iVolunteerApplication {

	private static final String MARKETPLACE_ID = "0eaf3a6281df11e8adc0fa7ae01bbebc";
	private static final String MARKETPLACE_NAME = "Marketplace 1";
	private static final String MARKETPLACE_SHORTNAME = "MP1";
	private static final String MARKETPLACE_URL = "http://localhost:8080";

	private static final String MMUSTERMANN = "mmustermann";
	private static final String MWEISSENBEK = "mweissenbek";
	private static final String PSTARZER = "pstarzer";
	private static final String BROISER = "broiser";

	private static final String RAW_PASSWORD = "passme";

	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired private CoreHelpSeekerRepository coreHelpSeekerRepository;
	@Autowired private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired private MarketplaceRepository marketplaceRepository;
	@Autowired private BlockchainRestClient blockchainRestClient;
	
	public static void main(String[] args) {
		SpringApplication.run(iVolunteerApplication.class, args);
	}

	@PostConstruct
	private void init() {
		createMarketplace();
		createHelpSeeker(MMUSTERMANN, RAW_PASSWORD);
		createVolunteer(BROISER, RAW_PASSWORD);
		createVolunteer(PSTARZER, RAW_PASSWORD);
		createVolunteer(MWEISSENBEK, RAW_PASSWORD);
		
//		System.out.println("\n##### starting test #####");
//		Task task = new Task();
//		task.setStartDate(new Timestamp(118, 11, 5, 21, 25, 39, 527000000));
//		task.setId("testId01");
//		task.setMarketplaceId("Marketplace1");
//		testPostPublishedTask(task);
//		testGetPublishedTask(task);
//		System.out.println("#####  ending test  #####\n");
	}

	// ######################### TESTS begin #########################
	
//	private static CompetenceEntry e = new CompetenceEntry("", "competenceId01", "marketplace01", 
//			"volunteer1", new Date());
	
	private void testPostMarketplace() {
		blockchainRestClient.postNewMarketplace("Marketplace8");
	}
	
	private void testPostCompetence() {
		// TODO Auto-generated method stub
//		CompetenceEntryDTO entry = new CompetenceEntryDTO();
//		entry.setTimestamp(new Date());
//		entry.setCompetenceId("competenceId01");
//		entry.setMarketplaceId("Marketplace1");
//		entry.setVolunteerId("Volunteer1");
		CompetenceEntry entry = new CompetenceEntry("", "competenceId01", "marketplace01",
				"volunteer1", new Date());
		blockchainRestClient.postCompetence(entry);
	}
	
	private void testGetCompetence() {
		Timestamp timestamp = new Timestamp(118, 11, 5, 21, 25, 39, 527000000);
		CompetenceEntry entry = new CompetenceEntry("", "competenceId01", "marketplace01", 
				"volunteer1", timestamp);
		blockchainRestClient.getCompetence(entry);
	}
	
	private void testPostPublishedTask(Task task) {
//		Task task = new Task();
//		task.setStartDate(new Timestamp(118, 11, 5, 21, 25, 39, 527000000));
//		task.setId("testId01");
//		task.setMarketplaceId("Marketplace1");
//		blockchainRestClient.postPublishedTask(task);
	}
	
	private void testGetPublishedTask(Task task) {
//		Task task = new Task();
//		task.setStartDate(new Timestamp(118, 11, 6, 23, 53, 17, 467000000));
//		task.setId("testId01");
//		task.setMarketplaceId("Marketplace1");
//		blockchainRestClient.getPublishedTask(task);
	}
	
	private void testPostTaskInteraction() {
		TaskInteraction task = new TaskInteraction();
		task.setTimestamp(new Date());
		task.setId("testId01");
		Task t = new Task();
		t.setMarketplaceId("Marketplace1");
		task.setTask(t);
		blockchainRestClient.postTaskInteraction(task);
	}
	
	private void testPostFinishedTask() {
		TaskEntry entry = new TaskEntry();
		entry.setId("");
		entry.setTimestamp(new Date());
		entry.setTaskId("testId01");
		entry.setMarketplaceId("Marketplace1");
		entry.setVolunteerId("Volunteer1");
		blockchainRestClient.postFinishedTask(entry);
	}
	
	private void testGetFinishedTask() {
		TaskEntry entry = new TaskEntry();
		entry.setId("");
		entry.setTimestamp(new Timestamp(118, 11, 6, 23, 47, 55, 060000000));
		entry.setTaskId("testId01");
		entry.setMarketplaceId("Marketplace1");
		entry.setVolunteerId("Volunteer1");
		blockchainRestClient.getFinishedTask(entry);
	}

	// ######################### TESTS end #########################
	
	private Marketplace createMarketplace() {
		Marketplace marketplace = marketplaceRepository.findOne(MARKETPLACE_ID);
		if (marketplace == null) {
			marketplace = new Marketplace();
			marketplace.setId(MARKETPLACE_ID);
			marketplace.setUrl(MARKETPLACE_URL);
			marketplace.setName(MARKETPLACE_NAME);
			marketplace.setShortName(MARKETPLACE_SHORTNAME);
			marketplaceRepository.insert(marketplace);
		}
		return marketplace;
	}

	private CoreHelpSeeker createHelpSeeker(String username, String password) {
		CoreHelpSeeker helpSeeker = coreHelpSeekerRepository.findByUsername(username);
		if (helpSeeker == null) {
			helpSeeker = new CoreHelpSeeker();
			helpSeeker.setUsername(username);
			helpSeeker.setPassword(bCryptPasswordEncoder.encode(password));
			helpSeeker = coreHelpSeekerRepository.insert(helpSeeker);
		}
		return helpSeeker;
	}

	private CoreVolunteer createVolunteer(String username, String password) {
		CoreVolunteer volunteer = coreVolunteerRepository.findByUsername(username);
		if (volunteer == null) {
			volunteer = new CoreVolunteer();
			volunteer.setUsername(username);
			volunteer.setPassword(bCryptPasswordEncoder.encode(password));
			volunteer = coreVolunteerRepository.insert(volunteer);
		}
		return volunteer;
	}
}
