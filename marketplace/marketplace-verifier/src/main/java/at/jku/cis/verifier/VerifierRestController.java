package at.jku.cis.verifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.marketplace.blockchain.BlockchainRestClient;
import at.jku.cis.marketplace.blockchain.IHashObject;

@Service
public class VerifierRestController {

	@Autowired
	private BlockchainRestClient blockchainRestClient;

	public boolean verify(IHashObject hashObject) {
		return blockchainRestClient.isSimpleHashInBc(hashObject);
	}
}
