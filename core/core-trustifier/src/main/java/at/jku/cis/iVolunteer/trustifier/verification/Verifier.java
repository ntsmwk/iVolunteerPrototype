package at.jku.cis.iVolunteer.trustifier.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.task.interaction.dto.TaskInteractionDTO;
import at.jku.cis.iVolunteer.model.volunteer.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.TaskEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.CompetenceEntryDTO;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.TaskEntryDTO;
import at.jku.cis.iVolunteer.trustifier.blockchain.BlockchainRestClient;

@RestController
@RequestMapping("/trustifier/verifier")
public class Verifier {

	@Autowired private BlockchainRestClient blockchainRestClient;

	@PostMapping("/taskInteraction/{marketplaceId}")
	public boolean verifyTaskInteraction(@RequestBody TaskInteractionDTO taskInteractionDTO) {
		TaskInteraction taskInteraction = new TaskInteraction();
		taskInteraction.setTimestamp(taskInteractionDTO.getTimestamp());
		taskInteraction.setId(taskInteractionDTO.getId());
		taskInteraction.setMarketplaceId(taskInteractionDTO.getTask().getMarketplaceId());
		taskInteraction.setTaskInteractionType(taskInteractionDTO.getOperation());
		return (blockchainRestClient.getTaskInteraction(taskInteraction) == null) ? false : true;
	}

	@PostMapping("/finishedTaskEntry")
	public boolean verifyFinishedTaskEntry(@RequestBody TaskEntryDTO taskEntry) {
		TaskEntry entry = new TaskEntry();
		entry.setId(taskEntry.getId());
		entry.setTimestamp(taskEntry.getTimestamp());
		entry.setMarketplaceId(taskEntry.getMarketplaceId());
		entry.setVolunteerId(taskEntry.getVolunteerId());
		return (blockchainRestClient.getFinishedTask(entry) == null) ? false : true;
	}

	@PostMapping("/publishedTask")
	public boolean verifyPublishedTask(@RequestBody TaskDTO taskDTO) {
		Task t = new Task();
		t.setId(taskDTO.getId());
		t.setMarketplaceId(taskDTO.getMarketplaceId());
		t.setPublishedDate(taskDTO.getPublishedDate());
		return (blockchainRestClient.getPublishedTask(t) == null) ? false : true;
	}

	@PostMapping("/competenceEntry")
	public boolean verifyCompetence(@RequestBody CompetenceEntryDTO competenceEntry) {
		CompetenceEntry entry = new CompetenceEntry();
		entry.setId(competenceEntry.getId());
		entry.setMarketplaceId(competenceEntry.getMarketplaceId());
		entry.setTimestamp(competenceEntry.getTimestamp());
		entry.setVolunteerId(competenceEntry.getVolunteerId());
		return (blockchainRestClient.getCompetence(entry) == null) ? false : true;
	}

}