package at.jku.cis.iVolunteer.marketplace.competence;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.competence.Competence;

public interface CompetenceRepository extends MongoRepository<Competence, String> {

	Competence findByName(String name);
}
