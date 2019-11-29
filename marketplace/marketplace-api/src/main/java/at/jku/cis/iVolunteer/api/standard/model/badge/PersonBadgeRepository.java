package at.jku.cis.iVolunteer.api.standard.model.badge;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonBadgeRepository extends MongoRepository<PersonBadge, String> {

	@Query("{personID:?0}")
	List<PersonBadge> findByPersonID(String personID);

}
