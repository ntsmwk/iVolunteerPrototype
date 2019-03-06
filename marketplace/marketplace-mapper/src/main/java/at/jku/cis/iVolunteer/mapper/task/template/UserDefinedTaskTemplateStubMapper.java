package at.jku.cis.iVolunteer.mapper.task.template;

import org.mapstruct.Mapper;
import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.dto.UserDefinedTaskTemplateStubDTO;

@Mapper()
public abstract class UserDefinedTaskTemplateStubMapper implements AbstractMapper<UserDefinedTaskTemplate, UserDefinedTaskTemplateStubDTO> {

}