package at.jku.csi.marketplace.task.interaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.csi.marketplace.participant.Participant;
import at.jku.csi.marketplace.participant.Volunteer;
import at.jku.csi.marketplace.participant.VolunteerRepository;
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

	@Autowired
	private VolunteerRepository volunteerRepository;

	@GetMapping("/task/{id}/interaction")
	public List<TaskInteraction> findByTaskId(@PathVariable("id") String id) {
		return taskInteractionRepository.findByTask(id);
	}

	@GetMapping("/volunteer/{id}/interaction")
	public List<TaskInteraction> findByVolunteerId(@PathVariable("id") String id) {
		return taskInteractionRepository.findByVolunteer(id);
	}

	@GetMapping("/task/{id}/reserved")
	public ArrayList<Participant> findReservedVolunteersByTaskId(@PathVariable("id") String id) {
		List<TaskInteraction> taskInteractions = taskInteractionRepository.findByTask(id);
		return findReservedVolunteers(taskInteractions);
	}

	private ArrayList<Participant> findReservedVolunteers(List<TaskInteraction> taskInteractions) {
		Set<Participant> volunteers = new HashSet<Participant>();
		for (TaskInteraction taskInteraction : taskInteractions) {
			if (isReserved(taskInteraction)) {
				volunteers.add(taskInteraction.getParticipant());
			} else if (isUnreserved(taskInteraction)) {
				volunteers.remove(taskInteraction.getParticipant());
			}
		}
		return Lists.newArrayList(volunteers);
	}

	private boolean isReserved(TaskInteraction taskInteraction) {
		return taskInteraction.getOperation() == TaskVolunteerOperation.RESERVED
				|| taskInteraction.getOperation() == TaskVolunteerOperation.UNASSIGNED;
	}

	private boolean isUnreserved(TaskInteraction taskInteraction) {
		return taskInteraction.getOperation() == TaskVolunteerOperation.UNRESERVED
				|| taskInteraction.getOperation() == TaskVolunteerOperation.ASSIGNED;
	}

	@GetMapping("/volunteer/isReserved/{id}")
	public boolean isLoggedInVolunteerAlreadyReserved(@PathVariable("id") String id) {
		if (loginService.getLoggedInParticipantRole().equals(ParticipantRole.VOLUNTEER)) {
			Participant participant = loginService.getLoggedInParticipant();
			List<TaskInteraction> taskInteractions = taskInteractionRepository
					.findByVolunteerAndTask(participant.getId(), id);
			return alreadyReserved(taskInteractions);
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
		List<TaskInteraction> taskInteractions = taskInteractionRepository.findByVolunteerAndTask(volunteer.getId(),
				taskId);
		return alreadyAssigned(taskInteractions);
	}

	@PostMapping("/volunteer/reserve")
	public void reserveForTask(@RequestBody String id) {
		if (loginService.getLoggedInParticipantRole().equals(ParticipantRole.VOLUNTEER)) {
			Participant participant = loginService.getLoggedInParticipant();
			List<TaskInteraction> taskInteractions = taskInteractionRepository
					.findByVolunteerAndTask(participant.getId(), id);

			if (!alreadyReserved(taskInteractions)) {
				createTaskInteraction(taskRepository.findOne(id), participant, TaskVolunteerOperation.RESERVED);
			}
		}
	}

	@PostMapping("/volunteer/unreserve")
	public void unreserveForTask(@RequestBody String id) {
		if (loginService.getLoggedInParticipantRole().equals(ParticipantRole.VOLUNTEER)) {
			Participant participant = loginService.getLoggedInParticipant();
			List<TaskInteraction> taskVolunteerInteractions = taskInteractionRepository
					.findByVolunteerAndTask(participant.getId(), id);
			if (alreadyReserved(taskVolunteerInteractions) && !alreadyAssigned(taskVolunteerInteractions)) {
				createTaskInteraction(taskRepository.findOne(id), participant, TaskVolunteerOperation.UNRESERVED);
			}
		}
	}

	@GetMapping("/task/{id}/assigned")
	public ArrayList<Participant> findAssignedVolunteersByTaskId(@PathVariable("id") String id) {
		List<TaskInteraction> taskInteractions = taskInteractionRepository.findByTask(id);
		return findAssignedVolunteers(taskInteractions);
	}

	private ArrayList<Participant> findAssignedVolunteers(List<TaskInteraction> taskInteractions) {
		Set<Participant> volunteers = new HashSet<Participant>();
		for (TaskInteraction taskInteraction : taskInteractions) {
			if (taskInteraction.getOperation() == TaskVolunteerOperation.ASSIGNED) {
				volunteers.add(taskInteraction.getParticipant());
			}
			if (taskInteraction.getOperation() == TaskVolunteerOperation.UNASSIGNED) {
				volunteers.remove(taskInteraction.getParticipant());
			}
		}
		return Lists.newArrayList(volunteers);
	}

	@PostMapping("/task/{taskId}/assign/{volunteerId}")
	public void assignForTask(@PathVariable("taskId") String taskId, @PathVariable("volunteerId") String volunteerId) {
		createTaskInteraction(taskRepository.findOne(taskId), volunteerRepository.findOne(volunteerId),
				TaskVolunteerOperation.ASSIGNED);
	}
	
	@PostMapping("/task/{taskId}/unassign/{volunteerId}")
	public void unassignForTask(@PathVariable("taskId") String taskId, @PathVariable("volunteerId") String volunteerId) {
		createTaskInteraction(taskRepository.findOne(taskId), volunteerRepository.findOne(volunteerId),
				TaskVolunteerOperation.UNASSIGNED);
	}

	private boolean alreadyReserved(List<TaskInteraction> taskVolunteerInteractions) {
		boolean isReserved = false;
		for (TaskInteraction taskInteraction : taskVolunteerInteractions) {
			if (isReserved(taskInteraction)) {
				isReserved = true;
			}
			if (isUnreserved(taskInteraction)) {
				isReserved = false;
			}
		}
		return isReserved;
	}

	private boolean alreadyAssigned(List<TaskInteraction> taskVolunteerInteractions) {
		boolean isAssigned = false;
		for (TaskInteraction taskInteraction : taskVolunteerInteractions) {
			if (taskInteraction.getOperation().equals(TaskVolunteerOperation.ASSIGNED)) {
				isAssigned = true;
			}
			if (taskInteraction.getOperation().equals(TaskVolunteerOperation.UNASSIGNED)) {
				isAssigned = false;
			}
		}
		return isAssigned;
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
