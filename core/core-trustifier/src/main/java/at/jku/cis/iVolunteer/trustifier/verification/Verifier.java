package at.jku.cis.iVolunteer.trustifier.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.model.task.interaction.dto.TaskInteractionDTO;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.CompetenceEntryDTO;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.TaskEntryDTO;
import at.jku.cis.iVolunteer.trustifier.blockchain.BlockchainRestClient;
import at.jku.cis.iVolunteer.trustifier.hash.Hasher;

@RestController
@RequestMapping("/trustifier/verifier")
public class Verifier {

	@Autowired private Hasher hasher;
	@Autowired private BlockchainRestClient blockchainRestClient;

	@PostMapping("/taskInteraction")
	public boolean verifyTaskInteraction(@RequestBody TaskInteractionDTO taskInteraction) {
		return (blockchainRestClient.getTaskInteractionHash(hasher.generateHash(taskInteraction)) == null) ? false
				: true;
	}

	@PostMapping("/finishedTaskEntry")
	public boolean verifyFinishedTaskEntry(@RequestBody TaskEntryDTO taskEntry) {
		return (blockchainRestClient.getFinishedTaskHash(hasher.generateHash(taskEntry)) == null) ? false : true;
	}

	@PostMapping("/publishedTask")
	public boolean verifyPublishedTask(@RequestBody TaskDTO task) {
		 return (blockchainRestClient.getPublishedTaskHash(hasher.generateHash(task))
		 == null) ? false : true;
	}

	@PostMapping("/competenceEntry")
	public boolean verifyCompetence(@RequestBody CompetenceEntryDTO competenceEntry) {
		return (blockchainRestClient.getCompetenceHash(hasher.generateHash(competenceEntry)) == null) ? false : true;
	}

}