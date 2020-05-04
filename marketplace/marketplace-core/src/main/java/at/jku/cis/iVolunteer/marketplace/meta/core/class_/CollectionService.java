package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
import at.jku.cis.iVolunteer.model.matching.MatchingCollector;
import at.jku.cis.iVolunteer.model.matching.MatchingCollectorEntry;
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
public class CollectionService {

	private static final String PATH_DELIMITER = Character.toString((char) 28);

	@Autowired ClassConfigurationRepository classConfigurationRepository;
	@Autowired ClassDefinitionRepository classDefinitionRepository;
	@Autowired RelationshipRepository relationshipRepository;
	


	public List<ClassDefinition> collectAllClassDefinitionsWithPropertiesAsSingleCollection(String slotId) {
		ClassConfiguration configurator = classConfigurationRepository.findOne(slotId);
		if (configurator == null) {
			return null;
		}

		List<ClassDefinition> collectors = new ArrayList<ClassDefinition>();
		classDefinitionRepository.findAll(configurator.getClassDefinitionIds()).forEach(c -> {
			if (c.isCollector()) {
				collectors.add(c);
			}
		});

		for (ClassDefinition collector : collectors) {
			collector.setProperties(this.aggregateAllPropertiesDFS(collector, 0, collector.getProperties()));
		}

		return collectors;
	}

	public List<MatchingCollector> collectAllClassDefinitionsWithPropertiesAsMultipleCollections(String classConfigurationId) {
		ClassConfiguration classConfiguration = classConfigurationRepository.findOne(classConfigurationId);

		if (classConfiguration == null) {
			return null;
		}

		List<MatchingCollector> collections = new ArrayList<>();
		classDefinitionRepository.findAll(classConfiguration.getClassDefinitionIds()).forEach(c -> {
			if (c.isCollector()) {
				MatchingCollector collection = new MatchingCollector();
				collection.setClassDefinition(c);
				collection.setNumberOfProperties(c.getProperties().size());
				collection.setPathDelimiter(PATH_DELIMITER);
				collections.add(collection);
			}
		});

		for (MatchingCollector collection : collections) {
			collection.setPath(getPathFromRoot(collection.getClassDefinition()));
			
			collection.setCollectorEntries(this.aggregateAllClassDefinitionsWithPropertiesDFS(collection.getClassDefinition(),
					0, new ArrayList<>(), collection.getPath()));

			for (MatchingCollectorEntry entry : collection.getCollectorEntries()) {
				collection.setNumberOfProperties(
						collection.getNumberOfProperties() + entry.getClassDefinition().getProperties().size());
			}

			collection.setNumberOfDefinitions(collection.getCollectorEntries().size());

		}

		return collections;

	}

	String getPathFromRoot(ClassDefinition classDefinition) {
		ArrayList<String> pathArray = new ArrayList<>();
		pathArray.add(classDefinition.getId());

		while (!classDefinition.isRoot()) {
			List<Relationship> relationships = this.relationshipRepository
					.findByTargetAndRelationshipType(classDefinition.getId(), RelationshipType.AGGREGATION);
			if (relationships.size() >= 1) {
				classDefinition = classDefinitionRepository.findOne(relationships.get(0).getSource());
				pathArray.add(PATH_DELIMITER);
				pathArray.add(classDefinition.getId());
			}
		}

		Collections.reverse(pathArray);
		return String.join("", pathArray);

	}

