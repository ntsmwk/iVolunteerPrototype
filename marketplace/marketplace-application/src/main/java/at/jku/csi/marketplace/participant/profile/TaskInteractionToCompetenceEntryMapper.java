package at.jku.csi.marketplace.participant.profile;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.Transformer;
import org.springframework.stereotype.Component;

import at.jku.csi.marketplace.task.interaction.TaskInteraction;

@Component
public class TaskInteractionToCompetenceEntryMapper implements Transformer<TaskInteraction, Set<CompetenceEntry>> {

	@Override
	public Set<CompetenceEntry> transform(TaskInteraction taskInteraction) {
		Date timestamp = taskInteraction.getTimestamp();

		if (isValidateTaskInteraction(taskInteraction)) {
			return Collections.emptySet();
		}

		Set<CompetenceEntry> competenceEntries = new HashSet<>();
		taskInteraction.getTask().getType().getAcquirableCompetences().forEach(competence -> {
			CompetenceEntry competenceEntry = new CompetenceEntry();
			competenceEntry.setCometenceName(competence.getName());
			competenceEntry.setTimestamp(timestamp);
			competenceEntries.add(competenceEntry);
		});
		return competenceEntries;
	}

	private boolean isValidateTaskInteraction(TaskInteraction taskInteraction) {
		return taskInteraction != null && taskInteraction.getTask() != null && taskInteraction.getTask().getType() != null;
	}
}