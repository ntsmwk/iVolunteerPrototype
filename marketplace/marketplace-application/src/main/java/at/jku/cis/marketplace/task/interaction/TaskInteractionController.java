package at.jku.cis.marketplace.task.interaction;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.marketplace.exception.BadRequestException;
import at.jku.cis.marketplace.exception.ForbiddenException;
import at.jku.cis.marketplace.participant.Participant;
import at.jku.cis.marketplace.participant.Volunteer;
import at.jku.cis.marketplace.participant.VolunteerRepository;
import at.jku.cis.marketplace.security.LoginService;
import at.jku.cis.marketplace.security.ParticipantRole;
import at.jku.cis.marketplace.task.Task;
import at.jku.cis.marketplace.task.TaskRepository;
import at.jku.cis.marketplace.task.TaskStatus;

@RestController
public class TaskInteractionController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskInteractionRepository taskInteractionRepository;
	@Autowired
	private VolunteerRepository volunteerRepository;

	@GetMapping("/task/{taskId}/interaction")
	public List<TaskInteraction> findByTaskId(@PathVariable("taskId") String taskId,
			@RequestParam(value = "volunteerId", required = false) String volunteerId,
			@RequestParam(value = "operation", required = false) TaskOperation operation) {
		Task task = taskRepository.findOne(taskId);

		if (operation != null) {
			return taskInteractionRepository.findByTaskAndOperation(task, operation);
		}
		if (StringUtils.isNotBlank(volunteerId)) {
			Volunteer volunteer = volunteerRepository.findOne(volunteerId);
			return taskInteractionRepository.findByTaskAndParticipant(task, volunteer);
		}
		return taskInteractionRepository.findByTask(task);
	}

	@GetMapping("/task/{id}/participant")
	public List<Participant> findParticipantsByTaskOperation(@PathVariable("id") String taskId,
			@RequestParam("operation") TaskOperation taskOperation) {

		Task task = taskRepository.findOne(taskId);
		Set<Participant> participants = taskInteractionRepository.findByTask(task).stream()
				.map((ti) -> ti.getParticipant()).collect(Collectors.toSet());

		return participants.stream().filter((participant) -> {
			TaskOperation latestOperation = getLatestTaskInteraction(task, participant).getOperation();
			if (TaskVolunteerOperation.RESERVED == taskOperation) {
				return latestOperation == TaskVolunteerOperation.RESERVED
						|| latestOperation == TaskVolunteerOperation.UNASSIGNED;
			} else if (TaskVolunteerOperation.ASSIGNED == taskOperation) {
				return latestOperation == TaskVolunteerOperation.ASSIGNED;
			} else {
				throw new BadRequestException();
			}
		}).collect(Collectors.toList());
	}

	@GetMapping("/task/{taskId}/participant/{participantId}")
	public TaskOperation getLatestTaskOperation(@PathVariable("taskId") String taskId,
			@PathVariable("participantId") String participantId) {
		Volunteer volunteer = volunteerRepository.findOne(participantId);
		if (volunteer != null) {
			TaskInteraction taskInteraction = getLatestTaskInteraction(taskRepository.findOne(taskId), volunteer);
			return taskInteraction != null ? taskInteraction.getOperation() : null;
		}
		return null;
	}

	@PostMapping("/task/{taskId}/reserve")
	public TaskInteraction reserveForTask(@PathVariable("taskId") String taskId) {
		if (ParticipantRole.VOLUNTEER != loginService.getLoggedInParticipantRole()) {
			throw new ForbiddenException();
		}

		Task task = taskRepository.findOne(taskId);
		if (task == null || TaskStatus.PUBLISHED != task.getStatus()) {
			throw new BadRequestException();
		}

		TaskInteraction latestTaskInteraction = getLatestTaskInteraction(task, loginService.getLoggedInParticipant());

		if (latestTaskInteraction == null
				|| latestTaskInteraction.getOperation() == TaskVolunteerOperation.UNRESERVED) {
			return createTaskInteraction(task, loginService.getLoggedInParticipant(), TaskVolunteerOperation.RESERVED);
		} else {
			throw new BadRequestException();
		}
	}

	@PostMapping("/task/{taskId}/unreserve")
	public TaskInteraction unreserveForTask(@PathVariable("taskId") String id) {
		if (ParticipantRole.VOLUNTEER != loginService.getLoggedInParticipantRole()) {
			throw new ForbiddenException();
		}

		Task task = taskRepository.findOne(id);
		if (task == null || TaskStatus.PUBLISHED != task.getStatus()) {
			throw new BadRequestException();
		}

		Participant participant = loginService.getLoggedInParticipant();
		TaskInteraction lastedTaskInteraction = getLatestTaskInteraction(task, participant);
		if (lastedTaskInteraction.getOperation() == TaskVolunteerOperation.RESERVED
				|| lastedTaskInteraction.getOperation() == TaskVolunteerOperation.UNASSIGNED) {
			return createTaskInteraction(task, participant, TaskVolunteerOperation.UNRESERVED);
		} else {
			throw new BadRequestException();
		}
	}

	@PostMapping("/task/{taskId}/assign/{volunteerId}")
	public TaskInteraction assignForTask(@PathVariable("taskId") String taskId, @PathVariable("volunteerId") String volunteerId) {
		Volunteer volunteer = volunteerRepository.findOne(volunteerId);
		return createTaskInteraction(taskRepository.findOne(taskId), volunteer, TaskVolunteerOperation.ASSIGNED);
	}

	@PostMapping("/task/{taskId}/unassign/{volunteerId}")
	public TaskInteraction unassignForTask(@PathVariable("taskId") String taskId,
			@PathVariable("volunteerId") String volunteerId) {
		Volunteer volunteer = volunteerRepository.findOne(volunteerId);
		return createTaskInteraction(taskRepository.findOne(taskId), volunteer, TaskVolunteerOperation.UNASSIGNED);
	}

	private TaskInteraction getLatestTaskInteraction(Task task, Participant participant) {
		Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
		List<TaskInteraction> taskInteractions = taskInteractionRepository.findSortedByTaskAndParticipant(task,
				participant, sort);
		return taskInteractions.isEmpty() ? null : taskInteractions.get(0);
	}

	private TaskInteraction createTaskInteraction(Task task, Participant participant, TaskOperation taskOperation) {
		TaskInteraction taskInteraction = new TaskInteraction();
		taskInteraction.setOperation(taskOperation);
		taskInteraction.setParticipant(participant);
		taskInteraction.setTask(task);
		taskInteraction.setTimestamp(new Date());
		return taskInteractionRepository.insert(taskInteraction);
	}
}
