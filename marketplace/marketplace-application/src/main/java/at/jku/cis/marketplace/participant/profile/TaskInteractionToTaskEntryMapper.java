package at.jku.cis.marketplace.participant.profile;

import org.apache.commons.collections4.Transformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import at.jku.cis.marketplace.task.Task;
import at.jku.cis.marketplace.task.interaction.TaskInteraction;

@Component
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
		taskEntry.setTaskName(extractTaskTypeName(taskInteraction));
		taskEntry.setTaskDescription(extractTaskTypeDescription(taskInteraction));
		taskEntry.setMarketplaceId(marketplaceId);
		return taskEntry;
	}

	private String extractTaskId(TaskInteraction taskInteraction) {
		Task task = taskInteraction.getTask();
		return task == null ? null : task.getId();
	}

	private String extractTaskTypeName(TaskInteraction taskInteraction) {
		Task task = taskInteraction.getTask();
		return task == null || task.getType() == null ? null : task.getType().getName();
	}

	private String extractTaskTypeDescription(TaskInteraction taskInteraction) {
		Task task = taskInteraction.getTask();
		return task == null || task.getType() == null ? null : task.getType().getDescription();
	}
}