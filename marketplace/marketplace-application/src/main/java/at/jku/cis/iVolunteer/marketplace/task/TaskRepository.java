package at.jku.cis.iVolunteer.marketplace.task;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.task.Task;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

}
