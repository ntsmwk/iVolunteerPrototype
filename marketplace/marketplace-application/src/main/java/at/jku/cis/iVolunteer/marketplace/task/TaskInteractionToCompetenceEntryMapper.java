package at.jku.cis.iVolunteer.marketplace.task;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections4.Transformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.participant.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;

@Service
public class TaskInteractionToCompetenceEntryMapper implements Transformer<TaskInteraction, Set<CompetenceEntry>> {

	@Value("${marketplace.identifier}")
	private String marketplaceId;

	@Override
	public Set<CompetenceEntry> transform(TaskInteraction taskInteraction) {
		Date timestamp = taskInteraction.getTimestamp();

		if (!isValidateTaskInteraction(taskInteraction)) {
			return Collections.emptySet();
		}

		Set<CompetenceEntry> competenceEntries = new HashSet<>();
		taskInteraction.getTask().getAcquirableCompetences().forEach(competence -> {
			CompetenceEntry competenceEntry = new CompetenceEntry();
			competenceEntry.setId(UUID.randomUUID().toString());
			competenceEntry.setCompetenceId(competence.getId());
			competenceEntry.setCompetenceName(competence.getName());
			competenceEntry.setMarketplaceId(marketplaceId);
			competenceEntry.setTimestamp(timestamp);
			competenceEntries.add(competenceEntry);
		});
		return competenceEntries;
	}

	private boolean isValidateTaskInteraction(TaskInteraction taskInteraction) {
		return taskInteraction != null && taskInteraction.getTask() != null;
	}
}