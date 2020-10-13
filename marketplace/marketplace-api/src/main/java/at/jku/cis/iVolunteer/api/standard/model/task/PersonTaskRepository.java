package at.jku.cis.iVolunteer.api.standard.model.task;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonTaskRepository extends MongoRepository<PersonTask, String> {

	@Query("{personID:?0}")
	List<PersonTask> findByPersonID(String personID);

}
