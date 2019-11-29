package at.jku.cis.iVolunteer.api.standard.model.badge;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassDefinitionToInstanceMapper;

@RestController

@RequestMapping("/api/standard/PersonBadges")
public class PersonBadgeController {

	@Autowired private PersonBadgeRepository personBadgeRepository;
	@Autowired private ClassDefinitionToInstanceMapper classDefinition2InstanceMapper;
//	@Autowired private ClassDefinitionRe classDefinitionService;

	@PutMapping
	public void savePersonBadge(List<PersonBadge> badges) {
		// TODO create class definition & instances...
//		classDefinition2InstanceMapper.toTarget(source)
		personBadgeRepository.save(badges);
	}

}
