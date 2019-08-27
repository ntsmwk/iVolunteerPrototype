package at.jku.cis.iVolunteer.marketplace.configurable.class_;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;

@Repository
public interface ConfigurableClassRepository extends MongoRepository<ClassDefinition, String> {

}
