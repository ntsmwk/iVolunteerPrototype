package at.jku.csi.marketplace.task;

import at.jku.csi.marketplace.task.interaction.TaskInteraction;
import jersey.repackaged.com.google.common.collect.Lists;

public class CompletedTaskBuilder {

	public static CompletedTask build(TaskInteraction taskInteraction) {
		CompletedTask completedTask = new CompletedTask();
		completedTask.setInteractionId(taskInteraction.getId());
		completedTask.setTaskId(taskInteraction.getTask().getId());
		completedTask.setTaskName(taskInteraction.getTask().getType().getName());
		completedTask.setTaskDescription(taskInteraction.getTask().getType().getDescription());
		completedTask.setRequiredComptences(Lists.newArrayList(
				taskInteraction.getTask().getType().getRequiredCompetences().stream().map(comp -> comp.getName())));
		completedTask.setAcquirableComptences(Lists.newArrayList(
				taskInteraction.getTask().getType().getAcquirableCompetences().stream().map(comp -> comp.getName())));
		completedTask.setParticipantId(taskInteraction.getParticipant().getId());
		completedTask.setTimestamp(taskInteraction.getTimestamp());

		return completedTask;
	}
}
