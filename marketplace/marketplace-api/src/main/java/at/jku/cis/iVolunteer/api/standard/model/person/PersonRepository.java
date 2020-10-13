package at.jku.cis.iVolunteer.api.standard.model.person;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {

	@Query("{ID: ?0}")
	Person findByID(String ID);
}
