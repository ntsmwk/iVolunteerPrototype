package at.jku.cis.iVolunteer.marketplace.user;

import java.util.List;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.user.HelpSeeker;

@RestController
public class HelpSeekerController {

	@Autowired private HelpSeekerRepository helpSeekerRepository;

	@GetMapping("/helpseeker/{id}")
	public HelpSeeker findById(@PathVariable("id") String id) {
		return helpSeekerRepository.findOne(id);
	}
	
	@GetMapping("/helpseeker")
	public List<HelpSeeker> findAll() {
		return helpSeekerRepository.findAll();
	}

	@PostMapping("/helpseeker")
	public HelpSeeker registerHelpSeeker(@RequestBody HelpSeeker helpSeeker) {
		if (helpSeekerRepository.findOne(helpSeeker.getId()) != null) {
			throw new BadRequestException("HelpSeeker already registed");
		}
		return helpSeekerRepository.insert(helpSeeker);
	}
}