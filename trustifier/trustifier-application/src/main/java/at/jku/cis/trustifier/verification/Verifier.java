package at.jku.cis.trustifier.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.trustifier.blockchain.BlockchainRestClient;
import at.jku.cis.trustifier.hash.Hasher;
import at.jku.cis.trustifier.model.task.Task;

@RestController
@RequestMapping("/trustifier/verifier")
public class Verifier {

	@Autowired
	private Hasher hasher;
	@Autowired
	private BlockchainRestClient blockchainRestClient;

	@GetMapping("verfiy")
	public boolean verify(Task task) {
		String hash = hasher.generateHash(task);
		return blockchainRestClient.isCompetenceEntryHashInBc(hash) || blockchainRestClient.isTaskEntryHashInBc(hash)
				|| blockchainRestClient.isTaskInteractionHashInBc(hash);
	}

}
