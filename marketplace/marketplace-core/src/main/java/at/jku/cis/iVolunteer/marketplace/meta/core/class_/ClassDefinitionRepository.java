package at.jku.cis.iVolunteer.marketplace.meta.core.class_;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

@Repository
public interface ClassDefinitionRepository extends MongoRepository<ClassDefinition, String> {
	
	List<ClassDefinition> findByClassArchetype(ClassArchetype classArchetype);
	
	ClassDefinition findByName(String name);
}
