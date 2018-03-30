package at.jku.cis.verifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.marketplace.blockchain.BlockchainRestClient;
import at.jku.cis.marketplace.blockchain.IHashObject;

@RestController
public class VerifierRestController {

	@Autowired
	private BlockchainRestClient blockchainRestClient;

	@PostMapping("/verify")
	public boolean verify(@RequestBody IHashObject hashObject) {
		return blockchainRestClient.isSimpleHashInBc(hashObject);
	}
}
