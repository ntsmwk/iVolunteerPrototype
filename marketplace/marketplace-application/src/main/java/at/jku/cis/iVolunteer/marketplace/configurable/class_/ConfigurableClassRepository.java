package at.jku.cis.iVolunteer.marketplace.configurable.class_;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.configurable.class_.ConfigurableClass;

@Repository
public interface ConfigurableClassRepository extends MongoRepository<ConfigurableClass, String> {

}
