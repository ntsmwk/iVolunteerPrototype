package at.jku.cis.iVolunteer.marketplace.participant;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.participant.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

	Employee findByUsername(String username);
}
