package at.jku.csi.marketplace.security;

import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import at.jku.csi.marketplace.participant.Employee;
import at.jku.csi.marketplace.participant.EmployeeRepository;
import at.jku.csi.marketplace.participant.Volunteer;
import at.jku.csi.marketplace.participant.VolunteerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private VolunteerRepository volunteerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByUsername(username);
		if (employee != null) {
			return new User(employee.getUsername(), employee.getPassword(), asList(ParticipantRole.EMPLOYEE));
		}

		Volunteer volunteer = volunteerRepository.findByUsername(username);
		if (volunteer != null) {
			return new User(volunteer.getUsername(), volunteer.getPassword(), asList(ParticipantRole.VOLUNTEER));
		}
		
		throw new UsernameNotFoundException(username);
	}
}
