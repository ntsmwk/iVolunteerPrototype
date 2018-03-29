package at.jku.cis.marketplace.task.type;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTypeRepository extends MongoRepository<TaskType, String> {

}
