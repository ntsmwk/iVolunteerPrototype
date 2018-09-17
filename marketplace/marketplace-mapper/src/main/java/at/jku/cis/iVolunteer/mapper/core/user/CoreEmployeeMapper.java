package at.jku.cis.iVolunteer.mapper.core.user;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreEmployee;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreEmployeeDTO;

@Mapper
public abstract class CoreEmployeeMapper implements AbstractMapper<CoreEmployee, CoreEmployeeDTO> {

}
