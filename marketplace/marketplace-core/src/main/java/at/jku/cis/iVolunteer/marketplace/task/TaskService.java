package at.jku.cis.iVolunteer.marketplace.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import at.jku.cis.iVolunteer.marketplace.project.ProjectRepository;
import at.jku.cis.iVolunteer.marketplace.task.interaction.TaskInteractionRepository;
import at.jku.cis.iVolunteer.marketplace.user.VolunteerRepository;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.TaskStatus;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.task.interaction.TaskVolunteerOperation;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class TaskService {

	private static final String STATUS_ENGAGED = "ENGAGED";
	private static final String STATUS_AVAILABLE = "AVAILABLE";
	private static final String STATUS_FINISHED = "FINISHED";

	@Autowired private ProjectRepository projectRepository;
	@Autowired private TaskRepository taskRepository;
	@Autowired private VolunteerRepository volunteerRepository;
	@Autowired private TaskInteractionRepository taskInteractionRepository;

	public List<Task> findAll(String projectId, String participantId, String status) {

		if (StringUtils.isEmpty(projectId) && !StringUtils.isEmpty(participantId)) {
			return findByVolunteer(volunteerRepository.findOne(participantId));
		}
		if (!StringUtils.isEmpty(projectId) && StringUtils.isEmpty(status)) {
			return taskRepository.findByProject(projectRepository.findOne(projectId));
		}
		if (!StringUtils.isEmpty(projectId) && !StringUtils.isEmpty(status)) {
			return findAllByStatus(status, projectId, participantId);
		}
		if (!StringUtils.isEmpty(projectId)) {
			return taskRepository.findByProject(projectRepository.findOne(projectId));
		}
		return taskRepository.findAll();
	}

	public List<Task> findByVolunteer(Volunteer volunteer) {
		Set<Task> tasks = new HashSet<Task>();
		for (TaskInteraction ti : taskInteractionRepository.findByParticipant(volunteer)) {
			tasks.add(ti.getTask());
		}
		return new ArrayList<>(tasks);
	}

	private List<Task> findAllByStatus(String status, String projectId, String participantId) {
		switch (status) {
		case STATUS_AVAILABLE:
			return handleAvailable(projectId, participantId);
		case STATUS_ENGAGED:
			// @formatter:off
			return findByVolunteer(volunteerRepository.findOne(participantId))
					.stream()
					.filter(task -> task.getStatus().equals(TaskStatus.PUBLISHED)
							|| task.getStatus().equals(TaskStatus.RUNNING))
					.filter(task -> task.getProject().getId().equals(projectId))
					.collect(Collectors.toList());			 
			// @formatter:on
		case STATUS_FINISHED:
			// @formatter:off
			return findByVolunteer(volunteerRepository.findOne(participantId))
					.stream()
					.filter(task -> TaskStatus.FINISHED == task.getStatus())
					.filter(task -> task.getProject().getId().equals(projectId))
					.collect(Collectors.toList());
					// @formatter:on
		}
		return Collections.emptyList();
	}

	private List<Task> handleAvailable(String projectId, String participantId) {
		Volunteer volunteer = volunteerRepository.findOne(participantId);
		List<Task> tasks = taskRepository.findByProjectAndStatus(projectRepository.findOne(projectId),
				TaskStatus.PUBLISHED);
		removeAlreadyReservedOrAssignedTasks(volunteer, tasks);
		return tasks;
	}

	private void removeAlreadyReservedOrAssignedTasks(Volunteer volunteer, List<Task> tasks) {
		tasks.removeIf(t -> {
			List<TaskInteraction> taskInteractions = taskInteractionRepository.findSortedByTaskAndParticipant(t,
					volunteer, new Sort(Sort.Direction.DESC, "timestamp"));
			return !taskInteractions.isEmpty()
					&& taskInteractions.get(0).getOperation() != TaskVolunteerOperation.UNRESERVED;
		});
	}

}