package at.jku.cis.iVolunteer.lib.mapper.task.template;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.lib.mapper.AbstractSpringMapper;
import at.jku.cis.iVolunteer.model.task.template.TaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.TaskTemplateDTO;

@Service
public class TaskTemplateMapper extends AbstractSpringMapper<TaskTemplate, TaskTemplateDTO> {

	public TaskTemplateMapper() {
		super(TaskTemplate.class, TaskTemplateDTO.class);
	}
}
