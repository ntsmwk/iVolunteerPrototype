package at.jku.csi.marketplace.task.interaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.jku.csi.marketplace.participant.ParticipantProfile;
import at.jku.csi.marketplace.participant.Volunteer;
import at.jku.csi.marketplace.participant.VolunteerRepository;
import at.jku.csi.marketplace.task.Task;
import at.jku.csi.marketplace.task.TaskRepository;

@RestController
public class TaskInteractionController {

	@Autowired
	private TaskInteractionRepository taskInteractionRepository;
	@Autowired
	private VolunteerRepository volunteerRepository;
	@Autowired
	private TaskRepository taskRepository;


	@GetMapping("/task/{id}/interaction")
	public List<TaskInteraction> findnByTaskId(@PathVariable("id") String id) {
		return taskInteractionRepository.findByTask(id);
	}
	
	@GetMapping("/volunteer/{id}/interaction")
	public List<TaskInteraction> findnByVolunteerId(@PathVariable("id") String id) {	
		return taskInteractionRepository.findByVolunteer(id);
	}
}
