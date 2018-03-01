package at.jku.csi.marketplace.task;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
	
}
