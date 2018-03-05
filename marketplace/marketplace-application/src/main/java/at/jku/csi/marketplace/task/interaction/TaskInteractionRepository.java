package at.jku.csi.marketplace.task.interaction;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import at.jku.csi.marketplace.task.Task;

public interface TaskInteractionRepository extends MongoRepository<TaskInteraction, String> {

	List<TaskInteraction> findByTask(Task task);

	List<TaskInteraction> findByTaskAndOperation(Task task, TaskOperation operation);

	@Query("{ 'participant': {'$ref': 'volunteer', '$id': ?0 } }")
	List<TaskInteraction> findByVolunteer(String volunteertId);

	@Query("{ 'participant': {'$ref': 'volunteer', '$id': ?0}, 'task': {'$ref': 'task', '$id': ?1} }")
	List<TaskInteraction> findByVolunteerAndTask(String volunteerId, String taskId);
}
