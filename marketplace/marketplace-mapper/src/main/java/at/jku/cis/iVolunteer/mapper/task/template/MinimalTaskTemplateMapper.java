//TODO: Just for testing - DELETE


package at.jku.cis.iVolunteer.mapper.task.template;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.task.template.MinimalTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.dto.MinimalTaskTemplateDTO;

@Mapper
public abstract class MinimalTaskTemplateMapper 
	implements AbstractMapper<MinimalTaskTemplate, MinimalTaskTemplateDTO> { }
