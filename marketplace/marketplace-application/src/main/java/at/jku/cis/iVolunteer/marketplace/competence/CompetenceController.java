package at.jku.cis.iVolunteer.marketplace.competence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.competence.Competence;

@RestController
public class CompetenceController {

	@Autowired private CompetenceRepository competenceRepository;

	@GetMapping("/competence")
	public List<Competence> findAll() {
		return competenceRepository.findAll();
	}
}
