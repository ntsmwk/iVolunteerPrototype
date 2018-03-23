package at.jku.csi.marketplace.participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VolunteerController {

	@Autowired
	private VolunteerRepository volunteerRepository;

	@GetMapping("/volunteer/{id}")
	public Volunteer findById(@PathVariable("id") String id) {
		return volunteerRepository.findOne(id);
	}
}