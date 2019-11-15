package at.jku.cis.iVolunteer.marketplace.meta.core.property;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;

@Repository
public interface PropertyDefinitionRepository extends MongoRepository<PropertyDefinition<Object>, String> {

	List<PropertyDefinition<Object>> findAll();

}
