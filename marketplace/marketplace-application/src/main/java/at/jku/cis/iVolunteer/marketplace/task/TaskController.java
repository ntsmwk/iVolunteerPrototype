package at.jku.cis.iVolunteer.marketplace.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.task.TaskMapper;
import at.jku.cis.iVolunteer.marketplace.project.ProjectRepository;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.marketplace.task.interaction.TaskInteractionRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.model.exception.NotAcceptableException;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.TaskStatus;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.task.interaction.TaskVolunteerOperation;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@RestController
public class TaskController {

	private static final String STATUS_ENGAGED = "ENGAGED";
	private static final String STATUS_AVAILABLE = "AVAILABLE";
	private static final String STATUS_FINISHED = "FINISHED";

	@Value("${marketplace.identifier}") private String marketplaceId;

	@Autowired private TaskMapper taskMapper;
	@Autowired private ProjectRepository projectRepository;
	@Autowired private TaskRepository taskRepository;
	@Autowired private TaskInteractionRepository taskInteractionRepository;
	@Autowired private VolunteerRepository volunteerRepository;

	@Autowired private LoginService loginService;

	@GetMapping("/task")
	public List<TaskDTO> findAll(@RequestParam(value = "projectId", required = false) String projectId,
			@RequestParam(value = "participantId", required = false) String participantId,
			@RequestParam(value = "status", required = false) String status) {
		if (StringUtils.isEmpty(projectId) && !StringUtils.isEmpty(participantId)) {
			return taskMapper.toDTOs(findByVolunteer(volunteerRepository.findOne(participantId)));
		}
		if (!StringUtils.isEmpty(projectId) && StringUtils.isEmpty(status)) {
			return taskMapper.toDTOs(taskRepository.findByProject(projectRepository.findOne(projectId)));
		}
		if (!StringUtils.isEmpty(projectId) && !StringUtils.isEmpty(status)) {
			return findAllByStatus(status, projectId, participantId);
		}
		if (!StringUtils.isEmpty(projectId)) {
			return taskMapper.toDTOs(taskRepository.findByProject(projectRepository.findOne(projectId)));
		}
		return taskMapper.toDTOs(taskRepository.findAll());
	}

	private List<TaskDTO> findAllByStatus(String status, String projectId, String participantId) {
		switch (status) {
		case STATUS_AVAILABLE:
			return handleAvailable(projectId, participantId);
		case STATUS_ENGAGED:
			return taskMapper.toDTOs(findByVolunteer(volunteerRepository.findOne(participantId))).stream()
					.filter(task -> task.getStatus().equals(TaskStatus.PUBLISHED) || task.getStatus().equals(TaskStatus.RUNNING))
					.filter(task -> task.getProject().getId().equals(projectId)).collect(Collectors.toList());
		case STATUS_FINISHED:
			return taskMapper.toDTOs(findByVolunteer(volunteerRepository.findOne(participantId)).stream()
					.filter(task -> TaskStatus.FINISHED == task.getStatus())
					.filter(task -> task.getProject().getId().equals(projectId)).collect(Collectors.toList()));
		}
		return Collections.emptyList();
	}

	private List<TaskDTO> handleAvailable(String projectId, String participantId) {
		Volunteer volunteer = volunteerRepository.findOne(participantId);
		List<Task> tasks = taskRepository.findByProjectAndStatus(projectRepository.findOne(projectId), TaskStatus.PUBLISHED);
		removeAlreadyReservedOrAssignedTasks(volunteer, tasks);
		return taskMapper.toDTOs(tasks);
	}

	private void removeAlreadyReservedOrAssignedTasks(Volunteer volunteer, List<Task> tasks) {
		tasks.removeIf(t -> {
			List<TaskInteraction> taskInteractions = taskInteractionRepository.findSortedByTaskAndParticipant(t, volunteer,
					new Sort(Sort.Direction.DESC, "timestamp"));
			return !taskInteractions.isEmpty() && taskInteractions.get(0).getOperation() != TaskVolunteerOperation.UNRESERVED;
		});
	}

	public List<Task> findByVolunteer(Volunteer volunteer) {
		Set<Task> tasks = new HashSet<Task>();
		for (TaskInteraction ti : taskInteractionRepository.findByParticipant(volunteer)) {
			tasks.add(ti.getTask());
		}
		return new ArrayList<>(tasks);
	}

	@GetMapping("/task/{id}")
	public TaskDTO findById(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		
		List<TaskInteraction> t = taskInteractionRepository.findByTaskAndOperation(task, task.getStatus());
		
		TaskDTO dto = taskMapper.toDTO(task);
		dto.setStatusDate(t.iterator().next().getTimestamp());
		
		t = taskInteractionRepository.findByTaskAndOperation(task, TaskStatus.PUBLISHED);

		if (!t.isEmpty()) {
			dto.setPublishedDate(t.iterator().next().getTimestamp());
		}
		
		return dto;
	}
	
	@GetMapping("/task/finished")
	public List<TaskDTO> findAllFinished(@RequestParam(value = "participantId", required = true) String participantId) {
		return taskMapper.toDTOs(findByVolunteer(volunteerRepository.findOne(participantId))).stream()
				.filter(task -> task.getStatus().equals(TaskStatus.FINISHED)).collect(Collectors.toList());
	}

	@PostMapping("/task")
	public TaskDTO createTask(@RequestBody TaskDTO taskDto) {
		Task task = taskMapper.toEntity(taskDto);
		task.setStatus(TaskStatus.CREATED);
		task.setMarketplaceId(marketplaceId);
		task.setProject(projectRepository.findOne(taskDto.getProject().getId()));
		task.setPublishedDate(taskDto.getStatusDate());
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
