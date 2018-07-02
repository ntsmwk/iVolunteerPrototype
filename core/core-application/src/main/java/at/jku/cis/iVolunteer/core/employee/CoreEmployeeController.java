package at.jku.cis.iVolunteer.core.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.core.participant.CoreEmployeeMapper;
import at.jku.cis.iVolunteer.model.core.participant.dto.CoreEmployeeDTO;

@RestController("/employee")
public class CoreEmployeeController {

	@Autowired
	private CoreEmployeeMapper coreEmployeeMapper;

	@Autowired
	private CoreEmployeeRepository coreEmployeeRepository;

	@GetMapping("/{coreEmployeeId}")
	public CoreEmployeeDTO getCoreEmployee(@PathVariable("coreEmployeeId") String coreEmployeeId) {
		return coreEmployeeMapper.toDTO(coreEmployeeRepository.findOne(coreEmployeeId));
	}

}
