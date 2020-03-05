package at.jku.cis.iVolunteer;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ConfiguratorRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.rule.DerivationRuleRepository;

@Service
public class FinalizationService {

	FinalizationService() {

	}

	public void destroy(ConfiguratorRepository configuratorRepository,
			ClassDefinitionRepository classDefinitionRepository, ClassInstanceRepository classInstanceRepository,
			RelationshipRepository relationshipRepository, PropertyDefinitionRepository propertyDefinitionRepository,
			DerivationRuleRepository derivationRuleRepository) {
//		 classDefinitionRepository.deleteAll();
//		 relationshipRepository.deleteAll();
//		 classInstanceRepository.deleteAll();
//		 configuratorRepository.deleteAll();
//		 propertyDefinitionRepository.deleteAll();
//		 derivationRuleRepository.deleteAll();

	}
}
