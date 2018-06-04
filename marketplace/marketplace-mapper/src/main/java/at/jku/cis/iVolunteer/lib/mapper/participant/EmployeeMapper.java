package at.jku.cis.iVolunteer.lib.mapper.participant;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.lib.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.participant.Employee;
import at.jku.cis.iVolunteer.model.participant.dto.EmployeeDTO;

@Mapper
public abstract class EmployeeMapper implements AbstractMapper<Employee, EmployeeDTO> {

}
