package at.jku.cis.iVolunteer.marketplace.user;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.user.HelpSeekerMapper;
import at.jku.cis.iVolunteer.model.user.dto.HelpSeekerDTO;

@RestController
public class HelpSeekerController {

	@Autowired private HelpSeekerMapper helpSeekerMapper;
	@Autowired private HelpSeekerRepository helpSeekerRepository;

	@GetMapping("/helpseeker/{id}")
	public HelpSeekerDTO findById(@PathVariable("id") String id) {
		return helpSeekerMapper.toDTO(helpSeekerRepository.findOne(id));
	}

	@PostMapping("/helpseeker")
	public HelpSeekerDTO registerHelpSeeker(@RequestBody HelpSeekerDTO helpSeekerDto) {
		if (helpSeekerRepository.findOne(helpSeekerDto.getId()) == null) {
			throw new BadRequestException("HelpSeeker already registed");
		}
		return helpSeekerMapper.toDTO(helpSeekerRepository.insert(helpSeekerMapper.toEntity(helpSeekerDto)));
	}

}