package at.jku.cis.iVolunteer.trustifier.contract;

import java.util.Date;

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
import at.jku.cis.iVolunteer.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassInstance;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.volunteer.profile.VolunteerTaskEntry;
import at.jku.cis.iVolunteer.trustifier.blockchain.BlockchainRestClient;
import at.jku.cis.iVolunteer.trustifier.hash.Hasher;
import at.jku.cis.iVolunteer.trustifier.marketplace.TrustifierMarketplaceRestClient;
import at.jku.cis.iVolunteer.trustifier.verification.VerificationFailureException;
import at.jku.cis.iVolunteer.trustifier.verification.Verifier;

@RestController
@RequestMapping("/trustifier/contractor")
public class Contractor {

	@Autowired private Hasher hasher;
	@Autowired private Verifier verifier;
	@Autowired private BlockchainRestClient blockchainRestClient;
	@Autowired private TrustifierMarketplaceRestClient marketplaceRestClient;

	@PostMapping("/task")
	public void publishTask(@RequestBody Task task) {
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

		if (!verifier.verifyPublishedTask(reservation.getTask())) {
			throw new VerificationFailureException();
		}

		try {
			String address = reservation.getSource().getAddress();
			TaskInteraction taskInteraction = marketplaceRestClient.reserve(address, authorization,
					reservation.getTask());

			blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction),
					taskInteraction.getTimestamp(), taskInteraction.getTask().getId(),
					reservation.getSource().getIdentifier(), taskInteraction.getOperation().toString());

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/unreserve")
	public void unreserveTask(@RequestBody TaskReservation reservation,
			@RequestHeader("Authorization") String authorization) {
		if (!verifier.verifyPublishedTask(reservation.getTask())) {
			throw new VerificationFailureException();
		}
		try {
			String address = reservation.getSource().getAddress();
			TaskInteraction taskInteraction = marketplaceRestClient.unreserve(address, authorization,
					reservation.getTask());
			blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction),
					taskInteraction.getTimestamp(), taskInteraction.getTask().getId(),
					reservation.getSource().getIdentifier(), taskInteraction.getOperation().toString());

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/assign")
	public void assignTask(@RequestBody TaskAssignment assignment,
			@RequestHeader("Authorization") String authorization) {
		if (!verifier.verifyPublishedTask(assignment.getTask())) {
			throw new VerificationFailureException();
		}

		try {
			String address = assignment.getSource().getAddress();
			TaskInteraction taskInteraction = marketplaceRestClient.assign(address, authorization, assignment.getTask(),
					assignment.getVolunteer());

			blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction),
					taskInteraction.getTimestamp(), taskInteraction.getTask().getId(),
					assignment.getSource().getIdentifier(), taskInteraction.getOperation().toString());

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/unassign")
	public void unassignTask(@RequestBody TaskAssignment assignment,
			@RequestHeader("Authorization") String authorization) {
		if (!verifier.verifyPublishedTask(assignment.getTask())) {
			throw new VerificationFailureException();
		}
		try {
			String address = assignment.getSource().getAddress();
			TaskInteraction taskInteraction = marketplaceRestClient.unassign(address, authorization,
					assignment.getTask(), assignment.getVolunteer());

			blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction),
					taskInteraction.getTimestamp(), taskInteraction.getTask().getId(),
					assignment.getSource().getIdentifier(), taskInteraction.getOperation().toString());

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/finish")
	public void finishTask(@RequestBody TaskCompletation completation,
			@RequestHeader("Authorization") String authorization) {
		if (!verifier.verifyPublishedTask(completation.getTask())) {
			throw new VerificationFailureException();
		}
		try {
			String address = completation.getSource().getAddress();
			TaskInteraction taskInteraction = marketplaceRestClient.finish(address, authorization,
					completation.getTask());

			blockchainRestClient.postTaskInteractionHash(hasher.generateHash(taskInteraction),
					taskInteraction.getTimestamp(), taskInteraction.getTask().getId(),
					completation.getSource().getIdentifier(), taskInteraction.getOperation().toString());

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/competenceEntry")
	public void publishCompetenceInstance(@RequestBody CompetenceClassInstance competenceInstance) {
		try {
			blockchainRestClient.postCompetenceHash(
					hasher.generateHash(competenceInstance),
					competenceInstance.getTimestamp(), 
					competenceInstance.getClassDefinition().getId(),
					competenceInstance.getMarketplaceId(), 
					competenceInstance.getUserId());

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