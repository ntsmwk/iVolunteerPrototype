package at.jku.cis.iVolunteer.lib.mapper.task.interaction;

import at.jku.cis.iVolunteer.lib.mapper.AbstractSpringMapper;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.task.interaction.dto.TaskInteractionDTO;

public class TaskInteractionMapper extends AbstractSpringMapper<TaskInteraction, TaskInteractionDTO> {

	public final static TaskInteractionMapper INSTANCE = new TaskInteractionMapper();

	public TaskInteractionMapper() {
		super(TaskInteraction.class, TaskInteractionDTO.class);
	}
}
