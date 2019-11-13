package at.jku.cis.iVolunteer.marketplace.user;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.user.RecruiterMapper;
import at.jku.cis.iVolunteer.model.user.dto.RecruiterDTO;

@RestController
public class RecruiterController {

	@Autowired private RecruiterMapper recruiterMapper;
	@Autowired private RecruiterRepository recruiterRepository;

	@GetMapping("/recruiter/{id}")
	public RecruiterDTO findById(@PathVariable("id") String id) {
		return recruiterMapper.toDTO(recruiterRepository.findOne(id));
	}

	@PostMapping("/recruiter")
	public RecruiterDTO registerHelpSeeker(@RequestBody RecruiterDTO recruiterDto) {
		if (recruiterRepository.findOne(recruiterDto.getId()) != null) {
			throw new BadRequestException("HelpSeeker already registed");
		}
		return recruiterMapper.toDTO(recruiterRepository.insert(recruiterMapper.toEntity(recruiterDto)));
	}

}
