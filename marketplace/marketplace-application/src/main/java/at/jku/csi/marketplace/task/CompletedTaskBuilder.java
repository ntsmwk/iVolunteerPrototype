package at.jku.csi.marketplace.task;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import at.jku.csi.marketplace.competence.Competence;
import at.jku.csi.marketplace.task.interaction.TaskInteraction;

public class CompletedTaskBuilder {

	public static CompletedTask build(TaskInteraction taskInteraction) {
		CompletedTask completedTask = new CompletedTask();
		completedTask.setInteractionId(taskInteraction.getId());
		completedTask.setTaskId(taskInteraction.getTask().getId());
		completedTask.setTaskName(taskInteraction.getTask().getType().getName());
		completedTask.setTaskDescription(taskInteraction.getTask().getType().getDescription());

		List<String> requiredComptences = new ArrayList<>();
		for (Competence c : taskInteraction.getTask().getType().getRequiredCompetences()) {
			requiredComptences.add(c.getName());
		}
		completedTask.setRequiredComptences(requiredComptences);

		List<String> acquirableComptences = new ArrayList<>();
		for (Competence c : taskInteraction.getTask().getType().getAcquirableCompetences()) {
			acquirableComptences.add(c.getName());
		}
		completedTask.setAcquirableComptences(acquirableComptences);

		completedTask.setParticipantId(taskInteraction.getParticipant().getId());
		completedTask.setTimestamp(taskInteraction.getTimestamp());

		JsonObject json = new JsonObject();
		json.addProperty("interactionId", completedTask.getInteractionId());
		json.addProperty("taskId", completedTask.getTaskId());
		json.addProperty("taskName", completedTask.getTaskName());
		json.addProperty("taskDescription", completedTask.getTaskDescription());
		json.addProperty("requiredComptences", String.join(", ", completedTask.getRequiredComptences()));
		json.addProperty("acquirableComptences", String.join(", ", completedTask.getAcquirableComptences()));
		json.addProperty("participantId", completedTask.getParticipantId());
		json.addProperty("timestamp", completedTask.getTimestamp().toString());
		completedTask.setJson(json);

		return completedTask;
	}
}
