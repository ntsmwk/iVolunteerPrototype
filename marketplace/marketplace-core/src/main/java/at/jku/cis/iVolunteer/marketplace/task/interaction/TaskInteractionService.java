package at.jku.cis.iVolunteer.marketplace.task.interaction;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.task.interaction.TaskVolunteerOperation;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Service
public class TaskInteractionService {

	@Autowired
	private TaskInteractionRepository taskInteractionRepository;

	public Set<Volunteer> findReservedVolunteersByTask(Task task) {
		Set<Volunteer> volunteers = new HashSet<>();

		findByTaskGroupedByParticipant(task).entrySet().forEach(entry -> {
			List<TaskInteraction> interactions = entry.getValue();
			if (TaskVolunteerOperation.RESERVED == interactions.get(interactions.size() - 1).getOperation()
					|| TaskVolunteerOperation.UNASSIGNED == interactions.get(interactions.size() - 1).getOperation()) {
				volunteers.add((Volunteer) entry.getKey());
			}
		});

		return volunteers;
	}

	public Set<Volunteer> findAssignedVolunteersByTask(Task task) {
		Set<Volunteer> volunteers = new HashSet<>();

		findByTaskGroupedByParticipant(task).entrySet().forEach(entry -> {
			List<TaskInteraction> interactions = entry.getValue();
			if (TaskVolunteerOperation.ASSIGNED == interactions.get(interactions.size() - 1).getOperation()) {
				volunteers.add((Volunteer) entry.getKey());
			}
		});

		return volunteers;
	}

	private Map<User, List<TaskInteraction>> findByTaskGroupedByParticipant(Task task) {
		List<TaskInteraction> taskInteractions = taskInteractionRepository.findByTask(task);
		return taskInteractions.stream().filter(ti -> ti.getParticipant() != null).collect(Collectors.groupingBy(t -> t.getParticipant()));
	}

}
