package at.jku.cis.marketplace.task;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

	List<Task> findByStatus(TaskStatus status);

}
