package at.jku.csi.marketplace.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.csi.marketplace.exception.BadRequestException;
import at.jku.csi.marketplace.exception.NotAcceptableException;
import at.jku.csi.marketplace.task.interaction.TaskInteraction;
import at.jku.csi.marketplace.task.interaction.TaskInteractionRepository;

@RestController
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskInteractionRepository taskInteractionRepository;

	@GetMapping("/task")
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	@GetMapping("/task/{id}")
	public Task findById(@PathVariable("id") String id) {
		return taskRepository.findOne(id);
	}

	@GetMapping("/task/created")
	public List<Task> findCreated() {
		return taskRepository.findCreated();
	}
	@PostMapping("/task")
	public Task createTask(@RequestBody Task task) {
		task.setStatus(TaskStatus.CREATED);
		Task createdTask = taskRepository.insert(task);

		taskInteractionRepository.insert(new TaskInteraction(createdTask, TaskStatus.CREATED, new Date()));
		return createdTask;
	}

	@PutMapping("/task/{id}")
	public Task updateTask(@PathVariable("id") String id, @RequestBody Task task) {
		if (!taskRepository.exists(id)) {
			throw new NotAcceptableException();
		}
		return taskRepository.save(task);
	}

	@PostMapping("/task/{id}/start")
	public void startTask(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null || !isCreatedOrCanceledTask(task)) {
			throw new BadRequestException();
		}
		task.setStatus(TaskStatus.STARTED);
		Task updatedTask = taskRepository.save(task);

		TaskInteraction taskInteraction = new TaskInteraction();
		taskInteraction.setTask(updatedTask);
		taskInteraction.setTimestamp(new Date());
		taskInteraction.setOperation(TaskStatus.STARTED);
		taskInteractionRepository.insert(taskInteraction);
	}

	@PostMapping("/task/{id}/finish")
	public void finishTask(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null || !isStartedTask(task)) {
			throw new BadRequestException();
		}
		task.setStatus(TaskStatus.FINISHED);
		Task updatedTask = taskRepository.save(task);

		TaskInteraction taskInteraction = new TaskInteraction();
		taskInteraction.setTask(updatedTask);
		taskInteraction.setTimestamp(new Date());
		taskInteraction.setOperation(TaskStatus.FINISHED);
		taskInteractionRepository.insert(taskInteraction);
	}

	@PostMapping("/task/{id}/cancel")
	public void cancelTask(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null || !isStartedTask(task)) {
			throw new BadRequestException();
		}
		task.setStatus(TaskStatus.CANCELED);
		Task updatedTask = taskRepository.save(task);

		TaskInteraction taskInteraction = new TaskInteraction();
		taskInteraction.setTask(updatedTask);
		taskInteraction.setTimestamp(new Date());
		taskInteraction.setOperation(TaskStatus.CANCELED);
		taskInteractionRepository.insert(taskInteraction);
	}

	private boolean isStartedTask(Task task) {
		return TaskStatus.STARTED == task.getStatus();
	}

	private boolean isCreatedOrCanceledTask(Task task) {
		return TaskStatus.CREATED == task.getStatus() || TaskStatus.CANCELED == task.getStatus();
	}

	@DeleteMapping("/task/{id}")
	public void deleteTask(@PathVariable("id") String id) {
		taskRepository.delete(id);
	}

}
