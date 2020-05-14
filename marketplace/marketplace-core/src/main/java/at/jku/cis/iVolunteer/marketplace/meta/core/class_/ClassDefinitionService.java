package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.management.relation.Relation;
import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.model.meta.core.relationship.AssociationCardinality;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;
import at.jku.cis.iVolunteer.model.meta.form.EnumEntry;
import at.jku.cis.iVolunteer.model.meta.form.EnumRepresentation;
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
		
	
//			ids.forEach(id -> {
//				classDefinitions.add(classDefinitionRepository.getByIdAndTenantId(id, tenantId));
//			});
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
//				|| cd.getClassArchetype() == ClassArchetype.ACHIEVEMENT_HEAD
//				|| cd.getClassArchetype() == ClassArchetype.COMPETENCE_HEAD
//				|| cd.getClassArchetype() == ClassArchetype.FUNCTION_HEAD
//				|| cd.getClassArchetype() == ClassArchetype.TASK_HEAD;
		// @formatter:on
	}

//	public List<FormConfiguration> getParentsById(List<String> childIds) {
//		System.out.println("getParentyById");
//		List<ClassDefinition> childClassDefinitions = new ArrayList<>();
//
//		classDefinitionRepository.findAll(childIds).forEach(childClassDefinitions::add);
//		
//		List<FormConfiguration> configList = new ArrayList<FormConfiguration>();
//
//		// Pre-Condition: Graph must be acyclic - a child can only have one parent, one
//		// parent can have multiple children
//		// Work our way up the chain until we are at the root
//
//		for (ClassDefinition childClassDefinition : childClassDefinitions) {
//			FormConfiguration formConfig = new FormConfiguration();
//			formConfig.setName(childClassDefinition.getName());
//			formConfig.setId(childClassDefinition.getId());
//			
//			ClassConfiguration classConfiguration = classConfigurationRepository.findOne(childClassDefinition.getConfigurationId());
//			
//			List<ClassDefinition> allClassDefinitions = new ArrayList<>();
//			List<Relationship> allRelationships = new ArrayList<>();
//			
//			classDefinitionRepository.findAll(classConfiguration.getClassDefinitionIds()).forEach(allClassDefinitions::add);
//			relationshipRepository.findAll(classConfiguration.getRelationshipIds()).forEach(allRelationships::add);
//			
////			formConfig.setFormEntry(this.collectionService.getParentClassDefintions(childClassDefinition, new FormEntry(), allClassDefinitions, allRelationships));
//			formConfig.setFormEntry(this.collectionService.aggregateFormEntry(childClassDefinition, new FormEntry(childClassDefinition.getId()), allClassDefinitions, allRelationships, true));
//
//			
//			configList.add(formConfig);
//		}
//
//		return configList;
//	}
	
//	public List<FormConfiguration> getParents(List<ClassDefinition> classDefinitions, List<Relationship> relationships, ClassDefinition rootClassDefinition) {
//		List<FormConfiguration> formConfigurations = new ArrayList<>();
//
//		FormEntry formEntry = collectionService.getParentClassDefintions(rootClassDefinition, new FormEntry(rootClassDefinition.getId()), classDefinitions, relationships);
//		
//		FormConfiguration formConfiguration = new FormConfiguration();
//		formConfiguration.setId(rootClassDefinition.getId());
//		formConfiguration.setName(rootClassDefinition.getName());
//		formConfiguration.setFormEntry(formEntry);
//		formConfigurations.add(formConfiguration);
//		return formConfigurations;
//	}


	public List<FormConfiguration> getClassDefinitionsById(List<String> startIds) {

		List<ClassDefinition> startClassDefintions = new ArrayList<ClassDefinition>();

		this.classDefinitionRepository.findAll(startIds).forEach(startClassDefintions::add);

		List<FormConfiguration> formConfigurations = new ArrayList<>();
		for (ClassDefinition startClassDefinition : startClassDefintions) {

			List<ClassDefinition> classDefinitions = new ArrayList<>();
			List<Relationship> relationships = new ArrayList<>();
			ClassConfiguration classConfiguration = classConfigurationRepository.findOne(startClassDefinition.getConfigurationId());
			
			classDefinitionRepository.findAll(classConfiguration.getClassDefinitionIds()).forEach(classDefinitions::add);
			relationshipRepository.findAll(classConfiguration.getRelationshipIds()).forEach(relationships::add);

			FormEntry formEntry = collectionService.aggregateFormEntry(startClassDefinition, new FormEntry(startClassDefinition.getId()), classDefinitions, relationships, true, false);
			generateFormEntryIds(formEntry, formEntry.getId());

			FormConfiguration formConfiguration = new FormConfiguration();
			formConfiguration.setId(startClassDefinition.getId());
			formConfiguration.setName(startClassDefinition.getName());
			formConfiguration.setFormEntry(formEntry);
			formConfigurations.add(formConfiguration);
		}

		return formConfigurations;
	}

	public List<FormConfiguration> getClassDefinitions(List<ClassDefinition> classDefinitions, List<Relationship> relationships, ClassDefinition startClassDefinition) {
//		ClassDefinition rootClassDefinition = classDefinitions.stream().filter(cd -> cd.isRoot()).findFirst().get();
		List<FormConfiguration> formConfigurations = new ArrayList<>();

		FormEntry formEntry = collectionService.aggregateFormEntry(startClassDefinition, new FormEntry(startClassDefinition.getId()),
				classDefinitions, relationships, true, false);
		
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
		
//		System.out.println(formEntry.getId());
//		for (ClassProperty p : formEntry.getClassProperties()) {
//			System.out.println(formEntry.getId() + "." + p.getName());
//		}
		
		for (FormEntry f : formEntry.getSubEntries()) {
			generateFormEntryIds(f, currentPath + "." + f.getId());
		}
	}
	
	public FormEntry getClassDefinitionChunk(String path, String startClassDefinitionId, String choiceId) {
		
		List<ClassDefinition> classDefinitions = new ArrayList<>();
		List<Relationship> relationships = new ArrayList<>();
		
		ClassDefinition startClassDefinition = classDefinitionRepository.findOne(startClassDefinitionId);
		
		ClassDefinition choiceClassDefinition = classDefinitionRepository.findOne(choiceId);
		
		ClassConfiguration classConfiguration = classConfigurationRepository.findOne(startClassDefinition.getConfigurationId());
		
		classDefinitionRepository.findAll(classConfiguration.getClassDefinitionIds()).forEach(classDefinitions::add);
		relationshipRepository.findAll(classConfiguration.getRelationshipIds()).forEach(relationships::add);
//		
//		System.out.println("START:  " + startClassDefinition.getId() + ": " + startClassDefinition.getName());
//		System.out.println("CHOICE: " + choiceClassDefinition.getId() + ": " + choiceClassDefinition.getName());
		
//		collectionService.aggregateFormEntry(startClassDefinition, new FormEntry(startClassDefinitionId), classDefinitions, relationships, false);
		FormEntry entry =  collectionService.getFormEntryChunk(startClassDefinition, choiceClassDefinition, classDefinitions, relationships);
		
		
		generateFormEntryIds(entry, path);
		
		
		return entry;
	
	}

	// Keep in case of changes of mind :)
//		private EnumRepresentation createEnumRepresentation(ClassDefinition root) {
//			List<EnumEntry> entries = new ArrayList<EnumEntry>();
//			entries = performDFS(root, 0, entries);
//			
//			EnumRepresentation enumRepresentation = new EnumRepresentation();
//			enumRepresentation.setEnumEntries(entries);
//			enumRepresentation.setId(root.getId());
//			
//			return enumRepresentation;
//		}

}
