package at.jku.csi.marketplace.task.interaction;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.csi.marketplace.participant.Participant;
import at.jku.csi.marketplace.security.LoginService;
import at.jku.csi.marketplace.security.ParticipantRole;
import at.jku.csi.marketplace.task.TaskRepository;

@RestController
public class TaskInteractionController {
	@Autowired
	private TaskInteractionRepository taskInteractionRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private LoginService loginService;

	@GetMapping("/task/{id}/interaction")
	public List<TaskInteraction> findByTaskId(@PathVariable("id") String id) {
		return taskInteractionRepository.findByTask(id);
	}

	@GetMapping("/volunteer/{id}/interaction")
	public List<TaskInteraction> findByVolunteerId(@PathVariable("id") String id) {
		return taskInteractionRepository.findByVolunteer(id);
	}

	@PostMapping("/volunteer/reserve/{id}")
	public void reserveForTask(@PathVariable("id") String id) {
		if (loginService.getLoggedInParticipantRole().equals(ParticipantRole.VOLUNTEER)) {
			Participant participant = loginService.getLoggedInParticipant();
			TaskInteraction reservation = new TaskInteraction();
			reservation.setOperation(TaskVolunteerOperation.RESERVED);
			reservation.setParticipant(participant);
			reservation.setTask(taskRepository.findOne(id));
			reservation.setTimestamp(new Date());

			taskInteractionRepository.insert(reservation);
		}
	}

}
