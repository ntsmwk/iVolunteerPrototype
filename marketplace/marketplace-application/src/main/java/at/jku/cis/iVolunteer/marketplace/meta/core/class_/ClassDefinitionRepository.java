package at.jku.cis.iVolunteer.marketplace.meta.core.class_;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.meta.core.class_.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;

@Repository
public interface ClassDefinitionRepository extends MongoRepository<ClassDefinition, String> {
	
	List<ClassDefinition> getByClassArchetype(ClassArchetype classArchetype);
}
