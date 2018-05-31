package at.jku.cis.iVolunteer.marketplace.participant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

	@Autowired
	private VolunteerRepository volunteerRepository;
	
	@GetMapping("")
	public List<Volunteer> findAll(){
		return volunteerRepository.findAll();
	}

	@GetMapping("/volunteer/{id}")
	public Volunteer findById(@PathVariable("id") String id) {
		return volunteerRepository.findOne(id);
	}
}