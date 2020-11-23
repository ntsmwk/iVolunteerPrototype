package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
//import at.jku.cis.iVolunteer.model.meta.form.EnumEntry;
//import at.jku.cis.iVolunteer.model.meta.form.EnumRepresentation;
import at.jku.cis.iVolunteer.model.meta.form.FormConfiguration;
import at.jku.cis.iVolunteer.model.meta.form.FormEntry;

@Service
public class ClassDefinitionService {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private ClassConfigurationRepository classConfigurationRepository;
	@Autowired private CollectionService collectionService;

	public ClassDefinition getByName(String name, String tenantId) {
		return classDefinitionRepository.findByNameAndTenantId(name, tenantId);
	}

	public ClassDefinition getClassDefinitionById(String id) {
		return classDefinitionRepository.findOne(id);
	}

	public List<ClassDefinition> getClassDefinitonsById(List<String> ids) {
		List<ClassDefinition> classDefinitions = new ArrayList<>();
		
		classDefinitionRepository.findAll(ids).forEach(classDefinitions::add);

		return classDefinitions;
	}

	public ClassDefinition newClassDefinition(ClassDefinition classDefinitionDTO) {
		return classDefinitionRepository.save(classDefinitionDTO);
	}

	public List<ClassDefinition> deleteClassDefinition(List<String> idsToRemove) {
		for (String id : idsToRemove) {
			classDefinitionRepository.delete(id);
		}
		return classDefinitionRepository.findAll();
	}

	public List<ClassDefinition> addOrUpdateClassDefinitions(List<ClassDefinition> classDefinitions) {
		return classDefinitionRepository.save(classDefinitions);
	}

	public List<FormConfiguration> getClassDefinitionsById(List<String> startIds) {

		List<ClassDefinition> startClassDefintions = new ArrayList<ClassDefinition>();

		this.classDefinitionRepository.findAll(startIds).forEach(startClassDefintions::add);

		List<FormConfiguration> formConfigurations = new ArrayList<>();
		for (ClassDefinition startClassDefinition : startClassDefintions) {
			List<ClassDefinition> classDefinitions = new ArrayList<>();
			List<Relationship> relationships = new ArrayList<>();
			ClassConfiguration classConfiguration = classConfigurationRepository
					.findOne(startClassDefinition.getConfigurationId());

			classDefinitionRepository.findAll(classConfiguration.getClassDefinitionIds())
					.forEach(classDefinitions::add);
			relationshipRepository.findAll(classConfiguration.getRelationshipIds()).forEach(relationships::add);

			FormEntry formEntry = collectionService.aggregateFormEntry(startClassDefinition,
					new FormEntry(startClassDefinition.getId()), classDefinitions, relationships, true);
			generateFormEntryIds(formEntry, formEntry.getId());

			FormConfiguration formConfiguration = new FormConfiguration();
			formConfiguration.setId(startClassDefinition.getId());
			formConfiguration.setName(startClassDefinition.getName());
			formConfiguration.setFormEntry(formEntry);
			formConfigurations.add(formConfiguration);
		}

		return formConfigurations;
	}


	public void generateFormEntryIds(FormEntry formEntry, String currentPath) {
		formEntry.setId(currentPath);

		for (FormEntry f : formEntry.getSubEntries()) {
			generateFormEntryIds(f, currentPath + "." + f.getId());
		}
	}

	public FormEntry getClassDefinitionChunk(String path, String startClassDefinitionId, String choiceId) {

		List<ClassDefinition> classDefinitions = new ArrayList<>();
		List<Relationship> relationships = new ArrayList<>();

		ClassDefinition startClassDefinition = classDefinitionRepository.findOne(startClassDefinitionId);

		ClassDefinition choiceClassDefinition = classDefinitionRepository.findOne(choiceId);

		ClassConfiguration classConfiguration = classConfigurationRepository
				.findOne(startClassDefinition.getConfigurationId());

		classDefinitionRepository.findAll(classConfiguration.getClassDefinitionIds()).forEach(classDefinitions::add);
		relationshipRepository.findAll(classConfiguration.getRelationshipIds()).forEach(relationships::add);
		FormEntry entry = collectionService.getFormEntryChunk(startClassDefinition, choiceClassDefinition,
				classDefinitions, relationships);

		generateFormEntryIds(entry, path);

		return entry;

	}

}
