package at.jku.csi.marketplace.task.interaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.csi.marketplace.participant.Participant;
import at.jku.csi.marketplace.security.LoginService;
import at.jku.csi.marketplace.security.ParticipantRole;
import at.jku.csi.marketplace.task.Task;
import at.jku.csi.marketplace.task.TaskRepository;
import jersey.repackaged.com.google.common.collect.Lists;

@RestController
public class TaskInteractionController {
	@Autowired
	private TaskInteractionRepository taskInteractionRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private LoginService loginService;

	@GetMapping("/task/{taskId}/interaction")
	public List<TaskInteraction> findByTaskId(@PathVariable("taskId") String taskId,
			@RequestParam(value = "operation", required = false) TaskOperation operation) {
		Task task = taskRepository.findOne(taskId);
		if (operation == null) {
			return taskInteractionRepository.findByTask(task);
		}
		return taskInteractionRepository.findByTaskAndOperation(task, operation);
	}

	@GetMapping("/task/{id}/reserved")
	public ArrayList<Participant> findReservedVolunteersByTaskId(@PathVariable("id") String id) {
	Task task = taskRepository.findOne(id);
		Set<Participant> volunteers = new HashSet<Participant>();
		List<TaskInteraction> taskInteractions = taskInteractionRepository.findByTask(task);
		for (TaskInteraction taskInteraction : taskInteractions) {
			if (taskInteraction.getOperation() == TaskVolunteerOperation.RESERVED) {
				volunteers.add(taskInteraction.getParticipant());
			}
		}
		return Lists.newArrayList(volunteers);
	}

	@GetMapping("/volunteer/{id}/interaction")
	public List<TaskInteraction> findByVolunteerId(@PathVariable("id") String id) {
		return taskInteractionRepository.findByVolunteer(id);
	}

	@GetMapping("/volunteer/isReserved/{id}")
	public boolean isTaskAlreadyReserved(@PathVariable("id") String id) {
		if (loginService.getLoggedInParticipantRole().equals(ParticipantRole.VOLUNTEER)) {
			Participant participant = loginService.getLoggedInParticipant();
			List<TaskInteraction> taskInteractions = taskInteractionRepository
					.findByVolunteerAndTask(participant.getId(), id);
			return alreadyReserved(taskInteractions);
		}
		return false;
	}

	@PostMapping("/volunteer/reserve")
	public void reserveForTask(@RequestBody String id) {
		if (loginService.getLoggedInParticipantRole().equals(ParticipantRole.VOLUNTEER)) {
			Participant participant = loginService.getLoggedInParticipant();
			List<TaskInteraction> taskInteractions = taskInteractionRepository
					.findByVolunteerAndTask(participant.getId(), id);

			if (!alreadyReserved(taskInteractions)) {
				TaskInteraction reservation = new TaskInteraction();
				reservation.setOperation(TaskVolunteerOperation.RESERVED);
				reservation.setParticipant(participant);
				reservation.setTask(taskRepository.findOne(id));
				reservation.setTimestamp(new Date());

				taskInteractionRepository.insert(reservation);
			}
		}
	}

	private boolean alreadyReserved(List<TaskInteraction> taskInteractions) {
		for (TaskInteraction taskInteraction : taskInteractions) {
			if (taskInteraction.getOperation().equals(TaskVolunteerOperation.RESERVED)) {
				return true;
			}
		}
		return false;
	}

}
