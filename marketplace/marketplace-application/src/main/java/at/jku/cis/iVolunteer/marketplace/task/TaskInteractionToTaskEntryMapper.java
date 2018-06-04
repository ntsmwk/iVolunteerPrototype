package at.jku.cis.iVolunteer.marketplace.task;

import org.apache.commons.collections4.Transformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.participant.profile.TaskEntry;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;

@Service
public class TaskInteractionToTaskEntryMapper implements Transformer<TaskInteraction, TaskEntry> {

	@Value("${marketplace.identifier}")
	private String marketplaceId;

	@Override
	public TaskEntry transform(TaskInteraction taskInteraction) {
		if (taskInteraction == null) {
			return null;
		}

		TaskEntry taskEntry = new TaskEntry();
		taskEntry.setId(taskInteraction.getId());
		taskEntry.setTimestamp(taskInteraction.getTimestamp());
		taskEntry.setTaskId(extractTaskId(taskInteraction));
		taskEntry.setTaskName(extractTaskName(taskInteraction));
		taskEntry.setTaskDescription(extractTaskDescription(taskInteraction));
		taskEntry.setMarketplaceId(marketplaceId);
		return taskEntry;
	}

	private String extractTaskId(TaskInteraction taskInteraction) {
		Task task = taskInteraction.getTask();
		return task == null ? null : task.getId();
	}

	private String extractTaskName(TaskInteraction taskInteraction) {
		Task task = taskInteraction.getTask();
		return task == null ? null : task.getName();
	}

	private String extractTaskDescription(TaskInteraction taskInteraction) {
		Task task = taskInteraction.getTask();
		return task == null ? null : task.getDescription();
	}
}