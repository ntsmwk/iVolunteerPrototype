package at.jku.cis.iVolunteer.marketplace.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.marketplace.task.interaction.TaskInteractionRepository;
import at.jku.cis.iVolunteer.marketplace.task.interaction.TaskInteractionService;
import at.jku.cis.iVolunteer.marketplace.volunteer.profile.VolunteerProfileRepository;
import at.jku.cis.iVolunteer.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassInstance;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.TaskStatus;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.volunteer.profile.TaskEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.VolunteerProfile;
import at.jku.cis.iVolunteer.model.volunteer.profile.VolunteerTaskEntry;

@RestController
public class TaskOperationController {

	@Autowired private ContractorPublishingRestClient contractorRepositoryRestClient;
	@Autowired private TaskRepository taskRepository;
	@Autowired private TaskInteractionService taskInteractionService;
	@Autowired private TaskInteractionRepository taskInteractionRepository;
	@Autowired private TaskInteractionToTaskEntryMapper taskInteractionToTaskEntryMapper;
	@Autowired private TaskInteractionToCompetenceEntryMapper taskInteractionToCompetenceEntryMapper;
	@Autowired private VolunteerProfileRepository volunteerProfileRepository;
	@Autowired private LoginService loginService;

	@Value("${marketplace.identifier}") private String marketplaceId;

	@PostMapping("/task/{id}/publish")
	public void publishTask(@PathVariable("id") String id, @RequestHeader("authorization") String authorization) {
		Task task = taskRepository.findOne(id);
		if (task == null || task.getStatus() != TaskStatus.CREATED) {
			throw new BadRequestException();
		}
		contractorRepositoryRestClient.publishTask(task, authorization);
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
	public TaskInteraction finishTask(@PathVariable("id") String id,
			@RequestHeader("authorization") String authorization) {
		Task task = taskRepository.findOne(id);
		if (task == null || task.getStatus() != TaskStatus.RUNNING) {
			throw new BadRequestException();
		}
		TaskInteraction taskInteraction = updateTaskStatus(task, TaskStatus.FINISHED);

		TaskEntry taskEntry = taskInteractionToTaskEntryMapper.transform(taskInteraction);
		taskEntry.setMarketplaceId(marketplaceId);

		// TODO @MWE create task instances!
		List<CompetenceClassInstance> competenceInstances = taskInteractionToCompetenceEntryMapper
				.transform(taskInteraction);
		for (CompetenceClassInstance ce : competenceInstances) {
			ce.setMarketplaceId(marketplaceId);
		}

		taskInteractionService.findAssignedVolunteersByTask(task).forEach(volunteer -> {
			VolunteerProfile volunteerProfile = volunteerProfileRepository.findByVolunteer(volunteer);
			if (volunteerProfile == null) {
				volunteerProfile = new VolunteerProfile();
				volunteerProfile.setVolunteer(volunteer);
			}
			volunteerProfile.getTaskList().add(taskEntry);
			volunteerProfile.getCompetenceList().addAll(competenceInstances);
			volunteerProfileRepository.save(volunteerProfile);

			try {
				VolunteerTaskEntry vte = createVolunteerTaskEntryFromTaskEntry(taskEntry);
				vte.setVolunteerId(volunteer.getId());

				contractorRepositoryRestClient.publishTaskEntry(vte, authorization);

				competenceInstances.forEach(competenceEntry -> {
					contractorRepositoryRestClient.publishCompetenceEntry(competenceEntry, authorization);
				});

			} catch (RestClientException ex) {
				throw new BadRequestException();
			}

		});

		return taskInteraction;
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

	private VolunteerTaskEntry createVolunteerTaskEntryFromTaskEntry(TaskEntry taskEntry) {
		VolunteerTaskEntry volunteerTaskEntry = new VolunteerTaskEntry();
		volunteerTaskEntry.setId(taskEntry.getId());
		volunteerTaskEntry.setMarketplaceId(taskEntry.getMarketplaceId());
		volunteerTaskEntry.setTaskDescription(taskEntry.getTaskDescription());
		volunteerTaskEntry.setTaskId(taskEntry.getTaskId());
		volunteerTaskEntry.setTaskName(taskEntry.getTaskName());
		volunteerTaskEntry.setTimestamp(taskEntry.getTimestamp());
		return volunteerTaskEntry;
	}

}
