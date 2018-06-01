package at.jku.cis.iVolunteer.lib.mapper.task;

import at.jku.cis.iVolunteer.lib.mapper.AbstractSpringMapper;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;

public class TaskMapper extends AbstractSpringMapper<Task, TaskDTO> {

	public final static TaskMapper INSTANCE = new TaskMapper();

	public TaskMapper() {
		super(Task.class, TaskDTO.class);
	}
}
