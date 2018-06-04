package at.jku.cis.iVolunteer.marketplace.task;

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

import at.jku.cis.iVolunteer.lib.mapper.task.TaskMapper;
import at.jku.cis.iVolunteer.marketplace.participant.VolunteerRepository;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.marketplace.task.interaction.TaskInteractionRepository;
import at.jku.cis.iVolunteer.model.exception.NotAcceptableException;
import at.jku.cis.iVolunteer.model.participant.Volunteer;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.TaskStatus;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;

@RestController
public class TaskController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskInteractionRepository taskInteractionRepository;
	@Autowired
	private VolunteerRepository volunteerRepository;

	@GetMapping("/task")
	public List<TaskDTO> findAll(@RequestParam(name = "status", required = false) TaskStatus status) {
		if (status == null) {
			return taskMapper.toDTOs(taskRepository.findAll());
		}

		return taskMapper.toDTOs(taskRepository.findByStatus(status));
	}

	@GetMapping("/task/{id}")
	public TaskDTO findById(@PathVariable("id") String id) {
		return taskMapper.toDTO(taskRepository.findOne(id));
	}

	@GetMapping("/task/volunteer/{id}")
	public List<TaskDTO> findByVolunteer(@PathVariable("id") String id) {

		Volunteer volunteer = volunteerRepository.findOne(id);

		Set<Task> tasks = new HashSet<Task>();
		List<TaskInteraction> taskInteractions = taskInteractionRepository.findByParticipant(volunteer);
		for (TaskInteraction ti : taskInteractions) {
			tasks.add(ti.getTask());
		}

		return taskMapper.toDTOs(new ArrayList<>(tasks));
	}

	@PostMapping("/task")
	public TaskDTO createTask(@RequestBody TaskDTO taskDto) {
		Task task = taskMapper.toEntity(taskDto);
		task.setStatus(TaskStatus.CREATED);

		Task createdTask = taskRepository.insert(task);

		insertTaskInteraction(createdTask);
		return taskMapper.toDTO(createdTask);
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
	public TaskDTO updateTask(@PathVariable("id") String taskId, @RequestBody TaskDTO taskDto) {
		Task orginalTask = taskRepository.findOne(taskId);
		if (orginalTask == null) {
			throw new NotAcceptableException();
		}
		orginalTask.setStartDate(taskDto.getStartDate());
		orginalTask.setEndDate(taskDto.getEndDate());
		return taskMapper.toDTO(taskRepository.save(orginalTask));
	}

	@DeleteMapping("/task/{id}")
	public void deleteTask(@PathVariable("id") String id) {
		taskRepository.delete(id);
	}
}
