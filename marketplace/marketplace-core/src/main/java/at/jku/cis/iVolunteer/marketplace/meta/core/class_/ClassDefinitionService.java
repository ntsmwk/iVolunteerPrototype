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

	public List<FormConfiguration> getParentsById(List<String> childIds) {
		List<ClassDefinition> childClassDefinitions = new ArrayList<>();

		classDefinitionRepository.findAll(childIds).forEach(childClassDefinitions::add);
		
		List<FormConfiguration> configList = new ArrayList<FormConfiguration>();

		// Pre-Condition: Graph must be acyclic - a child can only have one parent, one
		// parent can have multiple children
		// Work our way up the chain until we are at the root

		for (ClassDefinition childClassDefinition : childClassDefinitions) {
			FormConfiguration formConfig = new FormConfiguration();
			formConfig.setName(childClassDefinition.getName());
			formConfig.setId(childClassDefinition.getId());

			FormEntry formEntry = new FormEntry();
			formEntry.setClassDefinitions(new ArrayList<ClassDefinition>());
			formEntry.setClassProperties(new LinkedList<ClassProperty<Object>>());
			formEntry.setEnumRepresentations(new ArrayList<EnumRepresentation>());
			formEntry.setSubEntries(new ArrayList<FormEntry>());

			ClassDefinition currentClassDefinition = childClassDefinition;
			while (!currentClassDefinition.isRoot()) {

				formEntry.getClassDefinitions().add(currentClassDefinition);

				formEntry.getClassProperties().addAll(0,
						getPropertiesInClassDefinition(formEntry, currentClassDefinition));

				List<Relationship> inheritanceList = relationshipRepository
						.findByTargetAndRelationshipType(currentClassDefinition.getId(), RelationshipType.INHERITANCE);
				
				if (inheritanceList == null || inheritanceList.size() == 0) {
					throw new NotAcceptableException("getParentById: child is not root and has no parent");
				}

				if (currentClassDefinition.getImagePath() != null && formEntry.getImagePath() == null) {
					formEntry.setImagePath(currentClassDefinition.getImagePath());
				}

				currentClassDefinition = classDefinitionRepository.findOne(inheritanceList.get(0).getSource());
			}

			formEntry.getClassDefinitions().add(currentClassDefinition);

			if (currentClassDefinition.getImagePath() != null && formEntry.getImagePath() == null) {
				formEntry.setImagePath(currentClassDefinition.getImagePath());
			}

			List<ClassProperty<Object>> properties = new LinkedList<>();
			for (ClassProperty<Object> property : currentClassDefinition.getProperties()) {
				if (!formEntry.getClassProperties().contains(property)) {
					properties.add(property);
				}
			}

			formEntry.getClassProperties().addAll(0, properties);
			formConfig.setFormEntry(formEntry);
			configList.add(formConfig);
		}

		return configList;
	}

	private List<ClassProperty<Object>> getPropertiesInClassDefinition(FormEntry formEntry,
			ClassDefinition currentClassDefinition) {
		List<ClassProperty<Object>> properties = new LinkedList<ClassProperty<Object>>();
		CopyOnWriteArrayList<ClassProperty<Object>> copyList = new CopyOnWriteArrayList<>(
				currentClassDefinition.getProperties());
		for (ClassProperty<Object> property : copyList) {
			int i = 0;

			if (!formEntry.getClassProperties().contains(property)) {
				// TODO refactor into own method (Alex)
				if (property.getType().equals(PropertyType.ENUM)) {

					ClassProperty<EnumEntry> enumProperty = new ClassProperty<EnumEntry>();
					enumProperty.setId(property.getId());
					enumProperty.setName(property.getName());
					enumProperty.setType(PropertyType.ENUM);
					enumProperty.setRequired(property.isRequired());

					List<Relationship> associationList = (relationshipRepository
							.findBySourceAndRelationshipType(property.getId(), RelationshipType.ASSOCIATION));

					if (associationList != null) {
						for (Relationship r : associationList) {
							// for each Relationship, Do a DFS to construct dropdown options menu

							ClassDefinition classDefinition = classDefinitionRepository.findOne(r.getTarget());
							enumProperty.setAllowedValues(collectionService.aggregateAllEnumEntriesDFS(classDefinition,
									0, new ArrayList<EnumEntry>()));

							if (((Association) r).getTargetCardinality().equals(AssociationCardinality.ONE)) {
								enumProperty.setMultiple(false);
							} else if (((Association) r).getTargetCardinality()
									.equals(AssociationCardinality.ONESTAR)) {
								enumProperty.setMultiple(true);
							}

							currentClassDefinition.getProperties().remove(i);

							ArrayList temp = new ArrayList();
							temp.add(enumProperty);
							currentClassDefinition.getProperties().addAll(i, temp);
							properties.addAll(temp);
							i++;
						}
					}
				}

				if (!property.getType().equals(PropertyType.ENUM)) {
					properties.add(property);
				}
			}

		}

		return properties;
	}

	public List<FormConfiguration> aggregateChildrenById(List<String> rootIds) {

		List<ClassDefinition> rootClassDefintions = new ArrayList<ClassDefinition>();

		this.classDefinitionRepository.findAll(rootIds).forEach(rootClassDefintions::add);

		List<FormConfiguration> formConfigurations = new ArrayList<>();
		for (ClassDefinition rootClassDefinition : rootClassDefintions) {

			List<ClassDefinition> classDefinitions = new ArrayList<>();
			List<Relationship> relationships = new ArrayList<>();
			ClassConfiguration classConfiguration = classConfigurationRepository
					.findOne(rootClassDefinition.getConfigurationId());
			classDefinitionRepository.findAll(classConfiguration.getClassDefinitionIds())
					.forEach(classDefinitions::add);
			relationshipRepository.findAll(classConfiguration.getRelationshipIds()).forEach(relationships::add);

			FormEntry formEntry = collectionService.aggregateClassDefinitions(rootClassDefinition, new FormEntry(),
					classDefinitions, relationships);
			FormConfiguration formConfiguration = new FormConfiguration();
			formConfiguration.setId(rootClassDefinition.getId());
			formConfiguration.setName(rootClassDefinition.getName());
			formConfiguration.setFormEntry(formEntry);
			formConfigurations.add(formConfiguration);
		}

		return formConfigurations;
	}

	public List<FormConfiguration> aggregateChildren(List<ClassDefinition> classDefinitions,
			List<Relationship> relationships) {

		ClassDefinition rootClassDefinition = classDefinitions.stream().filter(cd -> cd.isRoot()).findFirst().get();
		List<FormConfiguration> formConfigurations = new ArrayList<>();

		FormEntry formEntry = collectionService.aggregateClassDefinitions(rootClassDefinition, new FormEntry(),
				classDefinitions, relationships);
		
		FormConfiguration formConfiguration = new FormConfiguration();
		formConfiguration.setId(rootClassDefinition.getId());
		formConfiguration.setName(rootClassDefinition.getName());
		formConfiguration.setFormEntry(formEntry);
		formConfigurations.add(formConfiguration);
		return formConfigurations;
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
