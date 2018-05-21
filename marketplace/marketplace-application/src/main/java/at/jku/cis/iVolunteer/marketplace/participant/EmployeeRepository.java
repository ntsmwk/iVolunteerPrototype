package at.jku.cis.iVolunteer.marketplace.participant;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

	Employee findByUsername(String username);
}
