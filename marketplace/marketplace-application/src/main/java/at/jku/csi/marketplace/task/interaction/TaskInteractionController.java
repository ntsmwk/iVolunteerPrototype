package at.jku.csi.marketplace.task.interaction;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.csi.marketplace.participant.Participant;
import at.jku.csi.marketplace.participant.Volunteer;
import at.jku.csi.marketplace.participant.VolunteerRepository;
import at.jku.csi.marketplace.security.LoginService;
import at.jku.csi.marketplace.security.ParticipantRole;
import at.jku.csi.marketplace.task.Task;
import at.jku.csi.marketplace.task.TaskRepository;

@RestController
public class TaskInteractionController {
	@Autowired
	private TaskInteractionRepository taskInteractionRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private LoginService loginService;

	@Autowired
	private VolunteerRepository volunteerRepository;

	@GetMapping("/task/{taskId}/interaction")
	public List<TaskInteraction> findByTaskId(@PathVariable("taskId") String taskId,
			@RequestParam(value = "operation", required = false) TaskOperation operation) {
		Task task = taskRepository.findOne(taskId);
		if (operation == null) {
			return taskInteractionRepository.findByTask(task);
		}
		return taskInteractionRepository.findByTaskAndOperation(task, operation);
	}

	@GetMapping("/volunteer/{id}/interaction")
	public List<TaskInteraction> findByVolunteerId(@PathVariable("id") String id) {
		return taskInteractionRepository.findByVolunteer(id);
	}

	@GetMapping("/task/{id}/reserved")
	public List<Participant> findReservedVolunteersByTaskId(@PathVariable("id") String taskId) {
		Task task = taskRepository.findOne(taskId);

		return taskInteractionRepository.findByTask(task).stream()
				.filter(ti -> ti.getParticipant() instanceof Volunteer)
				.filter(ti -> {
					TaskOperation latestOperation = getLatestTaskInteractions(ti.getParticipant().getId(), taskId)
							.get(0).getOperation();
					return latestOperation == TaskVolunteerOperation.RESERVED
							|| latestOperation == TaskVolunteerOperation.UNASSIGNED;
				}).map(ti -> ti.getParticipant()).collect(Collectors.toList());

	}

	@GetMapping("/volunteer/isReserved/{id}")
	public boolean isLoggedInVolunteerAlreadyReserved(@PathVariable("id") String taskId) {
		if (loginService.getLoggedInParticipantRole().equals(ParticipantRole.VOLUNTEER)) {
			Participant participant = loginService.getLoggedInParticipant();
			List<TaskInteraction> taskInteractions = getLatestTaskInteractions(participant.getId(), taskId);
			if (!taskInteractions.isEmpty()) {
				return taskInteractions.get(0).getOperation() != TaskVolunteerOperation.UNRESERVED;
			}
		}
		return false;
	}

	@GetMapping("/volunteer/isAssigned/{id}")
	public boolean isLoggedInVolunteerAlreadyAssigned(@PathVariable("id") String taskId) {
		if (loginService.getLoggedInParticipantRole().equals(ParticipantRole.VOLUNTEER)) {
			Participant participant = loginService.getLoggedInParticipant();
			List<TaskInteraction> taskInteractions = getLatestTaskInteractions(participant.getId(), taskId);
			if (!taskInteractions.isEmpty()) {
				return taskInteractions.get(0).getOperation() == TaskVolunteerOperation.ASSIGNED;
			}
		}
		return false;
	}

	@GetMapping("/volunteer/{volunteerId}/isAssigned/{taskId}")
	public boolean isVolunteerAlreadyAssigned(@PathVariable("volunteerId") String volunteerId,
			@PathVariable("taskId") String taskId) {
		Volunteer volunteer = volunteerRepository.findOne(volunteerId);
		if (volunteer == null) {
			throw new BadRequestException("Could not find volunteer");
		}
		List<TaskInteraction> taskInteractions = getLatestTaskInteractions(volunteerId, taskId);
		if (!taskInteractions.isEmpty()) {
			return taskInteractions.get(0).getOperation() == TaskVolunteerOperation.ASSIGNED;
		}
		return false;

	}

	@PostMapping("/volunteer/reserve")
	public void reserveForTask(@RequestBody String taskId) {
		if (loginService.getLoggedInParticipantRole().equals(ParticipantRole.VOLUNTEER)) {
			Participant participant = loginService.getLoggedInParticipant();
			List<TaskInteraction> latestTaskInteractions = getLatestTaskInteractions(participant.getId(), taskId);

			if (latestTaskInteractions.isEmpty()
					|| latestTaskInteractions.get(0).getOperation() == TaskVolunteerOperation.UNRESERVED) {
				createTaskInteraction(taskRepository.findOne(taskId), participant, TaskVolunteerOperation.RESERVED);
			}
		}
	}

	@PostMapping("/volunteer/unreserve")
	public void unreserveForTask(@RequestBody String id) {
		if (loginService.getLoggedInParticipantRole().equals(ParticipantRole.VOLUNTEER)) {
			Participant participant = loginService.getLoggedInParticipant();
			List<TaskInteraction> taskVolunteerInteractions = taskInteractionRepository
					.findByVolunteerAndTask(participant.getId(), id);
			if (!taskVolunteerInteractions.isEmpty()
					&& (taskVolunteerInteractions.get(0).getOperation() == TaskVolunteerOperation.RESERVED
							|| taskVolunteerInteractions.get(0).getOperation() == TaskVolunteerOperation.UNASSIGNED)) {
				createTaskInteraction(taskRepository.findOne(id), participant, TaskVolunteerOperation.UNRESERVED);
			}
		}
	}

	@GetMapping("/task/{id}/assigned")
	public List<Participant> findAssignedVolunteersByTaskId(@PathVariable("id") String taskId) {
		return taskInteractionRepository.findByTask(taskRepository.findOne(taskId)).stream()
				.filter(ti -> ti.getOperation() == TaskVolunteerOperation.ASSIGNED)
				.filter(ti -> getLatestTaskInteractions(ti.getParticipant().getId(), taskId).get(0)
						.getOperation() == TaskVolunteerOperation.ASSIGNED)
				.map(ti -> ti.getParticipant()).collect(Collectors.toList());
	}

	@PostMapping("/task/{taskId}/assign/{volunteerId}")
	public void assignForTask(@PathVariable("taskId") String taskId, @PathVariable("volunteerId") String volunteerId) {
		createTaskInteraction(taskRepository.findOne(taskId), volunteerRepository.findOne(volunteerId),
				TaskVolunteerOperation.ASSIGNED);
	}

	@PostMapping("/task/{taskId}/unassign/{volunteerId}")
	public void unassignForTask(@PathVariable("taskId") String taskId,
			@PathVariable("volunteerId") String volunteerId) {
		createTaskInteraction(taskRepository.findOne(taskId), volunteerRepository.findOne(volunteerId),
				TaskVolunteerOperation.UNASSIGNED);
	}

	private List<TaskInteraction> getLatestTaskInteractions(String participantId, String taskId) {
		return taskInteractionRepository.findSortedByVolunteerAndTask(participantId, taskId,
				new Sort(Sort.Direction.DESC, "timestamp"));
	}

	private void createTaskInteraction(Task task, Participant participant, TaskOperation taskOperation) {
		TaskInteraction taskInteraction = new TaskInteraction();
		taskInteraction.setOperation(taskOperation);
		taskInteraction.setParticipant(participant);
		taskInteraction.setTask(task);
		taskInteraction.setTimestamp(new Date());
		taskInteractionRepository.insert(taskInteraction);
	}
}
