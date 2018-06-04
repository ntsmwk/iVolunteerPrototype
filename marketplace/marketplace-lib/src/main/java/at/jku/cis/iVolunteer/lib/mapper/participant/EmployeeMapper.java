package at.jku.cis.iVolunteer.lib.mapper.participant;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.lib.mapper.AbstractSpringMapper;
import at.jku.cis.iVolunteer.model.participant.Employee;
import at.jku.cis.iVolunteer.model.participant.dto.EmployeeDTO;

@Service
public class EmployeeMapper extends AbstractSpringMapper<Employee, EmployeeDTO> {

	public EmployeeMapper() {
		super(Employee.class, EmployeeDTO.class);
	}
}
