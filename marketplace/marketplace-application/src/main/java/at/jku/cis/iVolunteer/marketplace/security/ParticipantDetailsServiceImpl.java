package at.jku.cis.iVolunteer.marketplace.security;

import static at.jku.cis.iVolunteer.marketplace.security.ParticipantRole.EMPLOYEE;
import static at.jku.cis.iVolunteer.marketplace.security.ParticipantRole.VOLUNTEER;
import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.participant.EmployeeRepository;
import at.jku.cis.iVolunteer.marketplace.participant.VolunteerRepository;
import at.jku.cis.iVolunteer.model.participant.Employee;
import at.jku.cis.iVolunteer.model.participant.Volunteer;
import at.jku.cis.marketplace.security.service.ParticipantDetailsService;

@Service
public class ParticipantDetailsServiceImpl implements ParticipantDetailsService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private VolunteerRepository volunteerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByUsername(username);
		if (employee != null) {
			return new User(employee.getUsername(), employee.getPassword(), asList(EMPLOYEE));
		}

		Volunteer volunteer = volunteerRepository.findByUsername(username);
		if (volunteer != null) {
			return new User(volunteer.getUsername(), volunteer.getPassword(), asList(VOLUNTEER));
		}

		throw new UsernameNotFoundException(username);
	}
}
