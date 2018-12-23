package at.jku.cis.iVolunteer.trustifier.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
import at.jku.cis.iVolunteer.trustifier.hash.Hasher;

@RestController
@RequestMapping("/trustifier/verifier")
public class Verifier {

//	@Autowired private Hasher hasher;

	@Autowired private BlockchainRestClient blockchainRestClient;

//	@PostMapping("/taskInteraction")
	@PostMapping("/taskInteraction/{marketplaceId}")
	public boolean verifyTaskInteraction(@RequestBody TaskInteractionDTO taskInteraction, @PathVariable("marketplaceId") String marketplaceId) {
//		return (blockchainRestClient.getTaskInteractionHash(hasher.generateHash(taskInteraction)) == null) ? false : true;
		TaskInteraction entry = new TaskInteraction();
		entry.setId(taskInteraction.getId());
		entry.setTimestamp(taskInteraction.getTimestamp());
		entry.setTaskInteractionType("verifyTaskInteraction"); // TODO ??
		entry.setMarketplaceId(marketplaceId);
		return (blockchainRestClient.getTaskInteraction(entry) == null) ? false : true;
	}

	@PostMapping("/finishedTaskEntry/{volunteerId}")
//	@PostMapping("/finishedTaskEntry")
	public boolean verifyFinishedTaskEntry(@RequestBody TaskEntryDTO taskEntry, @PathVariable("volunteerId") String volunteerId) {
//		return (blockchainRestClient.getFinishedTaskHash(hasher.generateHash(taskEntry)) == null) ? false : true;
		TaskEntry entry = new TaskEntry();
		entry.setId(taskEntry.getId());
		entry.setTimestamp(taskEntry.getTimestamp());
		entry.setMarketplaceId(taskEntry.getMarketplaceId());
		entry.setVolunteerId(volunteerId);
		return (blockchainRestClient.getFinishedTask(entry) == null) ? false : true;
	}

	@PostMapping("/publishedTask")
	public boolean verifyPublishedTask(@RequestBody TaskDTO task) {
//		return (blockchainRestClient.getPublishedTaskHash(hasher.generateHash(task)) == null) ? false : true;
		TaskInteraction t = new TaskInteraction();
		t.setId(task.getId());
		t.setTimestamp(task.getStartDate());
		t.setMarketplaceId(task.getMarketplaceId());
		return (blockchainRestClient.getPublishedTask(t) == null) ? false : true;
	}

	@PostMapping("/competenceEntry")
	public boolean verifyCompetence(@RequestBody CompetenceEntryDTO competenceEntry) {
//		return (blockchainRestClient.getCompetenceHash(hasher.generateHash(competenceEntry)) == null) ? false : true;
		return (blockchainRestClient.getCompetence(new CompetenceEntry(competenceEntry.getId(),
		competenceEntry.getCompetenceId(), competenceEntry.getMarketplaceId(),
		competenceEntry.getVolunteerId(), competenceEntry.getTimestamp())) == null) ? false : true;
	}

}