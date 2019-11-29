package at.jku.cis.iVolunteer.marketplace.task.template;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.task.template.TaskTemplate;

@Repository
public interface TaskTemplateRepository extends MongoRepository<TaskTemplate, String> {

}
