package at.jku.cis.iVolunteer.marketplace.task;

import java.util.Date;
import java.util.Set;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import at.jku.cis.iVolunteer.mapper.participant.profile.CompetenceEntryMapper;
import at.jku.cis.iVolunteer.mapper.participant.profile.TaskEntryMapper;
import at.jku.cis.iVolunteer.mapper.task.TaskMapper;
import at.jku.cis.iVolunteer.mapper.task.interaction.TaskInteractionMapper;
import at.jku.cis.iVolunteer.marketplace.participant.profile.VolunteerProfileRepository;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.marketplace.task.interaction.TaskInteractionRepository;
import at.jku.cis.iVolunteer.marketplace.task.interaction.TaskInteractionService;
import at.jku.cis.iVolunteer.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.model.participant.Volunteer;
import at.jku.cis.iVolunteer.model.participant.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.model.participant.profile.TaskEntry;
import at.jku.cis.iVolunteer.model.participant.profile.VolunteerProfile;
import at.jku.cis.iVolunteer.model.participant.profile.dto.VolunteerCompetenceEntryDTO;
import at.jku.cis.iVolunteer.model.participant.profile.dto.VolunteerTaskEntryDTO;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.TaskStatus;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.task.interaction.dto.TaskInteractionDTO;

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
	public void publishTask(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null || task.getStatus() != TaskStatus.CREATED) {
			throw new BadRequestException();
		}

		contractorRepositoryRestClient.publishTask(taskMapper.toDTO(task));
		updateTaskStatus(task, TaskStatus.PUBLISHED);
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
	public TaskInteractionDTO finishTask(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null || task.getStatus() != TaskStatus.RUNNING) {
			throw new BadRequestException();
		}
		TaskInteraction taskInteraction = updateTaskStatus(task, TaskStatus.FINISHED);

		TaskEntry taskEntry = taskInteractionToTaskEntryMapper.transform(taskInteraction);
		taskEntry.setMarketplaceId(marketplaceId);

		Set<CompetenceEntry> competenceEntries = taskInteractionToCompetenceEntryMapper.transform(taskInteraction);
		for (CompetenceEntry ce : competenceEntries) {
			ce.setMarketplaceId(marketplaceId);
		}

		taskInteractionService.findAssignedVolunteersByTask(task).forEach(new Consumer<Volunteer>() {
			@Override
			public void accept(Volunteer volunteer) {
				VolunteerProfile volunteerProfile = volunteerProfileRepository.findByVolunteer(volunteer);
				if (volunteerProfile == null) {
					volunteerProfile = new VolunteerProfile();
					volunteerProfile.setVolunteer(volunteer);
				}
				volunteerProfile.getTaskList().add(taskEntry);
				volunteerProfile.getCompetenceList().addAll(competenceEntries);
				volunteerProfileRepository.save(volunteerProfile);

				try {
					VolunteerTaskEntryDTO vte = (VolunteerTaskEntryDTO) taskEntryMapper.toDTO(taskEntry);
					vte.setVolunteerId(volunteer.getId());

					contractorRepositoryRestClient.publishTaskEntry(vte);

					competenceEntries.forEach(competenceEntry -> {
						VolunteerCompetenceEntryDTO vce = (VolunteerCompetenceEntryDTO) competenceEntryMapper
								.toDTO(competenceEntry);
						vce.setVolunteerId(volunteer.getId());
						contractorRepositoryRestClient.publishCompetenceEntry(vce);
					});

				} catch (RestClientException ex) {
					throw new BadRequestException();
				}

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
}
