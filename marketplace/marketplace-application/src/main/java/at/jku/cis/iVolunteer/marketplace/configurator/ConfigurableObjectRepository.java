package at.jku.cis.iVolunteer.marketplace.configurator;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.configurable.ConfigurableObject;

public interface ConfigurableObjectRepository extends MongoRepository<ConfigurableObject, String> {

}
