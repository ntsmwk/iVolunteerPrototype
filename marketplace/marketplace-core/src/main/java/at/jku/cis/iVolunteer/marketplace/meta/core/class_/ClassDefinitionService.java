package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
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

	public ClassDefinition getClassDefinitionById(String id, String tenantId) {
		return classDefinitionRepository.getByIdAndTenantId(id, tenantId);
	}

	public List<ClassDefinition> getClassDefinitonsById(List<String> ids, String tenantId) {
		List<ClassDefinition> classDefinitions = new ArrayList<>();
		
		classDefinitionRepository.findAll(ids).forEach(classDefinitions::add);

		return classDefinitions;
	}

	public List<ClassDefinition> getAllClassDefinitionsWithProperties(String slotId) {
		ClassConfiguration classConfiguration = classConfigurationRepository.findOne(slotId);

		if (classConfiguration == null) {
			return null;
		}

		List<ClassDefinition> classDefinitions = new ArrayList<ClassDefinition>();
		classDefinitionRepository.findAll(classConfiguration.getClassDefinitionIds()).forEach(c -> {
			if (c.getProperties() != null && c.getProperties().size() > 0) {
				classDefinitions.add(c);
			}
		});

		return classDefinitions;
	}

	public ClassDefinition newClassDefinition(ClassDefinition classDefinitionDTO) {
		return classDefinitionRepository.save(classDefinitionDTO);
	}

	public ClassDefinition changeClassDefinitionName(String id, String newName) {
		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		clazz.setName(newName);
		return (classDefinitionRepository.save(clazz));
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

	public List<ClassDefinition> getClassDefinitionsByArchetype(ClassArchetype archetype, String tenantId) {
		List<ClassDefinition> classDefinitions = classDefinitionRepository.getByClassArchetypeAndTenantId(archetype,
				tenantId);
		return classDefinitions;
	}

	public List<ClassDefinition> getAllClassDefinitionsWithoutEnums(String tenantId) {
		List<ClassDefinition> classDefinitions = classDefinitionRepository.getByTenantId(tenantId).stream()
				.filter(cd -> filterEnumClasses(cd)).collect(Collectors.toList());
		return classDefinitions;
	}

	public List<ClassDefinition> getAllClassDefinitionsWithoutEnumsAndHeads(String tenantId) {
		List<ClassDefinition> classDefinitions = classDefinitionRepository.getByTenantId(tenantId).stream()
				.filter(cd -> filterEnumsAndHeadClasses(cd)).collect(Collectors.toList());
		return classDefinitions;
	}

	private boolean filterEnumsAndHeadClasses(ClassDefinition cd) {
		// @formatter:off
		return cd.getClassArchetype() == ClassArchetype.ACHIEVEMENT
				|| cd.getClassArchetype() == ClassArchetype.COMPETENCE
				|| cd.getClassArchetype() == ClassArchetype.FUNCTION 
				|| cd.getClassArchetype() == ClassArchetype.TASK;
		// @formatter:on
	}

	private boolean filterEnumClasses(ClassDefinition cd) {
		// @formatter:off
		return cd.getClassArchetype() == ClassArchetype.ACHIEVEMENT
				|| cd.getClassArchetype() == ClassArchetype.COMPETENCE
				|| cd.getClassArchetype() == ClassArchetype.FUNCTION 
				|| cd.getClassArchetype() == ClassArchetype.TASK;
		// @formatter:on
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

	public List<FormConfiguration> getClassDefinitions(List<ClassDefinition> classDefinitions,
			List<Relationship> relationships, ClassDefinition startClassDefinition) {
		List<FormConfiguration> formConfigurations = new ArrayList<>();

		FormEntry formEntry = collectionService.aggregateFormEntry(startClassDefinition,
				new FormEntry(startClassDefinition.getId()), classDefinitions, relationships, true);

		generateFormEntryIds(formEntry, formEntry.getId());

		FormConfiguration formConfiguration = new FormConfiguration();
		formConfiguration.setId(startClassDefinition.getId());
		formConfiguration.setName(startClassDefinition.getName());
		formConfiguration.setFormEntry(formEntry);
		formConfigurations.add(formConfiguration);
		
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
