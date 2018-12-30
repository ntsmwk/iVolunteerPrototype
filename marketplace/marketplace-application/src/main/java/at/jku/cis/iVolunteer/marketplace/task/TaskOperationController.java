package at.jku.cis.iVolunteer.marketplace.task;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import at.jku.cis.iVolunteer.mapper.task.TaskMapper;
import at.jku.cis.iVolunteer.mapper.task.interaction.TaskInteractionMapper;
import at.jku.cis.iVolunteer.mapper.volunteer.profile.CompetenceEntryMapper;
import at.jku.cis.iVolunteer.mapper.volunteer.profile.TaskEntryMapper;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.marketplace.task.interaction.TaskInteractionRepository;
import at.jku.cis.iVolunteer.marketplace.task.interaction.TaskInteractionService;
import at.jku.cis.iVolunteer.marketplace.volunteer.profile.VolunteerProfileRepository;
import at.jku.cis.iVolunteer.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.TaskStatus;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.task.interaction.dto.TaskInteractionDTO;
import at.jku.cis.iVolunteer.model.volunteer.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.TaskEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.VolunteerProfile;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.CompetenceEntryDTO;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.TaskEntryDTO;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.VolunteerCompetenceEntryDTO;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.VolunteerTaskEntryDTO;

@RestController
public class TaskOperationController {

	@Autowired
	private ContractorPublishingRestClient contractorRepositoryRestClient;

	@Autowired
	private CompetenceEntryMapper competenceEntryMapper;

	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskEntryMapper taskEntryMapper;
	@Autowired
	private TaskInteractionMapper taskInteractionMapper;
	@Autowired
	private TaskInteractionService taskInteractionService;
	@Autowired
	private TaskInteractionRepository taskInteractionRepository;
	@Autowired
	private TaskInteractionToTaskEntryMapper taskInteractionToTaskEntryMapper;
	@Autowired
	private TaskInteractionToCompetenceEntryMapper taskInteractionToCompetenceEntryMapper;
	@Autowired
	private VolunteerProfileRepository volunteerProfileRepository;

	@Autowired
	private LoginService loginService;

	@Value("${marketplace.identifier}")
	private String marketplaceId;

	@PostMapping("/task/{id}/publish")
	public void publishTask(@PathVariable("id") String id, @RequestHeader("authorization") String authorization) {
		Task task = taskRepository.findOne(id);
		
		if (task == null || task.getStatus() != TaskStatus.CREATED) {
			throw new BadRequestException();
		}

		TaskInteraction taskInteraction = updateTaskStatus(task, TaskStatus.PUBLISHED);

		TaskDTO dto = taskMapper.toDTO(task);
		dto.setStatusDate(taskInteraction.getTimestamp());

		contractorRepositoryRestClient.publishTask(dto, authorization);
	}

