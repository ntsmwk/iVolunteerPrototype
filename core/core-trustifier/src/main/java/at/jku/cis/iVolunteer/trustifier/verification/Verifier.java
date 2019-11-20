package at.jku.cis.iVolunteer.trustifier.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassInstance;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.volunteer.profile.TaskEntry;
import at.jku.cis.iVolunteer.trustifier.blockchain.BlockchainRestClient;
import at.jku.cis.iVolunteer.trustifier.hash.Hasher;

@RestController
@RequestMapping("/trustifier/verifier")
public class Verifier {

	@Autowired private Hasher hasher;
	@Autowired private BlockchainRestClient blockchainRestClient;

	@PostMapping("/taskInteraction")
	public boolean verifyTaskInteraction(@RequestBody TaskInteraction taskInteraction) {
		return (blockchainRestClient.getTaskInteractionHash(hasher.generateHash(taskInteraction)) == null) ? false
				: true;
	}

	@PostMapping("/finishedTaskEntry")
	public boolean verifyFinishedTaskEntry(@RequestBody TaskEntry taskEntry) {
		return (blockchainRestClient.getFinishedTaskHash(hasher.generateHash(taskEntry)) == null) ? false : true;
	}

	@PostMapping("/publishedTask")
	public boolean verifyPublishedTask(@RequestBody Task task) {
		return (blockchainRestClient.getPublishedTaskHash(hasher.generateHash(task)) == null) ? false : true;
	}

	@PostMapping("/competenceEntry")
	public boolean verifyCompetence(@RequestBody CompetenceClassInstance competenceInstance) {
		return (blockchainRestClient.getCompetenceHash(hasher.generateHash(competenceInstance)) == null) ? false : true;
	}

}