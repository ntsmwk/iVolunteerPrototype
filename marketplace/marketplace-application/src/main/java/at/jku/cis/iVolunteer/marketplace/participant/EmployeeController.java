package at.jku.cis.iVolunteer.marketplace.participant;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostMapping("/employee")
	public EmployeeDTO registerEmployee(@RequestBody EmployeeDTO employeeDto) {
		if (employeeRepository.findOne(employeeDto.getId()) == null) {
			throw new BadRequestException("Volunteer already registed");
		}
		return employeeMapper.toDTO(employeeRepository.insert(employeeMapper.toEntity(employeeDto)));
	}

}