	@PostMapping("/task/{id}/start")
	public void startTask(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null || task.getStatus() != TaskStatus.PUBLISHED) {
			throw new BadRequestException();
		}
		updateTaskStatus(task, TaskStatus.RUNNING);
	}

	@PostMapping("/task/{id}/suspend")
	public void suspendTask(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null || task.getStatus() != TaskStatus.RUNNING) {
			throw new BadRequestException();
		}
		updateTaskStatus(task, TaskStatus.SUSPENDED);
	}

	@PostMapping("/task/{id}/resume")
	public void resumeTask(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null || task.getStatus() != TaskStatus.SUSPENDED) {
			throw new BadRequestException();
		}
		updateTaskStatus(task, TaskStatus.RUNNING);
	}

	@PostMapping("/task/{id}/finish")
	public TaskInteractionDTO finishTask(@PathVariable("id") String id,
			@RequestHeader("authorization") String authorization) {
		Task task = taskRepository.findOne(id);
		if (task == null || task.getStatus() != TaskStatus.RUNNING) {
			throw new BadRequestException();
		}
		TaskInteraction taskInteraction = updateTaskStatus(task, TaskStatus.FINISHED);

		TaskEntry taskEntry = taskInteractionToTaskEntryMapper.transform(taskInteraction);
		taskEntry.setMarketplaceId(marketplaceId);
		taskEntry.setTimestamp(taskInteraction.getTimestamp());

		Set<CompetenceEntry> competenceEntries = taskInteractionToCompetenceEntryMapper.transform(taskInteraction);
		for (CompetenceEntry ce : competenceEntries) {
			ce.setMarketplaceId(marketplaceId);
		}

		taskInteractionService.findAssignedVolunteersByTask(task).forEach(volunteer -> {
			VolunteerProfile volunteerProfile = volunteerProfileRepository.findByVolunteer(volunteer);
			if (volunteerProfile == null) {
				volunteerProfile = new VolunteerProfile();
				volunteerProfile.setVolunteer(volunteer);
			}
			volunteerProfile.getTaskList().add(taskEntry);
			volunteerProfile.getCompetenceList().addAll(competenceEntries);
			volunteerProfileRepository.save(volunteerProfile);

			try {
				VolunteerTaskEntryDTO vte = createVolunteerTaskEntryDTOFromTaskEntryDTO(taskEntryMapper.toDTO(taskEntry));
				vte.setVolunteerId(volunteer.getId());
				vte.setTimestamp(taskInteraction.getTimestamp());

				contractorRepositoryRestClient.publishTaskEntry(vte, authorization);

				competenceEntries.forEach(competenceEntry -> {
					VolunteerCompetenceEntryDTO vce = createVolunteerCompetenceEntryDTOFromCompetenceEntryDTO(
							competenceEntryMapper.toDTO(competenceEntry));
					vce.setVolunteerId(volunteer.getId());
					contractorRepositoryRestClient.publishCompetenceEntry(vce, authorization);
				});

			} catch (RestClientException ex) {
				throw new BadRequestException();
			}

		});

		return taskInteractionMapper.toDTO(taskInteraction);
	}

	@PostMapping("/task/{id}/abort")
	public void abortTask(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null || task.getStatus() == TaskStatus.FINISHED) {
			throw new BadRequestException();
		}
		updateTaskStatus(task, TaskStatus.ABORTED);
	}

	private TaskInteraction updateTaskStatus(Task task, TaskStatus taskStatus) {
		task.setStatus(taskStatus);
		Task updatedTask = taskRepository.save(task);
		return insertTaskInteraction(updatedTask);
	}

	private TaskInteraction insertTaskInteraction(Task task) {
		TaskInteraction taskInteraction = new TaskInteraction();
		taskInteraction.setTask(task);
		taskInteraction.setParticipant(loginService.getLoggedInParticipant());
		taskInteraction.setTimestamp(new Date());
		taskInteraction.setOperation(task.getStatus());
		return taskInteractionRepository.insert(taskInteraction);
	}

	private VolunteerCompetenceEntryDTO createVolunteerCompetenceEntryDTOFromCompetenceEntryDTO(
			CompetenceEntryDTO competenceEntryDto) {
		VolunteerCompetenceEntryDTO volunteerCompetenceEntryDTO = new VolunteerCompetenceEntryDTO();
		volunteerCompetenceEntryDTO.setCompetenceId(competenceEntryDto.getCompetenceId());
		volunteerCompetenceEntryDTO.setCompetenceName(competenceEntryDto.getCompetenceName());
		volunteerCompetenceEntryDTO.setId(competenceEntryDto.getId());
		volunteerCompetenceEntryDTO.setMarketplaceId(competenceEntryDto.getMarketplaceId());
		volunteerCompetenceEntryDTO.setTimestamp(competenceEntryDto.getTimestamp());
		return volunteerCompetenceEntryDTO;
	}

	private VolunteerTaskEntryDTO createVolunteerTaskEntryDTOFromTaskEntryDTO(TaskEntryDTO taskEntryDto) {
		VolunteerTaskEntryDTO volunteerTaskEntryDTO = new VolunteerTaskEntryDTO();
		volunteerTaskEntryDTO.setId(taskEntryDto.getId());
		volunteerTaskEntryDTO.setMarketplaceId(taskEntryDto.getMarketplaceId());
		volunteerTaskEntryDTO.setTaskDescription(taskEntryDto.getTaskDescription());
		volunteerTaskEntryDTO.setTaskId(taskEntryDto.getTaskId());
		volunteerTaskEntryDTO.setTaskName(taskEntryDto.getTaskName());
		volunteerTaskEntryDTO.setTimestamp(taskEntryDto.getTimestamp());
		return volunteerTaskEntryDTO;
	}

}
