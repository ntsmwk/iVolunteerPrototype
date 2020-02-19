package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.meta.configurator.ConfiguratorRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.meta.configurator.Configurator;
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
	@Autowired private ConfiguratorRepository configuratorRepository;

	public ClassDefinition getByName(String name, String tenantId) {
		return classDefinitionRepository.findByNameAndTenantId(name, tenantId);
	}

	public ClassDefinition getClassDefinitionById(String id, String tenantId) {
		return classDefinitionRepository.getByIdAndTenantId(id, tenantId);
	}

	public List<ClassDefinition> getClassDefinitonsById(List<String> ids, String tenantId) {
		List<ClassDefinition> classDefinitions = new ArrayList<>();
		
		ids.forEach(id -> {
			classDefinitions.add(classDefinitionRepository.getByIdAndTenantId(id, tenantId));
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
		List<ClassDefinition> classDefinitions = classDefinitionRepository.getByClassArchetypeAndTenantId(archetype, tenantId);
		return  classDefinitions;
	}

//	private List<ClassDefinition> filterOrg(String organisation, List<ClassDefinition> classDefinitions) {
//		if (organisation == null) {
//			return classDefinitions;
//		}
//		if (organisation.equals("FF")) {
//			Configurator configurator = configuratorRepository.findOne("slot2");
//			return classDefinitions.stream().filter(cd -> configurator.getClassDefinitionIds().contains(cd.getId())|| cd.getName().equals("PersonTask"))
//					.collect(Collectors.toList());
//		}
//		if (organisation.equals("MV")) {
//			Configurator configurator = configuratorRepository.findOne("slot4");
//			return classDefinitions.stream().filter(cd -> configurator.getClassDefinitionIds().contains(cd.getId())|| cd.getName().equals("PersonTask"))
//					.collect(Collectors.toList());
//		}
//		return new ArrayList<>();
//	}

	public List<ClassDefinition> getAllClassDefinitionsWithoutEnums(String tenantId) {
		List<ClassDefinition> classDefinitions = classDefinitionRepository.getByTenantId(tenantId).stream()
				.filter(cd -> filterEnumsAndHeadClasses(cd)).collect(Collectors.toList());
		return  classDefinitions;
	}

	private boolean filterEnumsAndHeadClasses(ClassDefinition cd) {
		// @formatter:off
		return cd.getClassArchetype() == ClassArchetype.ACHIEVEMENT
				|| cd.getClassArchetype() == ClassArchetype.COMPETENCE
				|| cd.getClassArchetype() == ClassArchetype.FUNCTION 
				|| cd.getClassArchetype() == ClassArchetype.TASK;		 
		// @formatter:on
	}


	public List<FormConfiguration> getParentsById(List<String> childIds, String tenantId) {
		List<ClassDefinition> childClassDefinitions = new ArrayList<>();
		
		childIds.forEach(id -> {
			childClassDefinitions.add(classDefinitionRepository.getByIdAndTenantId(id, tenantId));
		});

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
						getPropertiesInClassDefinition(formEntry, currentClassDefinition, tenantId));

				List<Relationship> inheritanceList = relationshipRepository
						.findByTargetAndRelationshipType(currentClassDefinition.getId(), RelationshipType.INHERITANCE);

				if (inheritanceList == null || inheritanceList.size() == 0) {
					throw new NotAcceptableException("getParentById: child is not root and has no parent");
				}

				if (currentClassDefinition.getImagePath() != null && formEntry.getImagePath() == null) {
					formEntry.setImagePath(currentClassDefinition.getImagePath());
				}

				currentClassDefinition = classDefinitionRepository.getByIdAndTenantId(inheritanceList.get(0).getSource(), tenantId);
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
			ClassDefinition currentClassDefinition, String tenantId) {
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
							enumProperty.setAllowedValues(performDFS(classDefinition, 0, new ArrayList<EnumEntry>(), tenantId));

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

// Keep in case of changes of mind :)
//	private EnumRepresentation createEnumRepresentation(ClassDefinition root) {
//		List<EnumEntry> entries = new ArrayList<EnumEntry>();
//		entries = performDFS(root, 0, entries);
//		
//		EnumRepresentation enum
//		Representation = new EnumRepresentation();
//		enumRepresentation.setEnumEntries(entries);
//		enumRepresentation.setId(root.getId());
//		
//		return enumRepresentation;
//	}

	private List<EnumEntry> performDFS(ClassDefinition root, int level, List<EnumEntry> list, String tenantId) {
		Stack<Relationship> stack = new Stack<>();
		List<Relationship> relationships = this.relationshipRepository.findBySourceAndRelationshipType(root.getId(),
				RelationshipType.INHERITANCE);
		Collections.reverse(relationships);
		stack.addAll(relationships);

		if (stack == null || stack.size() <= 0) {
			return list;
		} else {
			while (!stack.isEmpty()) {
				Relationship relationship = stack.pop();
				ClassDefinition classDefinition = classDefinitionRepository.findOne(relationship.getTarget());
				EnumEntry enumEntry = new EnumEntry(level, classDefinition.getName(), true);
				enumEntry.setPosition(new int[level + 1]);
				list.add(enumEntry);
				this.performDFS(classDefinition, level + 1, list, tenantId);
			}
		}
		return list;
	}

	// TODO @Alex implement
	public List<String> getChildrenById(List<String> rootIds, String tenantId) {

		List<ClassDefinition> rootClassDefintions = new ArrayList<ClassDefinition>();
		
		rootIds.forEach(id -> {
			rootClassDefintions.add(classDefinitionRepository.getByIdAndTenantId(id, tenantId));
		});

		List<String> returnIds;
		for (ClassDefinition rootClassDefinitions : rootClassDefintions) {

		}

		return null;
	}

	public List<EnumEntry> getEnumValues(String classDefinitionId, String tenantId) {
		ClassDefinition enumHead = classDefinitionRepository.getByIdAndTenantId(classDefinitionId, tenantId);
		return performDFS(enumHead, 0, new ArrayList<>(), tenantId);
	}

}
