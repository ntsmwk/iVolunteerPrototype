package at.jku.cis.iVolunteer.marketplace.competence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.competence.Competence;

@RestController
public class CompetenceController {

	@Autowired
	private CompetenceRepository competenceRepository;

	@GetMapping("/competence")
	public List<Competence> findAll() {
		return competenceRepository.findAll();
	}

	@PostMapping("/competence")
	public void addCompetence(@RequestBody Competence competence) {
		competenceRepository.insert(competence);
	}
	
	@DeleteMapping("/competence/{competenceId}")
	public void deleteCompetence(@PathVariable("competenceId") String competenceId) {
		competenceRepository.delete(competenceId);
	}

}
