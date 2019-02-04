//TODO: Just for testing - DELETE


package at.jku.cis.iVolunteer.marketplace.task.template;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.task.template.MinimalTaskTemplate;

@Repository
public interface MinimalTaskTemplateRepository extends MongoRepository<MinimalTaskTemplate, String>{

}
