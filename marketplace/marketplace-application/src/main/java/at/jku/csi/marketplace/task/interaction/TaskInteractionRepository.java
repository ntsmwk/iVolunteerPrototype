package at.jku.csi.marketplace.task.interaction;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TaskInteractionRepository extends MongoRepository<TaskInteraction, String> {

	@Query("{ 'task': {'$ref': 'task', '$id': ?0 } }")
	List<TaskInteraction> findByTask(String taskId);

	@Query("{ 'task': {'$ref': 'task', '$id': ?0 } }")
	List<TaskInteraction> findByTask(String taskId, Pageable pageable);

	@Query("{ 'participant': {'$ref': 'volunteer', '$id': ?0 } }")
	List<TaskInteraction> findByVolunteer(String volunteertId);

	@Query("{ 'participant': {'$ref': 'volunteer', '$id': ?0}, 'task': {'$ref': 'task', '$id': ?1} }")
	List<TaskInteraction> findByVolunteerAndTask(String volunteerId, String taskId);

}
