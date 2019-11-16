package at.jku.cis.iVolunteer.marketplace.task;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.project.ProjectRepository;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.marketplace.task.interaction.TaskInteractionRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.model.exception.NotAcceptableException;
import at.jku.cis.iVolunteer.model.project.Project;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.TaskStatus;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;

@RestController
public class TaskController {

	@Value("${marketplace.identifier}") private String marketplaceId;

	@Autowired private ProjectRepository projectRepository;
	@Autowired private TaskRepository taskRepository;
	@Autowired private TaskInteractionRepository taskInteractionRepository;
	@Autowired private VolunteerRepository volunteerRepository;
	@Autowired private TaskService taskService;

	@Autowired private LoginService loginService;

	@GetMapping("/task")
	public List<Task> findAll(@RequestParam(value = "projectId", required = false) String projectId,
			@RequestParam(value = "participantId", required = false) String participantId,
			@RequestParam(value = "availableOnly", defaultValue = "false", required = false) boolean availableOnly,
			@RequestParam(value = "engagedOnly", defaultValue = "false", required = false) boolean engagedOnly) {

		if (StringUtils.isEmpty(projectId) && !StringUtils.isEmpty(participantId)) {
			return taskService.findByVolunteer(volunteerRepository.findOne(participantId));
		}
		if (!StringUtils.isEmpty(projectId) && !availableOnly && !engagedOnly) {
			Project project = projectRepository.findOne(projectId);
			return taskRepository.findByProject(project);
		}

		if (!StringUtils.isEmpty(projectId) && availableOnly && !engagedOnly) {
			List<Task> test = taskRepository.findByProjectAndStatus(projectRepository.findOne(projectId),
					TaskStatus.PUBLISHED);
			return taskRepository.findByProjectAndStatus(projectRepository.findOne(projectId), TaskStatus.PUBLISHED);
		}
		if (!StringUtils.isEmpty(projectId) && !StringUtils.isEmpty(participantId) && engagedOnly) {
			// @formatter:off
			return taskService
					.findByVolunteer(volunteerRepository.findOne(participantId))
					.stream()
					.filter(task -> task.getStatus().equals(TaskStatus.PUBLISHED)
							|| task.getStatus().equals(TaskStatus.RUNNING))
					.filter(task -> task.getProject().getId().equals(projectId))
					.collect(Collectors.toList());			 
			// @formatter:on
		}
		if (!StringUtils.isEmpty(projectId)) {
			return taskRepository.findByProject(projectRepository.findOne(projectId));
		}

		return taskRepository.findAll();
	}

	@GetMapping("/task/{id}")
	public Task findById(@PathVariable("id") String id) {
		return taskRepository.findOne(id);
	}

	@GetMapping("/task/finished")
	public List<Task> findAllFinished(@RequestParam(value = "participantId", required = true) String participantId) {
		
		// @formatter:off
		return taskService
				.findByVolunteer(volunteerRepository.findOne(participantId))
				.stream()
				.filter(task -> task.getStatus().equals(TaskStatus.FINISHED))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@PostMapping("/task")
	public Task createTask(@RequestBody Task task) {
		task.setStatus(TaskStatus.CREATED);
		task.setMarketplaceId(marketplaceId);
		task.setProject(projectRepository.findOne(task.getProject().getId()));
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
	public Task updateTask(@PathVariable("id") String taskId, @RequestBody Task taskDto) {
		Task orginalTask = taskRepository.findOne(taskId);
		if (orginalTask == null) {
			throw new NotAcceptableException();
		}
		orginalTask.setStartDate(taskDto.getStartDate());
		orginalTask.setEndDate(taskDto.getEndDate());
		return taskRepository.save(orginalTask);
	}

	@DeleteMapping("/task/{id}")
	public void deleteTask(@PathVariable("id") String id) {
		taskRepository.delete(id);
	}
}
