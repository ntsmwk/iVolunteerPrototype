package at.jku.cis.iVolunteer;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.rule.DerivationRuleRepository;
import at.jku.cis.iVolunteer.marketplace.rule.engine.ContainerRuleEntryRepository;

@Service
public class FinalizationService {

	FinalizationService() {

	}

	public void destroy(ClassConfigurationRepository configuratorRepository,
			ClassDefinitionRepository classDefinitionRepository, ClassInstanceRepository classInstanceRepository,
			RelationshipRepository relationshipRepository, FlatPropertyDefinitionRepository propertyDefinitionRepository,
			DerivationRuleRepository derivationRuleRepository, ContainerRuleEntryRepository containerRuleEntryRepository) {
//		 classDefinitionRepository.deleteAll();
//		 relationshipRepository.deleteAll();
//		 classInstanceRepository.deleteAll();
//		 configuratorRepository.deleteAll();
//		 propertyDefinitionRepository.deleteAll();
//		 derivationRuleRepository.deleteAll();

	}
}
