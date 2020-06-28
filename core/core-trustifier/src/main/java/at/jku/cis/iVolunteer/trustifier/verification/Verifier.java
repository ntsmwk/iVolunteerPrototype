package at.jku.cis.iVolunteer.trustifier.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.trustifier.blockchain.BlockchainRestClient;
import at.jku.cis.iVolunteer.trustifier.hash.Hasher;

@RestController
@RequestMapping("/trustifier/verifier")
public class Verifier {

	@Autowired private Hasher hasher;
	@Autowired private BlockchainRestClient blockchainRestClient;

	@PostMapping("/classInstance")
	public boolean verifyCompetence(@RequestBody ClassInstance classInstance) {
		return (blockchainRestClient.getClassInstanceHash(hasher.generateHash(classInstance)) == null) ? false : true;
	}

}