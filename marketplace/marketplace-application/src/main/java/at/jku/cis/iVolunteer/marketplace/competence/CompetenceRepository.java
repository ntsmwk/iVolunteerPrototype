package at.jku.cis.iVolunteer.marketplace.competence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompetenceRepository extends MongoRepository<Competence, String> {

	Competence findByName(String name);
}
