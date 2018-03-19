package at.jku.csi.marketplace.task.entry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskEntryRepository extends MongoRepository<TaskEntry, String>{

}
