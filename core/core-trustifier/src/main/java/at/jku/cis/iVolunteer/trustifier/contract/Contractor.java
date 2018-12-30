package at.jku.cis.iVolunteer.trustifier.contract;

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
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.task.interaction.dto.TaskInteractionDTO;
import at.jku.cis.iVolunteer.model.volunteer.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.TaskEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.VolunteerCompetenceEntryDTO;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.VolunteerTaskEntryDTO;
import at.jku.cis.iVolunteer.trustifier.blockchain.BlockchainRestClient;
import at.jku.cis.iVolunteer.trustifier.marketplace.TrustifierMarketplaceRestClient;
import at.jku.cis.iVolunteer.trustifier.verification.VerificationFailureException;
import at.jku.cis.iVolunteer.trustifier.verification.Verifier;

@RestController
@RequestMapping("/trustifier/contractor")
public class Contractor {

	@Autowired
	private Verifier verifier;
	@Autowired
	private BlockchainRestClient blockchainRestClient;
	@Autowired
	private TrustifierMarketplaceRestClient marketplaceRestClient;

	@PostMapping("/task")
	public void publishTask(@RequestBody TaskDTO tDTO) {
		try {
			Task t = new Task();
			t.setPublishedDate(tDTO.getStatusDate());
			t.setId(tDTO.getId());
			t.setMarketplaceId(tDTO.getMarketplaceId());
			blockchainRestClient.postPublishedTask(t);
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/reserve")
	public void reserveTask(@RequestBody TaskReservationDTO reservation, @RequestHeader("Authorization") String authorization) {

		if (!verifier.verifyPublishedTask(reservation.getTask())) {
			throw new VerificationFailureException();
		}

		try {
			String address = reservation.getSource().getAddress();
			TaskInteractionDTO taskInteractionDTO = marketplaceRestClient.reserve(address, authorization, reservation.getTask());
			TaskInteraction taskInteraction = new TaskInteraction();
			taskInteraction.setTimestamp(taskInteractionDTO.getTimestamp());
			taskInteraction.setTaskId(taskInteractionDTO.getTask().getId());
			taskInteraction.setMarketplaceId(taskInteractionDTO.getTask().getMarketplaceId());
			taskInteraction.setTaskInteractionType(taskInteractionDTO.getOperation());
			taskInteraction.setVolunteerId(taskInteractionDTO.getParticipant().getId());
			blockchainRestClient.postTaskInteraction(taskInteraction);

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/unreserve")
	public void unreserveTask(@RequestBody TaskReservationDTO reservation, @RequestHeader("Authorization") String authorization) {
		if (!verifier.verifyPublishedTask(reservation.getTask())) {
			throw new VerificationFailureException();
		}
		try {
			String address = reservation.getSource().getAddress();
			TaskInteractionDTO taskInteractionDTO = marketplaceRestClient.unreserve(address, authorization, reservation.getTask());
			TaskInteraction taskInteraction = new TaskInteraction();
			taskInteraction.setTimestamp(taskInteractionDTO.getTimestamp());
			taskInteraction.setTaskId(taskInteractionDTO.getTask().getId());
			taskInteraction.setMarketplaceId(taskInteractionDTO.getTask().getMarketplaceId());
			taskInteraction.setTaskInteractionType(taskInteractionDTO.getOperation());
			taskInteraction.setVolunteerId(taskInteractionDTO.getParticipant().getId());
			blockchainRestClient.postTaskInteraction(taskInteraction);

		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/assign")
	public void assignTask(@RequestBody TaskAssignmentDTO assignment, @RequestHeader("Authorization") String authorization) {
		if (!verifier.verifyPublishedTask(assignment.getTask())) {
			throw new VerificationFailureException();
		}

		try {
			String address = assignment.getSource().getAddress();
			TaskInteractionDTO taskInteractionDTO = marketplaceRestClient.assign(address, authorization,
					assignment.getTask(), assignment.getVolunteer());
			TaskInteraction taskInteraction = new TaskInteraction();
			taskInteraction.setTimestamp(taskInteractionDTO.getTimestamp());
			taskInteraction.setTaskId(taskInteractionDTO.getTask().getId());
			taskInteraction.setMarketplaceId(taskInteractionDTO.getTask().getMarketplaceId());
			taskInteraction.setTaskInteractionType(taskInteractionDTO.getOperation());
			taskInteraction.setVolunteerId(taskInteractionDTO.getParticipant().getId());
			blockchainRestClient.postTaskInteraction(taskInteraction);
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/unassign")
	public void unassignTask(@RequestBody TaskAssignmentDTO assignment, @RequestHeader("Authorization") String authorization) {
		if (!verifier.verifyPublishedTask(assignment.getTask())) {
			throw new VerificationFailureException();
		}
		try {
			String address = assignment.getSource().getAddress();
			TaskInteractionDTO taskInteractionDTO = marketplaceRestClient.unassign(address, authorization,
					assignment.getTask(), assignment.getVolunteer());
			TaskInteraction taskInteraction = new TaskInteraction();
			taskInteraction.setTimestamp(taskInteractionDTO.getTimestamp());
			taskInteraction.setTaskId(taskInteractionDTO.getTask().getId());
			taskInteraction.setMarketplaceId(taskInteractionDTO.getTask().getMarketplaceId());
			taskInteraction.setTaskInteractionType(taskInteractionDTO.getOperation());
			taskInteraction.setVolunteerId(taskInteractionDTO.getParticipant().getId());
			blockchainRestClient.postTaskInteraction(taskInteraction);
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/task/finish")
	public void finishTask(@RequestBody TaskCompletationDTO completation, @RequestHeader("Authorization") String authorization) {
		if (!verifier.verifyPublishedTask(completation.getTask())) {
			throw new VerificationFailureException();
		}
		try {
			String address = completation.getSource().getAddress();
			TaskInteractionDTO taskInteractionDTO = marketplaceRestClient.finish(address, authorization, completation.getTask());
			TaskInteraction taskInteraction = new TaskInteraction();
			taskInteraction.setTimestamp(taskInteractionDTO.getTimestamp());
			taskInteraction.setTaskId(taskInteractionDTO.getTask().getId());
			taskInteraction.setMarketplaceId(taskInteractionDTO.getTask().getMarketplaceId());
			taskInteraction.setTaskInteractionType(taskInteractionDTO.getOperation());
			taskInteraction.setVolunteerId(taskInteractionDTO.getParticipant().getId());
			blockchainRestClient.postTaskInteraction(taskInteraction);
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/competenceEntry")
	public void publishCompetenceEntry(@RequestBody VolunteerCompetenceEntryDTO vce) {
		try {
			CompetenceEntry entry = new CompetenceEntry();
			entry.setCompetenceId(vce.getCompetenceId());
			entry.setMarketplaceId(vce.getMarketplaceId());
			entry.setVolunteerId(vce.getVolunteerId());
			entry.setTimestamp(vce.getTimestamp());
			blockchainRestClient.postCompetence(entry);			
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}

	@PostMapping("/finishedTaskEntry")
	public void publishFinishedTaskEntry(@RequestBody VolunteerTaskEntryDTO vte) {
		try {
			TaskEntry entry = new TaskEntry();
			entry.setId(vte.getId());
			entry.setTimestamp(vte.getTimestamp());
			entry.setTaskId(vte.getTaskId());
			entry.setMarketplaceId(vte.getMarketplaceId());
			entry.setVolunteerId(vte.getVolunteerId());
			blockchainRestClient.postFinishedTask(entry);
		} catch (RestClientException ex) {
			throw new BadRequestException(ex);
		}
	}
}