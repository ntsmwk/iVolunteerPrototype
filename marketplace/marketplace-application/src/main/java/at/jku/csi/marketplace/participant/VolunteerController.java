package at.jku.csi.marketplace.participant;

import java.util.List;

import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VolunteerController {

	@Autowired
	private VolunteerRepository volunteerRepository;

	@GetMapping("/volunteer")
	public List<Volunteer> findAll() {
		return volunteerRepository.findAll();
	}

	@GetMapping("/volunteer/{id}")
	public Volunteer findById(@PathVariable("id") String id) {
		return volunteerRepository.findOne(id);
	}

	@PostMapping("/volunteer")
	public Volunteer createVolunteer(@RequestBody Volunteer volunteer) {
		return volunteerRepository.insert(volunteer);
	}

	@PutMapping("/volunteer/{id}")
	public Volunteer updateVolunteer(@PathVariable("id") String id, @RequestBody Volunteer volunteer) {
		if (volunteerRepository.exists(id)) {
			throw new NotAcceptableException();
		}
		return volunteerRepository.save(volunteer);
	}

	@DeleteMapping("/volunteer/{id}")
	public void deleteVolunteer(@PathVariable("id") String id) {
		volunteerRepository.delete(id);
	}
}