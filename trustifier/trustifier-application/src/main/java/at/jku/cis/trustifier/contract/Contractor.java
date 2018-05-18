package at.jku.cis.trustifier.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import at.jku.cis.trustifier.blockchain.BlockchainRestClient;
import at.jku.cis.trustifier.exception.BadRequestException;
import at.jku.cis.trustifier.hash.Hasher;
import at.jku.cis.trustifier.marketplace.MarketplaceRestClient;
import at.jku.cis.trustifier.model.participant.profile.CompetenceEntry;
import at.jku.cis.trustifier.model.participant.profile.TaskEntry;
import at.jku.cis.trustifier.model.task.Task;
import at.jku.cis.trustifier.model.task.interaction.TaskInteraction;
import at.jku.cis.trustifier.verification.Verifier;

@RestController
@RequestMapping("/trustifier/contractor")
public class Contractor {

	@Autowired
	private Hasher hasher;
	@Autowired
	private Verifier verifier;
	@Autowired
	private BlockchainRestClient blockchainRestClient;
	@Autowired
	private MarketplaceRestClient marketplaceRestClient;

	@PostMapping("/task")
	public String publishTask(@RequestBody Task task) {
		try {
			return blockchainRestClient.postTaskInteractionHash(hasher.generateHash(task)).getHash();
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/reserve")
	public String reserveTask(@RequestBody TaskReservation reservation,
			@RequestHeader("Authorization") String authorization) {
		verifier.verifyTaskInteraction(reservation.getTask());

		try {
			String address = reservation.getSource().getAddress();
			TaskInteraction taskInteraction = marketplaceRestClient.reserve(address, authorization,
					reservation.getTask());
			return blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction)).getHash();

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/unreserve")
	public String unreserveTask(@RequestBody TaskReservation reservation,
			@RequestHeader("Authorization") String authorization) {
		verifier.verifyTaskInteraction(reservation.getTask());

		try {
			String address = reservation.getSource().getAddress();
			TaskInteraction taskInteraction = marketplaceRestClient.unreserve(address, authorization,
					reservation.getTask());
			return blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction)).getHash();

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/assign")
	public String assignTask(@RequestBody TaskAssignment assignment,
			@RequestHeader("Authorization") String authorization) {
		verifier.verifyTaskInteraction(assignment.getTask());

		try {
			String address = assignment.getSource().getAddress();
			TaskInteraction taskInteraction = marketplaceRestClient.assign(address, authorization, assignment.getTask(),
					assignment.getVolunteer());
			return blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction)).getHash();
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/unassign")
	public String unassignTask(@RequestBody TaskAssignment assignment,
			@RequestHeader("Authorization") String authorization) {
		verifier.verifyTaskInteraction(assignment.getTask());

		try {
			String address = assignment.getSource().getAddress();
			TaskInteraction taskInteraction = marketplaceRestClient.unassign(address, authorization,
					assignment.getTask(), assignment.getVolunteer());
			return blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction)).getHash();

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/finish")
	public String finishTask(@RequestBody TaskCompletation completation,
			@RequestHeader("Authorization") String authorization) {
		verifier.verifyTaskInteraction(completation.getTask());

		try {
			String address = completation.getSource().getAddress();
			TaskInteraction taskInteraction = marketplaceRestClient.finish(address, authorization,
					completation.getTask());
			return blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction)).getHash();
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/competenceEntry")
	public String publishCompetenceEntry(@RequestBody CompetenceEntry competenceEntry) {
		try {
			return blockchainRestClient.postCompetenceEntryHash(hasher.generateHash(competenceEntry)).getHash();
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}

	}

	@PostMapping("/taskEntry")
	public String publishTaskEntry(@RequestBody TaskEntry taskEntry) {
		try {
			return blockchainRestClient.postTaskEntryHash(hasher.generateHash(taskEntry)).getHash();
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);

		}
	}
}
