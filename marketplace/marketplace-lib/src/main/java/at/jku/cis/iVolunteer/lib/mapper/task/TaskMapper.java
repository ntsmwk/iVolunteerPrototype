package at.jku.cis.iVolunteer.lib.mapper.task;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.lib.mapper.AbstractSpringMapper;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;

@Service
public class TaskMapper extends AbstractSpringMapper<Task, TaskDTO> {

	public TaskMapper() {
		super(Task.class, TaskDTO.class);
	}
}
