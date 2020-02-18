package at.jku.cis.iVolunteer.marketplace.competence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

@RestController
public class CompetenceClassDefinitionController {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;

	@GetMapping("/competence/{tenantId}")
	public List<ClassDefinition> findAll(@PathVariable("tenantId") String tenantId) {
		List<ClassDefinition> findAll = classDefinitionRepository.findByClassArchetype(ClassArchetype.COMPETENCE, tenantId);
		return findAll;
	}
}
