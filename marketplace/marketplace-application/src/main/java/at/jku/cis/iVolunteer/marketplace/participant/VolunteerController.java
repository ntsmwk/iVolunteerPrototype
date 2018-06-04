package at.jku.cis.iVolunteer.marketplace.participant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.lib.mapper.participant.VolunteerMapper;
import at.jku.cis.iVolunteer.model.participant.dto.VolunteerDTO;

@RestController
public class VolunteerController {

	@Autowired
	private VolunteerMapper volunteerMapper;
	@Autowired
	private VolunteerRepository volunteerRepository;

	@GetMapping("/volunteer")
	public List<VolunteerDTO> findAll() {
		return volunteerMapper.toDTOs(volunteerRepository.findAll());
	}

	@GetMapping("/volunteer/{id}")
	public VolunteerDTO findById(@PathVariable("id") String id) {
		return volunteerMapper.toDTO(volunteerRepository.findOne(id));
	}
}