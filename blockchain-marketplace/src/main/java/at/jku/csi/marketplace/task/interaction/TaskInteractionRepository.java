package at.jku.csi.marketplace.task.interaction;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import at.jku.csi.marketplace.task.Task;

public interface TaskInteractionRepository extends MongoRepository<TaskInteraction, String> {

	@Query(value = "{ 'task' : ?0 }")
	List<TaskInteraction> findByTask(Task task, Pageable pageable);

}
