package at.jku.csi.marketplace.task.interaction;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.jku.csi.marketplace.task.Task;
import at.jku.csi.marketplace.task.TaskRepository;

@RestController
public class TaskInteractionController {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskInteractionRepository taskInteractionRepository;

	@GetMapping("/task/{id}/interaction")
	public List<TaskInteraction> findnByTaskId(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null) {
			return new ArrayList<>();
		}
		return taskInteractionRepository.findByTask(task);
	}
}
