package at.jku.cis.iVolunteer;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.meta.configurator.ConfiguratorRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;

@Service
public class FinalizationService {

	
	
	FinalizationService() {
		
	}

//TODO 
	public void destroy(ConfiguratorRepository configuratorRepository, ClassDefinitionRepository classDefinitionRepository, ClassInstanceRepository classInstanceRepository, RelationshipRepository relationshipRepository, PropertyDefinitionRepository propertyDefinitionRepository) {
		classDefinitionRepository.deleteAll();
		relationshipRepository.deleteAll();
		classInstanceRepository.deleteAll();
		configuratorRepository.deleteAll();
		propertyDefinitionRepository.deleteAll();

	}
	
}
