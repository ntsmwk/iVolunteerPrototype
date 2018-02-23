package at.jku.csi.marketplace.participant;

import java.util.List;

import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/employees")
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@GetMapping("/employee/{id}")
	public Employee findById(@PathVariable("id") String id) {
		return employeeRepository.findOne(id);
	}

	@PostMapping("/employee")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.insert(employee);
	}

	@PutMapping("/employee/{id}")
	public Employee updateEmployee(@PathVariable("id") String id, @RequestBody Employee employee) {
		if (employeeRepository.exists(id)) {
			throw new NotAcceptableException();
		}
		return employeeRepository.save(employee);
	}

	@DeleteMapping("/employee/{id}")
	public void deleteEmployee(@PathVariable("id") String id) {
		employeeRepository.delete(id);
	}

}
