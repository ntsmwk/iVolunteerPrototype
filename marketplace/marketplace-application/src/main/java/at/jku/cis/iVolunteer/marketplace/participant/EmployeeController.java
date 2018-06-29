package at.jku.cis.iVolunteer.marketplace.participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.participant.EmployeeMapper;
import at.jku.cis.iVolunteer.model.participant.dto.EmployeeDTO;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeMapper employeeMapper;
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/employee/{id}")
	public EmployeeDTO findById(@PathVariable("id") String id) {
		return employeeMapper.toDTO(employeeRepository.findOne(id));
	}

}