package at.jku.csi.marketplace.task.interaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.csi.marketplace.task.TaskRepository;

@RestController
public class TaskInteractionController {

	private static final int PAGE_SIZE = 10;

	@Autowired
	TaskInteractionRepository taskInteractionRepository;

	@Autowired
	TaskRepository taskRepository;

	@GetMapping("/taskInteraction")
	public List<TaskInteraction> findAll() {
		return taskInteractionRepository.findAll();
	}

	@GetMapping("/taskTransaction/task/{id}")
	public List<TaskInteraction> findByTask(@PathVariable("id") String id) {
		return taskInteractionRepository.findByTask(taskRepository.findOne(id), new PageRequest(0, PAGE_SIZE));
	}

	@PostMapping("/taskTransaction")
	public TaskInteraction createTaskTransaction(@RequestBody TaskInteraction taskInteraction) {
		return taskInteractionRepository.insert(taskInteraction);
	}

}
