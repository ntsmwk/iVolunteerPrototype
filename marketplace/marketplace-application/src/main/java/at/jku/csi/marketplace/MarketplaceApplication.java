package at.jku.csi.marketplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import at.jku.csi.marketplace.participant.Employee;
import at.jku.csi.marketplace.participant.EmployeeRepository;
import at.jku.csi.marketplace.participant.Volunteer;
import at.jku.csi.marketplace.participant.VolunteerRepository;

@SpringBootApplication
public class MarketplaceApplication implements CommandLineRunner {

	private static final String MMUSTERMANN = "mmustermann";
	private static final String MWEISSENBEK = "mweissenbek";
	private static final String PSTARZER = "pstarzer";
	private static final String BROISER = "broiser";

	private static final String RAW_PASSWORD = "passme";
	
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private VolunteerRepository volunteerRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Employee employee = employeeRepository.findByUsername(MMUSTERMANN);
		if (employee == null) {
			employee = new Employee();
			employee.setUsername(MMUSTERMANN);
			employee.setPassword(bCryptPasswordEncoder.encode(RAW_PASSWORD));
			employee = employeeRepository.insert(employee);
		}

		Volunteer volunteer1 = volunteerRepository.findByUsername(BROISER);
		if (volunteer1 == null) {
			volunteer1 = new Volunteer();
			volunteer1.setUsername(BROISER);
			volunteer1.setPassword(bCryptPasswordEncoder.encode(RAW_PASSWORD));
			volunteer1 = volunteerRepository.insert(volunteer1);
		}

		Volunteer volunteer2 = volunteerRepository.findByUsername(PSTARZER);
		if (volunteer2 == null) {
			volunteer2 = new Volunteer();
			volunteer2.setUsername(PSTARZER);
			volunteer2.setPassword(bCryptPasswordEncoder.encode(RAW_PASSWORD));
			volunteer2 = volunteerRepository.insert(volunteer2);
		}
		
		Volunteer volunteer3 = volunteerRepository.findByUsername(MWEISSENBEK);
		if (volunteer3 == null) {
			volunteer3 = new Volunteer();
			volunteer3.setUsername(MWEISSENBEK);
			volunteer3.setPassword(bCryptPasswordEncoder.encode(RAW_PASSWORD));
			volunteer3 = volunteerRepository.insert(volunteer3);
		}

	}

}
