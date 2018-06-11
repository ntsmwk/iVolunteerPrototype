package at.jku.cis.iVolunteer.trustifier.contract;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import at.jku.cis.iVolunteer.model.contract.TaskAssignmentDTO;
import at.jku.cis.iVolunteer.model.contract.TaskCompletationDTO;
import at.jku.cis.iVolunteer.model.contract.TaskReservationDTO;
import at.jku.cis.iVolunteer.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.model.participant.profile.dto.VolunteerCompetenceEntryDTO;
import at.jku.cis.iVolunteer.model.participant.profile.dto.VolunteerTaskEntryDTO;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.model.task.interaction.dto.TaskInteractionDTO;
import at.jku.cis.iVolunteer.trustifier.blockchain.BlockchainRestClient;
import at.jku.cis.iVolunteer.trustifier.hash.Hasher;
import at.jku.cis.iVolunteer.trustifier.marketplace.MarketplaceRestClient;
import at.jku.cis.iVolunteer.trustifier.verification.VerificationFailureException;
import at.jku.cis.iVolunteer.trustifier.verification.Verifier;

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
	public void reserveTask(@RequestBody TaskReservationDTO reservation,
			@RequestHeader("Authorization") String authorization) {

		if (!verifier.verifyPublishedTask(reservation.getTask())) {
			throw new VerificationFailureException();
		}

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
	public void unreserveTask(@RequestBody TaskReservationDTO reservation,
			@RequestHeader("Authorization") String authorization) {
		if (!verifier.verifyPublishedTask(reservation.getTask())) {
			throw new VerificationFailureException();
		}
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
	public void assignTask(@RequestBody TaskAssignmentDTO assignment,
			@RequestHeader("Authorization") String authorization) {
		if (!verifier.verifyPublishedTask(assignment.getTask())) {
			throw new VerificationFailureException();
		}

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
	public void unassignTask(@RequestBody TaskAssignmentDTO assignment,
			@RequestHeader("Authorization") String authorization) {
		if (!verifier.verifyPublishedTask(assignment.getTask())) {
			throw new VerificationFailureException();
		}
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
	public void finishTask(@RequestBody TaskCompletationDTO completation,
			@RequestHeader("Authorization") String authorization) {
		if (!verifier.verifyPublishedTask(completation.getTask())) {
			throw new VerificationFailureException();
		}
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
	public void publishCompetenceEntry(@RequestBody VolunteerCompetenceEntryDTO vce) {
		try {

			blockchainRestClient.postCompetenceHash(hasher.generateHash(vce), vce.getTimestamp(), vce.getCompetenceId(),
					vce.getMarketplaceId(), vce.getVolunteerId());

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}

	}

	@PostMapping("/finishedTaskEntry")
	public void publishFinishedTaskEntry(@RequestBody VolunteerTaskEntryDTO vte) {
		try {

			blockchainRestClient.postFinishedTaskHash(hasher.generateHash(vte), vte.getTimestamp(), vte.getTaskId(),
					vte.getMarketplaceId(), vte.getVolunteerId());

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);

		}
	}
}