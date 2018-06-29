package at.jku.cis.iVolunteer.marketplace.competence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.competence.CompetenceMapper;
import at.jku.cis.iVolunteer.model.competence.dto.CompetenceDTO;

@RestController
public class CompetenceController {

	@Autowired
	private CompetenceMapper competenceMapper;

	@Autowired
	private CompetenceRepository competenceRepository;

	@GetMapping("/competence")
	public List<CompetenceDTO> findAll() {
		return competenceMapper.toDTOs(competenceRepository.findAll());
	}
}
