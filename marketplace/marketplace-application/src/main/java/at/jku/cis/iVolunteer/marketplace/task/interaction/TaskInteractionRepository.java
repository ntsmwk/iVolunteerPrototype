package at.jku.cis.iVolunteer.marketplace.task.interaction;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.TaskOperation;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.user.User;

@Repository
public interface TaskInteractionRepository extends MongoRepository<TaskInteraction, String> {

	List<TaskInteraction> findByTask(Task task);

	List<TaskInteraction> findByTaskAndOperation(Task task, TaskOperation operation);

	List<TaskInteraction> findByParticipant(User participant);

	List<TaskInteraction> findSortedByTaskAndParticipant(Task task, User participant, Sort sort);
	
}
