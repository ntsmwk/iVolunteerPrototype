package at.jku.csi.marketplace.task.interaction;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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

	@GetMapping("/task/{id}/reserved")
	public List<Participant> findReservedVolunteersByTaskId(@PathVariable("id") String taskId) {
		Task task = taskRepository.findOne(taskId);
		return taskInteractionRepository.findByTask(task).stream()
				.filter(ti -> ti.getParticipant() instanceof Volunteer).filter(ti -> {
					TaskOperation latestOperation = getLatestTaskInteraction(task, ti.getParticipant()).getOperation();
					return latestOperation == TaskVolunteerOperation.RESERVED
							|| latestOperation == TaskVolunteerOperation.UNASSIGNED;
				}).map(ti -> ti.getParticipant()).collect(Collectors.toList());
	}

	@GetMapping("/volunteer/isReserved/{id}")
	public boolean isLoggedInVolunteerAlreadyReserved(@PathVariable("id") String taskId) {
		Task task = taskRepository.findOne(taskId);
		if (loginService.getLoggedInParticipantRole().equals(ParticipantRole.VOLUNTEER)) {
			Participant participant = loginService.getLoggedInParticipant();
			TaskInteraction lastTaskInteraction = getLatestTaskInteraction(task, participant);
			return lastTaskInteraction != null
					&& lastTaskInteraction.getOperation() != TaskVolunteerOperation.UNRESERVED;
		}
		return false;
	}

	@GetMapping("/volunteer/isAssigned/{id}")
	public boolean isLoggedInVolunteerAlreadyAssigned(@PathVariable("id") String taskId) {
		Task task = taskRepository.findOne(taskId);
		if (loginService.getLoggedInParticipantRole().equals(ParticipantRole.VOLUNTEER)) {
			Participant participant = loginService.getLoggedInParticipant();
			TaskInteraction lastTaskInteraction = getLatestTaskInteraction(task, participant);
			return lastTaskInteraction != null && lastTaskInteraction.getOperation() == TaskVolunteerOperation.ASSIGNED;

		}
		return false;

	}

	@GetMapping("/volunteer/{volunteerId}/isAssigned/{taskId}")
	public boolean isVolunteerAlreadyAssigned(@PathVariable("volunteerId") String volunteerId,
			@PathVariable("taskId") String taskId) {
		Task task = taskRepository.findOne(taskId);
		Volunteer volunteer = volunteerRepository.findOne(volunteerId);
		TaskInteraction latestTaskInteraction = getLatestTaskInteraction(task, volunteer);
		return latestTaskInteraction != null && latestTaskInteraction.getOperation() == TaskVolunteerOperation.ASSIGNED;
	}

	@PostMapping("/volunteer/reserve")
	public void reserveForTask(@RequestBody String taskId) {
		Task task = taskRepository.findOne(taskId);
		if (loginService.getLoggedInParticipantRole().equals(ParticipantRole.VOLUNTEER)) {
			Participant participant = loginService.getLoggedInParticipant();
			TaskInteraction latestTaskInteraction = getLatestTaskInteraction(task, participant);

			if (latestTaskInteraction == null
					|| latestTaskInteraction.getOperation() == TaskVolunteerOperation.UNRESERVED) {
				createTaskInteraction(task, participant, TaskVolunteerOperation.RESERVED);
			}
		}
	}

	@PostMapping("/volunteer/unreserve")
	public void unreserveForTask(@RequestBody String id) {
		Task task = taskRepository.findOne(id);
		if (loginService.getLoggedInParticipantRole().equals(ParticipantRole.VOLUNTEER)) {
			Participant participant = loginService.getLoggedInParticipant();
			TaskInteraction lastedTaskInteraction = getLatestTaskInteraction(task, participant);
			if (lastedTaskInteraction == null 
					|| lastedTaskInteraction.getOperation() == TaskVolunteerOperation.RESERVED
					|| lastedTaskInteraction.getOperation() == TaskVolunteerOperation.UNASSIGNED) {
				createTaskInteraction(task, participant, TaskVolunteerOperation.UNRESERVED);
			}
		}
	}

	@GetMapping("/task/{id}/assigned")
	public List<Participant> findAssignedVolunteersByTaskId(@PathVariable("id") String taskId) {
		return taskInteractionRepository.findByTask(taskRepository.findOne(taskId)).stream()
				.filter(ti -> ti.getOperation() == TaskVolunteerOperation.ASSIGNED)
				.filter(ti -> getLatestTaskInteraction(ti.getTask(), ti.getParticipant())
						.getOperation() == TaskVolunteerOperation.ASSIGNED)
				.map(ti -> ti.getParticipant()).collect(Collectors.toList());
	}

	@PostMapping("/task/{taskId}/assign/{volunteerId}")
	public void assignForTask(@PathVariable("taskId") String taskId, @PathVariable("volunteerId") String volunteerId) {
		Volunteer volunteer = volunteerRepository.findOne(volunteerId);
		createTaskInteraction(taskRepository.findOne(taskId), volunteer, TaskVolunteerOperation.ASSIGNED);
	}

	@PostMapping("/task/{taskId}/unassign/{volunteerId}")
	public void unassignForTask(@PathVariable("taskId") String taskId,
			@PathVariable("volunteerId") String volunteerId) {
		Volunteer volunteer = volunteerRepository.findOne(volunteerId);
		createTaskInteraction(taskRepository.findOne(taskId), volunteer, TaskVolunteerOperation.UNASSIGNED);
	}

	private TaskInteraction getLatestTaskInteraction(Task task, Participant participant) {
		Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
		List<TaskInteraction> taskInteractions = taskInteractionRepository.findSortedByTaskAndParticipant(task,
				participant, sort);
		return taskInteractions.isEmpty() ? null : taskInteractions.get(0);
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
