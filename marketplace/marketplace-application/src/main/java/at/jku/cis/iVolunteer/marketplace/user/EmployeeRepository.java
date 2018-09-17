package at.jku.cis.iVolunteer.marketplace.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.user.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

	Employee findByUsername(String username);
}
