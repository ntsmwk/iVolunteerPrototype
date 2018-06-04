package at.jku.cis.iVolunteer.trustifier.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import at.jku.cis.iVolunteer.model.contract.TaskAssignment;
import at.jku.cis.iVolunteer.model.contract.TaskCompletation;
import at.jku.cis.iVolunteer.model.contract.TaskReservation;
import at.jku.cis.iVolunteer.model.contract.VolunteerCompetenceEntry;
import at.jku.cis.iVolunteer.model.contract.VolunteerTaskEntry;
import at.jku.cis.iVolunteer.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.model.task.interaction.dto.TaskInteractionDTO;
import at.jku.cis.iVolunteer.trustifier.blockchain.BlockchainRestClient;
import at.jku.cis.iVolunteer.trustifier.hash.Hasher;
import at.jku.cis.iVolunteer.trustifier.marketplace.MarketplaceRestClient;
import at.jku.cis.iVolunteer.trustifier.verification.Verifier;
import java.util.Date;

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
	public void publishTask(@RequestBody TaskDTO task) {
		try {
			blockchainRestClient.postPublishedTaskHash(hasher.generateHash(task), new Date(), task.getId(),
					task.getMarketplaceId());
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/reserve")
	public void reserveTask(@RequestBody TaskReservation reservation,
			@RequestHeader("Authorization") String authorization) {
		verifier.verifyPublishedTask(reservation.getTask());

		try {
			String address = reservation.getSource().getAddress();
			TaskInteractionDTO taskInteraction = marketplaceRestClient.reserve(address, authorization,
					reservation.getTask());
			blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction),
					taskInteraction.getTimestamp(), taskInteraction.getTask().getId(),
					reservation.getSource().getIdentifier(), taskInteraction.getOperation());

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/unreserve")
	public void unreserveTask(@RequestBody TaskReservation reservation,
			@RequestHeader("Authorization") String authorization) {
		verifier.verifyPublishedTask(reservation.getTask());

		try {
			String address = reservation.getSource().getAddress();
			TaskInteractionDTO taskInteraction = marketplaceRestClient.unreserve(address, authorization,
					reservation.getTask());
			blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction),
					taskInteraction.getTimestamp(), taskInteraction.getTask().getId(),
					reservation.getSource().getIdentifier(), taskInteraction.getOperation());

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/assign")
	public void assignTask(@RequestBody TaskAssignment assignment,
			@RequestHeader("Authorization") String authorization) {
		verifier.verifyPublishedTask(assignment.getTask());

		try {
			String address = assignment.getSource().getAddress();
			TaskInteractionDTO taskInteraction = marketplaceRestClient.assign(address, authorization,
					assignment.getTask(), assignment.getVolunteer());

			blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction),
					taskInteraction.getTimestamp(), taskInteraction.getTask().getId(),
					assignment.getSource().getIdentifier(), taskInteraction.getOperation());

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/unassign")
	public void unassignTask(@RequestBody TaskAssignment assignment,
			@RequestHeader("Authorization") String authorization) {
		verifier.verifyPublishedTask(assignment.getTask());

		try {
			String address = assignment.getSource().getAddress();
			TaskInteractionDTO taskInteraction = marketplaceRestClient.unassign(address, authorization,
					assignment.getTask(), assignment.getVolunteer());

			blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction),
					taskInteraction.getTimestamp(), taskInteraction.getTask().getId(),
					assignment.getSource().getIdentifier(), taskInteraction.getOperation());

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/finish")
	public void finishTask(@RequestBody TaskCompletation completation,
			@RequestHeader("Authorization") String authorization) {
		verifier.verifyPublishedTask(completation.getTask());

		try {
			String address = completation.getSource().getAddress();
			TaskInteractionDTO taskInteraction = marketplaceRestClient.finish(address, authorization,
					completation.getTask());

			blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction),
					taskInteraction.getTimestamp(), taskInteraction.getTask().getId(),
					completation.getSource().getIdentifier(), taskInteraction.getOperation());

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/competenceEntry")
	public void publishCompetenceEntry(@RequestBody VolunteerCompetenceEntry vce) {
		try {

			blockchainRestClient.postCompetenceHash(hasher.generateHash(vce), vce.getTimestamp(), vce.getCompetenceId(),
					vce.getMarketplaceId(), vce.getVolunteerId());

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}

	}

	@PostMapping("/finishedTaskEntry")
	public void publishFinishedTaskEntry(@RequestBody VolunteerTaskEntry vte) {
		try {

			blockchainRestClient.postFinishedTaskHash(hasher.generateHash(vte), vte.getTimestamp(), vte.getTaskId(),
					vte.getMarketplaceId(), vte.getVolunteerId());

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);

		}
	}
}