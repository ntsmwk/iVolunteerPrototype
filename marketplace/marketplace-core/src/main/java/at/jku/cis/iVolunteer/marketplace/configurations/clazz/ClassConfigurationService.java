package at.jku.cis.iVolunteer.marketplace.configurations.clazz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import at.jku.cis.iVolunteer.marketplace.configurations.matching.collector.MatchingEntityMappingConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.CollectionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.configurations.matching.collector.MatchingEntityMappingConfiguration;
import at.jku.cis.iVolunteer.model.matching.MatchingEntityMappings;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;

@Service
public class ClassConfigurationService {
	@Autowired
	private ClassConfigurationRepository classConfigurationRepository;
	@Autowired
	private CollectionService collectionService;
	@Autowired
	private MatchingEntityMappingConfigurationRepository matchingCollectorConfigurationRepository;
	@Autowired
	private ClassDefinitionRepository classDefinitionRepository;
	@Autowired
	private RelationshipRepository relationshipRepository;

	@GetMapping("class-configuration/all/tenant/{tenantId}")
	public List<ClassConfiguration> getClassConfigurationsByTenantId(@PathVariable("tenantId") String tenantId) {
		return classConfigurationRepository.findByTenantId(tenantId);
	}

	@GetMapping("class-configuration/{id}")
	public ClassConfiguration getClassConfigurationById(@PathVariable("id") String id) {
		return classConfigurationRepository.findOne(id);
	}

	public ClassConfiguration saveClassConfiguration(@RequestBody ClassConfiguration updatedClassConfiguration) {

		updatedClassConfiguration.setTimestamp(new Date());

		ClassConfiguration classConfiguration = classConfigurationRepository.save(updatedClassConfiguration);

		List<ClassDefinition> classDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(updatedClassConfiguration.getClassDefinitionIds())
				.forEach(classDefinitions::add);

		if (classDefinitions != null) {
			classDefinitions = updateClassDefinitions(classDefinitions, classConfiguration);
		}

		classDefinitionRepository.save(classDefinitions);

		// Build MatchingCollector
		MatchingEntityMappings mappings = collectionService
				.collectAllClassDefinitionsWithPropertiesAsMatchingEntityMappings(classConfiguration.getId());

		MatchingEntityMappingConfiguration matchingCollectorConfiguration = new MatchingEntityMappingConfiguration();
		matchingCollectorConfiguration.setId(classConfiguration.getId());
		matchingCollectorConfiguration.setClassConfigurationId(classConfiguration.getId());
		matchingCollectorConfiguration.setMappings(mappings);

		matchingCollectorConfigurationRepository.save(matchingCollectorConfiguration);
		return classConfiguration;
	}

	private List<ClassDefinition> updateClassDefinitions(List<ClassDefinition> classDefinitions,
			ClassConfiguration classConfiguration) {

		for (ClassDefinition cd : classDefinitions) {
			cd.setConfigurationId(classConfiguration.getId());
		}

		List<Relationship> relationships = new ArrayList<>();
		relationshipRepository.findAll(classConfiguration.getRelationshipIds()).forEach(relationships::add);

		collectionService.assignLevelsToClassDefinitions(classDefinitions, relationships);

		return classDefinitions;
	}

	public void deleteClassConfiguration(@PathVariable("id") String id) {
		ClassConfiguration classConfiguration = classConfigurationRepository.findOne(id);

		classConfiguration.getClassDefinitionIds().forEach(classDefinitionRepository::delete);
		classConfiguration.getRelationshipIds().forEach(relationshipRepository::delete);

		classConfigurationRepository.delete(id);
	}

	public List<ClassConfiguration> deleteMultipleClassConfigurations(@RequestBody List<String> ids) {
		ids.forEach(this::deleteClassConfiguration);
		return this.classConfigurationRepository.findAll();
	}
	
}
