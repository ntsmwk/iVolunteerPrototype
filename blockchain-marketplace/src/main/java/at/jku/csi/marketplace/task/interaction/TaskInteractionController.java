package at.jku.csi.marketplace.task.interaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskInteractionController {

	@Autowired
	private TaskInteractionRepository taskInteractionRepository;

	@GetMapping("/task/{id}/interaction")
	public List<TaskInteraction> findnByTaskId(@PathVariable("id") String id) {
		return taskInteractionRepository.findByTask(id);
	}
}
