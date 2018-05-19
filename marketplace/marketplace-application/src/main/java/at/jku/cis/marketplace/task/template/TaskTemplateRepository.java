package at.jku.cis.marketplace.task.template;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTemplateRepository extends MongoRepository<TaskTemplate, String> {

}
