package at.jku.cis.iVolunteer.trustifier.contract;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import at.jku.cis.iVolunteer.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.trustifier.blockchain.BcClassInstance;
import at.jku.cis.iVolunteer.trustifier.blockchain.BlockchainRestClient;
import at.jku.cis.iVolunteer.trustifier.hash.Hasher;

@RestController
@RequestMapping("/trustifier/contractor")
public class Contractor {

	@Autowired private Hasher hasher;
	@Autowired private BlockchainRestClient blockchainRestClient;

	@PostMapping("/classInstance")
	public void publishClassInstance(@RequestBody ClassInstance classInstance) {
		try {
			blockchainRestClient.postClassInstance(hasher.generateHash(classInstance), classInstance.getMarketplaceId(),
					classInstance.getUserId());
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);

		}
	}

	@PostMapping("/classInstances")
	public void publishClassInstances(@RequestBody List<ClassInstance> classInstances) {
		try {
			// @formatter:off
			List<BcClassInstance> list = classInstances
				.stream()
				.map(ci -> new BcClassInstance(hasher.generateHash(ci), ci.getUserId()))
				.collect(Collectors.toList());
			// @formatter:on

			blockchainRestClient.postClassInstanceArray(list);
		} catch (RestClientException e) {
			throw new BadRequestException(e);
		}
	}

}