package at.jku.cis.iVolunteer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import at.jku.cis.iVolunteer.core.employee.CoreEmployeeRepository;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.participant.CoreEmployee;
import at.jku.cis.iVolunteer.model.core.participant.CoreVolunteer;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;

@SpringBootApplication
public class iVolunteerApplication {

	private static final String MARKETPLACE_DEFAULT = "default";
	private static final String MARKETPLACE_ID = "MP1";
	private static final String MARKETPLACE_URL = "http://localhost:8080";
	
	private static final String MMUSTERMANN = "mmustermann";
	private static final String MWEISSENBEK = "mweissenbek";
	private static final String PSTARZER = "pstarzer";
	private static final String BROISER = "broiser";

	private static final String RAW_PASSWORD = "passme";

	@Autowired
	private MarketplaceRepository marketplaceRepository;

	@Autowired
	private CoreEmployeeRepository coreEmployeeRepository;
	@Autowired
	private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(iVolunteerApplication.class, args);
	}

	@PostConstruct
	private void init() {
		Marketplace marketplace = createMarketplace();
		createEmployee(marketplace);
		createVolunteer(BROISER, RAW_PASSWORD, marketplace);
		createVolunteer(PSTARZER, RAW_PASSWORD, marketplace);
		createVolunteer(MWEISSENBEK, RAW_PASSWORD, marketplace);
	}

	private Marketplace createMarketplace() {
		Marketplace marketplace = marketplaceRepository.findOne(MARKETPLACE_DEFAULT);
		if (marketplace == null) {
			marketplace = new Marketplace();
			marketplace.setMarketplaceId(MARKETPLACE_ID);
			marketplace.setUrl(MARKETPLACE_URL);
			marketplace.setId(MARKETPLACE_DEFAULT);
			marketplaceRepository.insert(marketplace);
		}
		return marketplace;
	}

	private void createEmployee(Marketplace marketplace) {
		CoreEmployee employee = coreEmployeeRepository.findByUsername(MMUSTERMANN);
		if (employee == null) {
			employee = new CoreEmployee();
			employee.setUsername(MMUSTERMANN);
			employee.setPassword(bCryptPasswordEncoder.encode(RAW_PASSWORD));
			employee.getRegisteredMarketplaces().add(marketplace);
			employee = coreEmployeeRepository.insert(employee);
		}
	}

	private void createVolunteer(String username, String password, Marketplace marketplace) {
		CoreVolunteer volunteer = coreVolunteerRepository.findByUsername(username);
		if (volunteer == null) {
			volunteer = new CoreVolunteer();
			volunteer.setUsername(username);
			volunteer.setPassword(bCryptPasswordEncoder.encode(password));
			volunteer.getRegisteredMarketplaces().add(marketplace);
			volunteer = coreVolunteerRepository.insert(volunteer);
		}
	}
}
