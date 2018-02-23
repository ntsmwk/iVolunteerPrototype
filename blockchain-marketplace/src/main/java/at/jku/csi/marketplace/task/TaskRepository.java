package at.jku.csi.marketplace.task;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import at.jku.csi.marketplace.task.transaction.TaskTransaction;
import at.jku.csi.marketplace.task.type.TaskType;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
	
	@Query(value = "{ 'taskStatus' : ?0 }")
	List<Task> findByTaskStatus(TaskStatus taskStatus, Pageable pageable);
	

}
