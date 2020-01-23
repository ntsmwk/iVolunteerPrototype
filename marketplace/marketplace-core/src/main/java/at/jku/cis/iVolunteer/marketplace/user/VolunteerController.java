package at.jku.cis.iVolunteer.marketplace.user;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassDefinition;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@RestController
public class VolunteerController {

	@Autowired private VolunteerRepository volunteerRepository;

	@GetMapping("/volunteer")
	public List<Volunteer> findAll() {
		return volunteerRepository.findAll();
	}

	@GetMapping("/volunteer/{id}")
	public Volunteer findById(@PathVariable("id") String id) {
		return volunteerRepository.findOne(id);
	}

	@GetMapping("/volunteer/username/{username}")
	public Volunteer findByUsername(@PathVariable("username") String username) {
		return volunteerRepository.findByUsername(username);
	}

	@GetMapping("/volunteer/{id}/competencies")
	public List<CompetenceClassDefinition> findCompetencies(@PathVariable("id") String id) {

		// TODO implement ...
		return Collections.emptyList();
	}

	@PostMapping("/volunteer")
	public Volunteer registerVolunteer(@RequestBody Volunteer volunteer) {
		if (volunteerRepository.findOne(volunteer.getId()) == null) {
			return volunteerRepository.insert(volunteer);
		}
		return null;
	}

}