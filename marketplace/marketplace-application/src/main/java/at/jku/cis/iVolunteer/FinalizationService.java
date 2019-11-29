package at.jku.cis.iVolunteer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.meta.configurator.ConfiguratorRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;

@Service
public class FinalizationService {

	@Autowired private ConfiguratorRepository configuratorRepository;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionsRepository;

	
	public void finalize() {
		classDefinitionRepository.deleteAll();
		relationshipRepository.deleteAll();
		classInstanceRepository.deleteAll();
		configuratorRepository.deleteAll();
		propertyDefinitionsRepository.deleteAll();

	}
	
}
