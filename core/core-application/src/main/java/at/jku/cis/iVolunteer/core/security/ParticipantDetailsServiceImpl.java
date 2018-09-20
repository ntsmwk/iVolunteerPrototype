package at.jku.cis.iVolunteer.core.security;

import static at.jku.cis.iVolunteer.core.security.ParticipantRole.EMPLOYEE;
import static at.jku.cis.iVolunteer.core.security.ParticipantRole.VOLUNTEER;
import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.employee.CoreEmployeeRepository;
import at.jku.cis.iVolunteer.core.service.ParticipantDetailsService;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.user.CoreEmployee;
import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;

@Service
public class ParticipantDetailsServiceImpl implements ParticipantDetailsService {

	@Autowired
	private CoreEmployeeRepository coreEmployeeRepository;

	@Autowired
	private CoreVolunteerRepository coreVolunteerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CoreEmployee employee = coreEmployeeRepository.findByUsername(username);
		if (employee != null) {
			return new User(employee.getUsername(), employee.getPassword(), asList(EMPLOYEE));
		}

		CoreVolunteer volunteer = coreVolunteerRepository.findByUsername(username);
		if (volunteer != null) {
			return new User(volunteer.getUsername(), volunteer.getPassword(), asList(VOLUNTEER));
		}

		throw new UsernameNotFoundException(username);
	}
}
