package at.jku.cis.iVolunteer.marketplace.project;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.project.ProjectMapper;
import at.jku.cis.iVolunteer.marketplace.participant.VolunteerRepository;
import at.jku.cis.iVolunteer.marketplace.task.interaction.TaskInteractionRepository;
import at.jku.cis.iVolunteer.model.exception.NotAcceptableException;
import at.jku.cis.iVolunteer.model.participant.Volunteer;
import at.jku.cis.iVolunteer.model.project.Project;
import at.jku.cis.iVolunteer.model.project.dto.ProjectDTO;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;

@RestController
public class ProjectController {

	@Value("${marketplace.identifier}")
	private String marketplaceId;

	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TaskInteractionRepository taskInteractionRepository;
	@Autowired
	private VolunteerRepository volunteerRepository;

	@GetMapping("/project")
	public List<ProjectDTO> findAll(@RequestParam(value = "volunteerId", required = false) String volunterrId) {
		if (StringUtils.isEmpty(volunterrId)) {
			return projectMapper.toDTOs(projectRepository.findAll());
		}
		Volunteer volunteer = volunteerRepository.findOne(volunterrId);
		return projectMapper.toDTOs(findProjectByVolunteer(volunteer));
	}

	private List<Project> findProjectByVolunteer(Volunteer volunteer) {
		List<TaskInteraction> taskInterationsByVolunteer = taskInteractionRepository.findByParticipant(volunteer);
		return taskInterationsByVolunteer.stream().map(taskInteraction -> taskInteraction.getTask().getProject())
				.distinct().collect(Collectors.toList());
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
