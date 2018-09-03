package at.jku.cis.iVolunteer.marketplace.project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.project.ProjectMapper;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.marketplace.task.TaskRepository;
import at.jku.cis.iVolunteer.marketplace.task.interaction.TaskInteractionRepository;
import at.jku.cis.iVolunteer.model.exception.NotAcceptableException;
import at.jku.cis.iVolunteer.model.participant.Participant;
import at.jku.cis.iVolunteer.model.participant.Volunteer;
import at.jku.cis.iVolunteer.model.project.Project;
import at.jku.cis.iVolunteer.model.project.dto.ProjectDTO;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.TaskStatus;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.task.interaction.TaskVolunteerOperation;

@RestController
public class ProjectController {

	private static final String AVAILABLE = "AVAILABLE";
	private static final String ENGAGED = "ENGAGED";

	@Value("${marketplace.identifier}")
	private String marketplaceId;

	@Autowired
	private LoginService loginService;
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskInteractionRepository taskInteractionRepository;

	@GetMapping("/project")
	public List<ProjectDTO> findAll(@RequestParam(value = "state", required = false) String state) {
		if (StringUtils.equalsIgnoreCase(state, AVAILABLE)) {
			Volunteer volunteer = (Volunteer) loginService.getLoggedInParticipant();
			return projectMapper.toDTOs(findAvailableProjectsByVolunteer(volunteer));
		}
		if (StringUtils.equalsIgnoreCase(state, ENGAGED)) {
			Volunteer volunteer = (Volunteer) loginService.getLoggedInParticipant();
			return projectMapper.toDTOs(findEngagedProjectsByVolunteer(volunteer));
		}
		return projectMapper.toDTOs(projectRepository.findAll());
	}

	private List<Project> findAvailableProjectsByVolunteer(Volunteer volunteer) {
		Set<Project> projects = new HashSet<>();

		taskRepository.findByStatus(TaskStatus.PUBLISHED).forEach(task -> {
			projects.add(task.getProject());
		});

		return new ArrayList<>(projects);
	}

	private List<Project> findEngagedProjectsByVolunteer(Volunteer volunteer) {
		Set<Project> projects = new HashSet<>();

		taskRepository.findByStatus(TaskStatus.RUNNING).forEach(task -> {
			TaskInteraction taskInteraction = getLatestTaskInteraction(task, volunteer);
			if (isAssignedTaskInteraction(taskInteraction)) {
				projects.add(task.getProject());
			}
		});

		return new ArrayList<>(projects);
	}

	private boolean isAssignedTaskInteraction(TaskInteraction taskInteraction) {
		return taskInteraction != null && TaskVolunteerOperation.ASSIGNED == taskInteraction.getOperation();
	}

	private TaskInteraction getLatestTaskInteraction(Task task, Participant participant) {
		List<TaskInteraction> taskInteractions = taskInteractionRepository.findSortedByTaskAndParticipant(task,
				participant, new Sort(Sort.Direction.DESC, "timestamp"));
		return taskInteractions.isEmpty() ? null : taskInteractions.get(0);
	}

	@GetMapping("/project/{id}")
	public ProjectDTO findById(@PathVariable("id") String projectId) {
		return projectMapper.toDTO(projectRepository.findOne(projectId));
	}

	@PostMapping("/project")
	public ProjectDTO createProject(@RequestBody ProjectDTO proectDto) {
		Project project = projectMapper.toEntity(proectDto);
		project.setMarketplaceId(marketplaceId);
		return projectMapper.toDTO(projectRepository.insert(project));
	}

	@PutMapping("/project/{id}")
	public ProjectDTO updateProject(@PathVariable("id") String projectId, @RequestBody ProjectDTO projectDto) {
		Project orginalProject = projectRepository.findOne(projectId);
		if (orginalProject == null) {
			throw new NotAcceptableException();
		}
		orginalProject.setStartDate(projectDto.getStartDate());
		orginalProject.setEndDate(projectDto.getEndDate());
		return projectMapper.toDTO(projectRepository.save(orginalProject));
	}
}
