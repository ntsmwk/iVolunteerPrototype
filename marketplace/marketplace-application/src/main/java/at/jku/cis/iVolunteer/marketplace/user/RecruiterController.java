package at.jku.cis.iVolunteer.marketplace.user;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.user.Recruiter;

@RestController
public class RecruiterController {

	@Autowired private RecruiterRepository recruiterRepository;

	@GetMapping("/recruiter/{id}")
	public Recruiter findById(@PathVariable("id") String id) {
		return recruiterRepository.findOne(id);
	}

	@PostMapping("/recruiter")
	public Recruiter registerHelpSeeker(@RequestBody Recruiter recruiterDto) {
		if (recruiterRepository.findOne(recruiterDto.getId()) != null) {
			throw new BadRequestException("HelpSeeker already registed");
		}
		return recruiterRepository.insert(recruiterDto);
	}

}
