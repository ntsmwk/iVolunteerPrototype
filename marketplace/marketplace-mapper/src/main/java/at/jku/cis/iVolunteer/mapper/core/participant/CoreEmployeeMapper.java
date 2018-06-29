package at.jku.cis.iVolunteer.mapper.core.participant;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.core.participant.CoreEmployee;
import at.jku.cis.iVolunteer.model.core.participant.dto.CoreEmployeeDTO;

@Mapper
public abstract class CoreEmployeeMapper implements AbstractMapper<CoreEmployee, CoreEmployeeDTO> {

}