	List<EnumEntry> aggregateAllEnumEntriesDFS(ClassDefinition root, int level, List<EnumEntry> list) {
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
				this.aggregateAllEnumEntriesDFS(classDefinition, level + 1, list);
			}
		}
		return list;
	}

	List<ClassProperty<Object>> aggregateAllPropertiesDFS(ClassDefinition root, int level,
			List<ClassProperty<Object>> list) {
		Stack<Relationship> stack = new Stack<Relationship>();
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
				for (ClassProperty<Object> property : classDefinition.getProperties()) {
					if (!list.stream().filter(p -> p.getId().contentEquals(property.getId())).findFirst().isPresent()) {
						list.add(property);
					}
				}
				this.aggregateAllPropertiesDFS(classDefinition, level + 1, list);
			}
		}
		return list;
	}

	List<MatchingCollectorEntry> aggregateAllClassDefinitionsWithPropertiesDFS(ClassDefinition root, int level,
			List<MatchingCollectorEntry> list, String path) {
		Stack<Relationship> stack = new Stack<Relationship>();
		List<Relationship> relationships = this.relationshipRepository.findBySourceAndRelationshipType(root.getId(),
				RelationshipType.AGGREGATION);

		Collections.reverse(relationships);
		stack.addAll(relationships);

		if (stack == null || stack.size() <= 0) {
			return list;
		} else {
			while (!stack.isEmpty()) {
				Relationship relationship = stack.pop();
				ClassDefinition classDefinition = classDefinitionRepository.findOne(relationship.getTarget());
				if (classDefinition.getProperties() != null && classDefinition.getProperties().size() > 0) {
					list.add(new MatchingCollectorEntry(classDefinition, path));
				}
				this.aggregateAllClassDefinitionsWithPropertiesDFS(classDefinition, level + 1, list,
						path + PATH_DELIMITER + classDefinition.getId());
			}
		}
		return list;
	}

	public List<EnumEntry> aggregateEnums(String classDefinitionId) {
		ClassDefinition enumHead = classDefinitionRepository.findOne(classDefinitionId);
		return aggregateAllEnumEntriesDFS(enumHead, 0, new ArrayList<>());
	}
	
	FormEntry getParentClassDefintions(ClassDefinition rootClassDefinition, FormEntry rootFormEntry, List<ClassDefinition> allClassDefinitons, List<Relationship> allRelationships) {

		rootFormEntry.setClassDefinitions(new ArrayList<ClassDefinition>());
		rootFormEntry.setClassProperties(new LinkedList<ClassProperty<Object>>());
		rootFormEntry.setEnumRepresentations(new ArrayList<EnumRepresentation>());
		rootFormEntry.setSubEntries(new ArrayList<FormEntry>());

		Queue<ClassDefinition> classDefinitions = new LinkedList<ClassDefinition>();
		classDefinitions.add(rootClassDefinition);
		
		while (!classDefinitions.isEmpty()) {

			ClassDefinition currentClassDefinition = classDefinitions.poll();
			rootFormEntry.getClassProperties().addAll(0, getPropertiesInClassDefinition(rootFormEntry, currentClassDefinition, allRelationships));
			rootFormEntry.getClassDefinitions().add(currentClassDefinition);
			
			if (!currentClassDefinition.isRoot()) {
				Relationship inheritance = allRelationships
						.stream()
						.filter(r -> r.getTarget().equals(currentClassDefinition.getId()) && r.getRelationshipType().equals(RelationshipType.INHERITANCE))
						.findFirst()
						.get();
				if (inheritance == null) {
					throw new NotAcceptableException("getParentById: child is not root and has no parent");
				}
				
				ClassDefinition next = allClassDefinitons.stream().filter(c -> c.getId().equals(inheritance.getSource())).findFirst().get();	
				if (next != null) {
					classDefinitions.add(next);
				}	
			}

			if (currentClassDefinition.getImagePath() != null && rootFormEntry.getImagePath() == null) {
				rootFormEntry.setImagePath(currentClassDefinition.getImagePath());
			}
		}
		
		return rootFormEntry;
	}
	
	private List<ClassProperty<Object>> getPropertiesInClassDefinition(FormEntry formEntry, ClassDefinition currentClassDefinition, List<Relationship> allRelationships) {
		List<ClassProperty<Object>> properties = new LinkedList<ClassProperty<Object>>();
		CopyOnWriteArrayList<ClassProperty<Object>> copyList = new CopyOnWriteArrayList<>(currentClassDefinition.getProperties());
		
		for (ClassProperty<Object> property : copyList) {
			if (!formEntry.getClassProperties().contains(property) && property.getType().equals(PropertyType.ENUM)) {
				handleEnumProperties(currentClassDefinition, properties, property);
			} else if (!formEntry.getClassProperties().contains(property) &&!property.getType().equals(PropertyType.ENUM)) {
				properties.add(property);
			}
		}

		return properties;
	}

	private void handleEnumProperties(ClassDefinition currentClassDefinition, List<ClassProperty<Object>> properties, ClassProperty<Object> property) {
//	TODO Alexander
//		
//		ClassProperty<EnumEntry> enumProperty = new ClassProperty<EnumEntry>();
//		
//		enumProperty.setId(property.getId());
//		enumProperty.setName(property.getName());
//		enumProperty.setType(PropertyType.ENUM);
//		enumProperty.setRequired(property.isRequired());
//
//		List<Relationship> associationList = (relationshipRepository.findBySourceAndRelationshipType(property.getId(), RelationshipType.ASSOCIATION));
//
//		if (associationList != null) {
//			int i = 0;
//			for (Relationship r : associationList) {
//				// for each Relationship, Do a DFS to construct dropdown options menu
//
//				ClassDefinition classDefinition = classDefinitionRepository.findOne(r.getTarget());
//				enumProperty.setAllowedValues(aggregateAllEnumEntriesDFS(classDefinition,
//						0, new ArrayList<EnumEntry>()));
//
//				if (((Association) r).getTargetCardinality().equals(AssociationCardinality.ONE)) {
//					enumProperty.setMultiple(false);
//				} else if (((Association) r).getTargetCardinality()
//						.equals(AssociationCardinality.ONESTAR)) {
//					enumProperty.setMultiple(true);
//				}
//
//				currentClassDefinition.getProperties().remove(i);
//
//				ArrayList temp = new ArrayList();
//				temp.add(enumProperty);
//				currentClassDefinition.getProperties().addAll(i, temp);
//				properties.addAll(temp);
//				i++;
//			}
//		}
	}

	FormEntry aggregateClassDefinitions(ClassDefinition rootClassDefinition, FormEntry rootFormEntry, List<ClassDefinition> allClassDefinitions, List<Relationship> allRelationships) {
		rootFormEntry.setClassDefinitions(new LinkedList<>());
		rootFormEntry.getClassDefinitions().add(rootClassDefinition);
		rootFormEntry.setClassProperties(rootClassDefinition.getProperties());

		List<Relationship> relationships = new ArrayList<>();
		
		for (Relationship r : allRelationships) {
			if (r.getRelationshipType().equals(RelationshipType.AGGREGATION) && r.getSource().equals(rootClassDefinition.getId())) {
				relationships.add(r);
			}
		}
		
		Collections.reverse(relationships);

		Stack<Relationship> stack = new Stack<Relationship>();
		stack.addAll(relationships);

		List<FormEntry> subFormEntries = new ArrayList<>();

		if (stack == null || stack.size() <= 0) {
			return rootFormEntry;
		} 
		
		while (!stack.isEmpty()) {
			Relationship relationship = stack.pop();				
			ClassDefinition classDefinition = allClassDefinitions.stream().filter(d -> d.getId().equals(relationship.getTarget())).findFirst().get();
			
			FormEntry subFormEntry = aggregateClassDefinitions(classDefinition, new FormEntry(), allClassDefinitions, allRelationships);
			subFormEntries.add(subFormEntry);
		}

		rootFormEntry.setSubEntries(subFormEntries);
		
		return rootFormEntry;

	}

}
