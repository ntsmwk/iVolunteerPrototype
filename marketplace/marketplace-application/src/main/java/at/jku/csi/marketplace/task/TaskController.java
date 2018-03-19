package at.jku.csi.marketplace.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.csi.marketplace.blockchain.BlockchainRestClient;
import at.jku.csi.marketplace.exception.ForbiddenException;
import at.jku.csi.marketplace.exception.NotAcceptableException;
import at.jku.csi.marketplace.security.LoginService;
import at.jku.csi.marketplace.task.interaction.TaskInteraction;
import at.jku.csi.marketplace.task.interaction.TaskInteractionRepository;

@RestController
public class TaskController {

	@Autowired
	private BlockchainRestClient blockchainRestClient;

	@Autowired
	private LoginService loginService;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskInteractionRepository taskInteractionRepository;

	@GetMapping("/task")
	public List<Task> findAll(@RequestParam(name = "status", required = false) TaskStatus status) {
		if (status == null) {
			return taskRepository.findAll();
		}

		return taskRepository.findByStatus(status);
	}

	@GetMapping("/task/{id}")
	public Task findById(@PathVariable("id") String id) {
		return taskRepository.findOne(id);
	}

	@GetMapping("/task/volunteer/{id}")
	public List<Task> findByVolunteer(@PathVariable("id") String id) {

		Set<Task> tasks = new HashSet<Task>();
		List<TaskInteraction> taskInteractions = taskInteractionRepository.findByVolunteer(id);
		for (TaskInteraction ti : taskInteractions) {
			tasks.add(ti.getTask());
		}

		return new ArrayList<>(tasks);
	}

	@GetMapping("/task/{id}/sync")
	public CompletedTask syncTask(@PathVariable("id") String taskId) {
		Task task = taskRepository.findOne(taskId);
		List<TaskInteraction> taskInteractions = taskInteractionRepository.findByTaskAndOperation(task,
				TaskStatus.FINISHED);

		if (taskInteractions.size() != 1) {
			throw new ForbiddenException();
		}
		
		CompletedTask completedTask = CompletedTaskBuilder.build(taskInteractions.get(0));
		return completedTask;
	}

	@PostMapping("/task")
	public Task createTask(@RequestBody Task task) {
		task.setStatus(TaskStatus.CREATED);
		Task createdTask = taskRepository.insert(task);

		insertTaskInteraction(createdTask);
		return createdTask;
	}

	private void insertTaskInteraction(Task task) {
		TaskInteraction taskInteraction = new TaskInteraction();
		taskInteraction.setTask(task);
		taskInteraction.setParticipant(loginService.getLoggedInParticipant());
		taskInteraction.setTimestamp(new Date());
		taskInteraction.setOperation(task.getStatus());
		taskInteractionRepository.insert(taskInteraction);
	}

	@PutMapping("/task/{id}")
	public Task updateTask(@PathVariable("id") String taskId, @RequestBody Task task) {
		Task orginalTask = taskRepository.findOne(taskId);
		if (orginalTask == null) {
			throw new NotAcceptableException();
		}
		orginalTask.setType(task.getType());
		orginalTask.setStartDate(task.getStartDate());
		orginalTask.setEndDate(task.getEndDate());
		return taskRepository.save(orginalTask);
	}

	@DeleteMapping("/task/{id}")
	public void deleteTask(@PathVariable("id") String id) {
		taskRepository.delete(id);
	}
}
