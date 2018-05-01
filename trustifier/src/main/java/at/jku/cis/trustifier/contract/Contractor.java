package at.jku.cis.trustifier.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import at.jku.cis.trustifier.blockchain.BlockchainService;
import at.jku.cis.trustifier.exception.BadRequestException;
import at.jku.cis.trustifier.hash.Hasher;
import at.jku.cis.trustifier.marketplace.MarketplaceService;
import at.jku.cis.trustifier.model.task.Task;
import at.jku.cis.trustifier.model.task.interaction.TaskInteraction;

@RestController
@RequestMapping("/trustifier/contractor")
public class Contractor {

	@Autowired
	private Hasher hasher;
	@Autowired
	private BlockchainService blockchainService;
	
	@Autowired
	private MarketplaceService marketplaceService;

	@PostMapping("/task")
	public String publishTask(@RequestBody Task task) {
		try {
			String hash = hasher.generateHash(task);
			return blockchainService.postSimpleHash(hash).getHash();
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}
	
	@PostMapping("/task/assign")
	public String assignTask(@RequestBody TaskInteraction taskInteraction) {
		try {
			String taskHash = hasher.generateHash(taskInteraction.getTask());
			if(blockchainService.isSimpleHashInBc(taskHash)) {
				TaskInteraction response = marketplaceService.postTaskAssignment(taskInteraction);
				String hash = hasher.generateHash(response);
				blockchainService.postSimpleHash(hash);
				return hash;
			}
			return null;
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

}
