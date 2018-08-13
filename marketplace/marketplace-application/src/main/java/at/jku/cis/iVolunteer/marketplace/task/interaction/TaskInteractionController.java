package at.jku.cis.iVolunteer.marketplace.task.interaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.task.interaction.TaskInteractionMapper;
import at.jku.cis.iVolunteer.marketplace.participant.VolunteerRepository;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.marketplace.task.TaskRepository;
import at.jku.cis.iVolunteer.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.model.exception.PreConditionFailedException;
import at.jku.cis.iVolunteer.model.participant.Participant;
import at.jku.cis.iVolunteer.model.participant.Volunteer;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.TaskOperation;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.task.interaction.TaskVolunteerOperation;
import at.jku.cis.iVolunteer.model.task.interaction.dto.TaskInteractionDTO;

@RestController
public class TaskInteractionController {

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskInteractionMapper taskInteractionMapper;
	@Autowired
	private TaskInteractionService taskInteractionService;
	@Autowired
	private TaskInteractionRepository taskInteractionRepository;
	@Autowired
	private VolunteerRepository volunteerRepository;

	@Autowired
	private LoginService loginService;

	@GetMapping("/task/{taskId}/interaction")
	public List<TaskInteractionDTO> findByTaskId(@PathVariable("taskId") String taskId,
			@RequestParam(value = "operation", required = false) TaskOperation operation) {

		Task task = findAndVerifyTaskById(taskId);

		if (operation == null) {
			return taskInteractionMapper.toDTOs(taskInteractionRepository.findByTask(task));
		}
		return taskInteractionMapper.toDTOs(taskInteractionRepository.findByTaskAndOperation(task, operation));
	}

	@GetMapping("/task/{id}/participant")
	public List<Participant> findParticipantsByTaskOperation(@PathVariable("id") String taskId,
			@RequestParam(name = "operation", required = false) TaskVolunteerOperation taskOperation) {
		Task task = findAndVerifyTaskById(taskId);

		Set<Volunteer> volunteers = new HashSet<>();
		if (taskOperation == null) {
			volunteers.addAll(taskInteractionService.findReservedVolunteersByTask(task));
			volunteers.addAll(taskInteractionService.findAssignedVolunteersByTask(task));
		} else if (TaskVolunteerOperation.RESERVED == taskOperation) {
			volunteers.addAll(taskInteractionService.findReservedVolunteersByTask(task));
		} else if (TaskVolunteerOperation.ASSIGNED == taskOperation) {
			volunteers.addAll(taskInteractionService.findAssignedVolunteersByTask(task));
		} else {
			throw new BadRequestException();
		}
		return new ArrayList<>(volunteers);
	}

	@PostMapping("/task/{taskId}/reserve")
	public TaskInteraction reserveForTask(@PathVariable("taskId") String taskId,
			@RequestHeader("Authorization") String token) {
		Task task = findAndVerifyTaskById(taskId);
		TaskInteraction latestTaskInteraction = getLatestTaskInteraction(task, loginService.getLoggedInParticipant());

		if (latestTaskInteraction == null
				|| latestTaskInteraction.getOperation() == TaskVolunteerOperation.UNRESERVED) {
			return createTaskInteraction(task, loginService.getLoggedInParticipant(), TaskVolunteerOperation.RESERVED);
		} else {
			throw new PreConditionFailedException("Cannot reserve! Volunteer is already reserved or assigned.");
		}
	}

	@PostMapping("/task/{taskId}/unreserve")
	public TaskInteractionDTO unreserveForTask(@PathVariable("taskId") String taskId,
			@RequestHeader("Authorization") String token) {
		Task task = findAndVerifyTaskById(taskId);
		TaskInteraction latestTaskInteraction = getLatestTaskInteraction(task, loginService.getLoggedInParticipant());

		if (latestTaskInteraction != null && (latestTaskInteraction.getOperation() == TaskVolunteerOperation.RESERVED
				|| latestTaskInteraction.getOperation() == TaskVolunteerOperation.UNASSIGNED)) {
			return taskInteractionMapper.toDTO(createTaskInteraction(task, loginService.getLoggedInParticipant(),
					TaskVolunteerOperation.UNRESERVED));
		} else {
			throw new PreConditionFailedException("Cannot cancel reservation! Volunteer is not reserved.");
		}
	}

	@PostMapping("/task/{taskId}/assign")
	public TaskInteractionDTO assignForTask(@PathVariable("taskId") String taskId,
			@RequestParam("volunteerId") String volunteerId) {
		Task task = findAndVerifyTaskById(taskId);
		Volunteer volunteer = findAndVerifyVolunteerById(volunteerId);
		TaskInteraction latestTaskInteraction = getLatestTaskInteraction(task, volunteer);
		if (latestTaskInteraction != null && latestTaskInteraction.getOperation() == TaskVolunteerOperation.RESERVED) {
			return taskInteractionMapper.toDTO(createTaskInteraction(task, volunteer, TaskVolunteerOperation.ASSIGNED));
		}
		throw new PreConditionFailedException("Cannot assign. Volunteer is already assigned or not reserved.");
	}

	@PostMapping("/task/{taskId}/unassign")
	public TaskInteractionDTO unassignForTask(@PathVariable("taskId") String taskId,
			@RequestParam("volunteerId") String volunteerId) {
		Task task = findAndVerifyTaskById(taskId);
		Volunteer volunteer = findAndVerifyVolunteerById(volunteerId);
		TaskInteraction latestTaskInteraction = getLatestTaskInteraction(task, volunteer);
		if (latestTaskInteraction.getOperation() == TaskVolunteerOperation.ASSIGNED) {
			return taskInteractionMapper
					.toDTO(createTaskInteraction(task, volunteer, TaskVolunteerOperation.UNASSIGNED));
		}
		throw new PreConditionFailedException("Cannot cancel assignment! Volunteer is not assigned.");
	}

	private TaskInteraction getLatestTaskInteraction(Task task, Participant participant) {
		List<TaskInteraction> taskInteractions = taskInteractionRepository.findSortedByTaskAndParticipant(task,
				participant, new Sort(Sort.Direction.DESC, "timestamp"));
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

	private Task findAndVerifyTaskById(String taskId) {
		Task task = taskRepository.findOne(taskId);
		if (task == null) {
			throw new BadRequestException();
		}
		return task;
	}

	private Volunteer findAndVerifyVolunteerById(String volunteerId) {
		Volunteer volunteer = volunteerRepository.findOne(volunteerId);
		if (volunteer == null) {
			throw new BadRequestException();
		}
		return volunteer;
	}
}
