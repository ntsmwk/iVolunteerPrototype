package at.jku.cis.iVolunteer.marketplace.participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/employee/{id}")
	public Employee findById(@PathVariable("id") String id) {
		return employeeRepository.findOne(id);
	}

}