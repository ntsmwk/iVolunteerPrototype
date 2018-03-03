package at.jku.csi.marketplace.competence;

import java.util.List;

import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompetenceController {

	@Autowired
	CompetenceRepository competenceRepository;

	@GetMapping("/competence")
	public List<Competence> findAll() {
		return competenceRepository.findAll();
	}

	@GetMapping("/competence/{id}")
	public Competence findById(@PathVariable("id") String id) {
		return competenceRepository.findOne(id);
	}

	@PostMapping("/competence")
	public Competence createCompetence(@RequestBody Competence competence) {
		return competenceRepository.insert(competence);
	}

	@PutMapping("/competence/{id}")
	public Competence updateCompetence(@PathVariable("id") String id, @RequestBody Competence competence) {
		if (competenceRepository.exists(id)) {
			throw new NotAcceptableException();
		}
		return competenceRepository.save(competence);
	}

	@DeleteMapping("/competence/{id}")
	public void deleteCompetence(@PathVariable("id") String id) {
		competenceRepository.delete(id);
	}

}
