package at.jku.csi.marketplace.participant;

import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.csi.marketplace.task.Task;

@RestController
public class VolunteerController {

	@Autowired
	private VolunteerRepository volunteerRepository;
	
	@PostMapping("/volunteer/{id}/profile")
	public Volunteer updateVolunteer(@PathVariable("id") String id, @RequestBody Volunteer volunteer) {
		if (volunteerRepository.exists(id)) {
			throw new NotAcceptableException();
		}
		return volunteerRepository.save(volunteer);
	}	
	
	//TODO
	
	
	
	
}
