package at.jku.csi.marketplace.task.transaction;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import at.jku.csi.marketplace.task.Task;

public interface TaskTransactionRepository extends MongoRepository<TaskTransaction, String> {

	@Query(value = "{ 'task' : ?0 }")
	List<TaskTransaction> findByTask(Task task, Pageable pageable);

}
