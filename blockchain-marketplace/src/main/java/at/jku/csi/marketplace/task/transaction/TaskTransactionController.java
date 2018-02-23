package at.jku.csi.marketplace.task.transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.csi.marketplace.task.TaskRepository;

@RestController
public class TaskTransactionController {

	private static final int PAGE_SIZE = 10;

	@Autowired
	TaskTransactionRepository taskTransactionRepository;

	@Autowired
	TaskRepository taskRepository;

	@GetMapping("/taskTransaction")
	public List<TaskTransaction> findAll() {
		return taskTransactionRepository.findAll();
	}

	@GetMapping("/taskTransaction/task/{id}")
	public List<TaskTransaction> findByTask(@PathVariable("id") String id) {
		return taskTransactionRepository.findByTask(taskRepository.findOne(id), new PageRequest(0, PAGE_SIZE));
	}

	@PostMapping("/taskTransaction")
	public TaskTransaction createTaskTransaction(@RequestBody TaskTransaction taskTransaction) {
		return taskTransactionRepository.insert(taskTransaction);
	}

	@DeleteMapping("/taskTransaction/{id}")
	public void deleteTaskTransaction(@PathVariable("id") String id) {
		taskTransactionRepository.delete(id);
	}
}
