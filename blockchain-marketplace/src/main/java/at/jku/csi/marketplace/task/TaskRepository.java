package at.jku.csi.marketplace.task;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

	@Query("{'status': 'CREATED'}")
	List<Task> findCreated();
	
	
	
}
