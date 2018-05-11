package at.jku.cis.trustifier.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.trustifier.blockchain.BlockchainRestClient;
import at.jku.cis.trustifier.hash.Hasher;
import at.jku.cis.trustifier.model.participant.profile.CompetenceEntry;
import at.jku.cis.trustifier.model.participant.profile.TaskEntry;
import at.jku.cis.trustifier.model.task.Task;

@RestController
@RequestMapping("/trustifier/verifier")
public class Verifier {

	@Autowired
	private Hasher hasher;
	@Autowired
	private BlockchainRestClient blockchainRestClient;

	@PostMapping("task")
	public boolean verifyTask(@RequestBody Task task) {
		return blockchainRestClient.isSimpleHashInBc(hasher.generateHash(task));
	}

	@PostMapping("taskEntry")
	public boolean verifyTaskEntry(@RequestBody TaskEntry taskEntry) {
		return blockchainRestClient.isSimpleHashInBc(hasher.generateHash(taskEntry));
	}

	@PostMapping("competenceEntry")
	public boolean verifyCompetence(@RequestBody CompetenceEntry competenceEntry) {
		return blockchainRestClient.isSimpleHashInBc(hasher.generateHash(competenceEntry));
	}

}
