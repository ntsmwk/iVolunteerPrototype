package at.jku.csi.marketplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import at.jku.csi.marketplace.participant.Employee;
import at.jku.csi.marketplace.participant.EmployeeRepository;

@SpringBootApplication
public class MarketplaceApplication implements CommandLineRunner {

	private static final String PASSWORD = "passme";
	private static final String USERNAME = "mmustermann";

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Employee employee = employeeRepository.findByUsername(USERNAME);
		if (employee == null) {
			employee = new Employee();
			employee.setUsername(USERNAME);
			employee.setPassword(bCryptPasswordEncoder.encode(PASSWORD));
			employee = employeeRepository.insert(employee);
		}

	}

}
