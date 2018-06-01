package at.jku.cis.iVolunteer.marketplace.task.interaction;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.marketplace.participant.Participant;
import at.jku.cis.iVolunteer.marketplace.task.Task;

@Repository
public interface TaskInteractionRepository extends MongoRepository<TaskInteraction, String> {

	List<TaskInteraction> findByTask(Task task);

	List<TaskInteraction> findByTaskAndOperation(Task task, TaskOperation operation);

	List<TaskInteraction> findByParticipant(Participant participant);

	List<TaskInteraction> findSortedByTaskAndParticipant(Task task, Participant participant, Sort sort);
	
}
