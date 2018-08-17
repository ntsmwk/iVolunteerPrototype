package at.jku.cis.iVolunteer.core.employee;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.core.participant.CoreEmployee;

public interface CoreEmployeeRepository extends MongoRepository<CoreEmployee, String> {

	CoreEmployee findByUsername(String username);
}
