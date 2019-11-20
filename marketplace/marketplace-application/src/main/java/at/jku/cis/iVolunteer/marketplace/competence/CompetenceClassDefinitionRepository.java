package at.jku.cis.iVolunteer.marketplace.competence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassDefinition;

@Repository
public interface CompetenceClassDefinitionRepository extends MongoRepository<CompetenceClassDefinition, String> {

}
