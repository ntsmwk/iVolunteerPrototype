package at.jku.cis.iVolunteer.marketplace.configurator;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.configurable.class_.ConfigurableClass;

public interface ConfigurableClassRepository extends MongoRepository<ConfigurableClass, String> {

}
