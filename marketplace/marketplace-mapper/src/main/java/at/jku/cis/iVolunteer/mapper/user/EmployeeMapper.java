package at.jku.cis.iVolunteer.mapper.user;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.user.Employee;
import at.jku.cis.iVolunteer.model.user.dto.EmployeeDTO;

@Mapper
public abstract class EmployeeMapper implements AbstractMapper<Employee, EmployeeDTO> {

}
