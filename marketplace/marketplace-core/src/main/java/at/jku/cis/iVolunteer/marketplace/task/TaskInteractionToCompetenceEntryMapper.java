package at.jku.cis.iVolunteer.marketplace.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.Transformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassInstance;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;

@Service
public class TaskInteractionToCompetenceEntryMapper
		implements Transformer<TaskInteraction, List<CompetenceClassInstance>> {

	@Value("${marketplace.identifier}") private String marketplaceId;

	@Override
	public List<CompetenceClassInstance> transform(TaskInteraction taskInteraction) {
		Date timestamp = taskInteraction.getTimestamp();

		if (!isValidateTaskInteraction(taskInteraction)) {
			return Collections.emptyList();
		}

		List<CompetenceClassInstance> competenceInstances = new ArrayList<>();
		taskInteraction.getTask().getAcquirableCompetences().forEach(competenceDefinition -> {
			CompetenceClassInstance competenceInstance = new CompetenceClassInstance();
			competenceInstance.setId(UUID.randomUUID().toString());
			competenceInstance.setClassDefinitionId(competenceDefinition.getId());
			competenceInstance.setName(competenceInstance.getName());
			competenceInstance.setMarketplaceId(marketplaceId);
			competenceInstance.setTimestamp(timestamp);
			competenceInstances.add(competenceInstance);
		});
		return competenceInstances;
	}

	private boolean isValidateTaskInteraction(TaskInteraction taskInteraction) {
		return taskInteraction != null && taskInteraction.getTask() != null;
	}
}