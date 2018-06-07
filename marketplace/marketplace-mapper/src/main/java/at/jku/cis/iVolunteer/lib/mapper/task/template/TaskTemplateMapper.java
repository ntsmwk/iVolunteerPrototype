package at.jku.cis.iVolunteer.lib.mapper.task.template;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.lib.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.lib.mapper.competence.CompetenceMapper;
import at.jku.cis.iVolunteer.model.task.template.TaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.TaskTemplateDTO;

@Mapper(uses = { CompetenceMapper.class })
public abstract class TaskTemplateMapper implements AbstractMapper<TaskTemplate, TaskTemplateDTO> {

}
