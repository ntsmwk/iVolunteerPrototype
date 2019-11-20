package at.jku.cis.iVolunteer.marketplace.competence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassDefinition;

@RestController
public class CompetenceClassDefinitionController {

	@Autowired private CompetenceClassDefinitionRepository competenceClassDefinitionRepository;

	@GetMapping("/competence")
	public List<CompetenceClassDefinition> findAll() {
		return competenceClassDefinitionRepository.findAll();
	}
}
