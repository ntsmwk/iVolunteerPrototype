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

import org.apache.commons.collections4.iterators.SingletonListIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace._mapper.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.matching.MatchingCollector;
import at.jku.cis.iVolunteer.model.matching.MatchingCollectorEntry;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.model.meta.core.relationship.AssociationCardinality;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;
import at.jku.cis.iVolunteer.model.meta.form.EnumEntry;
import at.jku.cis.iVolunteer.model.meta.form.EnumRepresentation;
import at.jku.cis.iVolunteer.model.meta.form.FormConfiguration;
import at.jku.cis.iVolunteer.model.meta.form.FormEntry;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class CollectionService {

	private static final String PATH_DELIMITER = Character.toString((char) 28);

	@Autowired ClassConfigurationRepository classConfigurationRepository;
	@Autowired ClassDefinitionRepository classDefinitionRepository;
	@Autowired RelationshipRepository relationshipRepository;
	
	@Autowired PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	
	public List<ClassDefinition> collectAllClassDefinitionsWithPropertiesAsList(String slotId) {
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
			collector.setProperties(aggregateAllPropertiesDFS(collector, 0, collector.getProperties()));
		}

		return collectors;
	}

	public List<MatchingCollector> collectAllClassDefinitionsWithPropertiesAsMatchingCollectors(String classConfigurationId) {
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

	private String getPathFromRoot(ClassDefinition classDefinition) {
		ArrayList<String> pathArray = new ArrayList<>();
		pathArray.add(classDefinition.getId());

		while (!classDefinition.isRoot()) {
			List<Relationship> relationships = this.relationshipRepository
					.findByTarget(classDefinition.getId());
			
			relationships = relationships.stream().filter(r -> r.getRelationshipType().equals(RelationshipType.AGGREGATION) | r.getRelationshipType().equals(RelationshipType.INHERITANCE)).collect(Collectors.toList());
			
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
		List<Relationship> relationships = this.relationshipRepository.findBySourceAndRelationshipType(root.getId(), RelationshipType.INHERITANCE);
		Collections.reverse(relationships);
		stack.addAll(relationships);

		if (stack == null || stack.size() <= 0) {
			return list;
		}
		
		while (!stack.isEmpty()) {
			Relationship relationship = stack.pop();
			ClassDefinition classDefinition = classDefinitionRepository.findOne(relationship.getTarget());
			EnumEntry enumEntry = new EnumEntry(level, classDefinition.getName(), true);
			enumEntry.setPosition(new int[level + 1]);
			list.add(enumEntry);
			this.aggregateAllEnumEntriesDFS(classDefinition, level + 1, list);
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
		}
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
		return list;
	}

	List<MatchingCollectorEntry> aggregateAllClassDefinitionsWithPropertiesDFS(ClassDefinition root, int level,
			List<MatchingCollectorEntry> list, String path) {
		Stack<Relationship> stack = new Stack<Relationship>();
		List<Relationship> relationships = this.relationshipRepository.findBySource(root.getId());
		relationships = relationships.stream().filter(r -> r.getRelationshipType().equals(RelationshipType.AGGREGATION) | r.getRelationshipType().equals(RelationshipType.INHERITANCE)).collect(Collectors.toList());

	
		Collections.reverse(relationships);
		stack.addAll(relationships);

		if (stack == null || stack.size() <= 0) {
			return list;
		} 
		while (!stack.isEmpty()) {
			Relationship relationship = stack.pop();
			ClassDefinition classDefinition = classDefinitionRepository.findOne(relationship.getTarget());
			if (classDefinition.getProperties() != null && classDefinition.getProperties().size() > 0) {
				list.add(new MatchingCollectorEntry(classDefinition, path + PATH_DELIMITER + classDefinition.getId(), PATH_DELIMITER));
			}
			this.aggregateAllClassDefinitionsWithPropertiesDFS(classDefinition, level + 1, list,
					path + PATH_DELIMITER + classDefinition.getId());
		}
		
		return list;
	}

	public List<EnumEntry> aggregateEnums(String classDefinitionId) {
		ClassDefinition enumHead = classDefinitionRepository.findOne(classDefinitionId);
		return aggregateAllEnumEntriesDFS(enumHead, 0, new ArrayList<>());
	}
	
//	FormEntry getParentClassDefintions(ClassDefinition rootClassDefinition, FormEntry rootFormEntry, List<ClassDefinition> allClassDefinitons, List<Relationship> allRelationships) {
//
//		rootFormEntry.setClassDefinitions(new ArrayList<ClassDefinition>());
//		rootFormEntry.setClassProperties(new LinkedList<ClassProperty<Object>>());
//		rootFormEntry.setEnumRepresentations(new ArrayList<EnumRepresentation>());
//		rootFormEntry.setSubEntries(new ArrayList<FormEntry>());
//
//		Queue<ClassDefinition> classDefinitions = new LinkedList<ClassDefinition>();
//		classDefinitions.add(rootClassDefinition);
//		
//		while (!classDefinitions.isEmpty()) {
//
//			ClassDefinition currentClassDefinition = classDefinitions.poll();
//			rootFormEntry.getClassProperties().addAll(0, getPropertiesInClassDefinition(rootFormEntry, currentClassDefinition, allRelationships));
//			rootFormEntry.getClassDefinitions().add(currentClassDefinition);
//			
//			if (!currentClassDefinition.isRoot()) {
//				Relationship inheritance = allRelationships
//						.stream()
//						.filter(r -> r.getTarget().equals(currentClassDefinition.getId()) && r.getRelationshipType().equals(RelationshipType.INHERITANCE))
//						.findFirst()
//						.get();
//				if (inheritance == null) {
//					throw new NotAcceptableException("getParentById: child is not root and has no parent");
//				}
//				
//				ClassDefinition next = allClassDefinitons.stream().filter(c -> c.getId().equals(inheritance.getSource())).findFirst().get();	
//				if (next != null) {
//					classDefinitions.add(next);
//				}	
//			}
//
//			if (currentClassDefinition.getImagePath() != null && rootFormEntry.getImagePath() == null) {
//				rootFormEntry.setImagePath(currentClassDefinition.getImagePath());
//			}
//		}
//		
//		return rootFormEntry;
//	}
	
//	private List<ClassProperty<Object>> getPropertiesInClassDefinition(FormEntry formEntry, ClassDefinition currentClassDefinition, List<Relationship> allRelationships) {
//		List<ClassProperty<Object>> properties = new LinkedList<ClassProperty<Object>>();
//		CopyOnWriteArrayList<ClassProperty<Object>> copyList = new CopyOnWriteArrayList<>(currentClassDefinition.getProperties());
//		
//		for (ClassProperty<Object> property : copyList) {
//			if (!formEntry.getClassProperties().contains(property) && property.getType().equals(PropertyType.ENUM)) {
//				handleEnumProperties(currentClassDefinition, properties, property);
//			} else if (!formEntry.getClassProperties().contains(property) &&!property.getType().equals(PropertyType.ENUM)) {
//				properties.add(property);
//			}
//		}
//
//		return properties;
//	}

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

//	FormEntry aggregateClassDefinitions(ClassDefinition rootClassDefinition, FormEntry rootFormEntry, List<ClassDefinition> allClassDefinitions, List<Relationship> allRelationships) {
//		rootFormEntry.setClassDefinitions(new LinkedList<>());
//		rootFormEntry.getClassDefinitions().add(rootClassDefinition);
//		rootFormEntry.setClassProperties(rootClassDefinition.getProperties());
//
//		List<Relationship> relationships = new ArrayList<>();
//		
//		for (Relationship r : allRelationships) {
//			if (r.getRelationshipType().equals(RelationshipType.AGGREGATION) && r.getSource().equals(rootClassDefinition.getId())) {
//				relationships.add(r);
//			}
//		}
//		
//		Collections.reverse(relationships);
//
//		Stack<Relationship> stack = new Stack<Relationship>();
//		stack.addAll(relationships);
//
//		List<FormEntry> subFormEntries = new ArrayList<>();
//
//		if (stack == null || stack.size() <= 0) {
//			return rootFormEntry;
//		} 
//		
//		while (!stack.isEmpty()) {
//			Relationship relationship = stack.pop();				
//			ClassDefinition classDefinition = allClassDefinitions.stream().filter(d -> d.getId().equals(relationship.getTarget())).findFirst().get();
//			
//			FormEntry subFormEntry = aggregateClassDefinitions(classDefinition, new FormEntry(classDefinition.getId()), allClassDefinitions, allRelationships);
//			subFormEntries.add(subFormEntry);
//		}
//
//		rootFormEntry.setSubEntries(subFormEntries);
//		
//		return rootFormEntry;
//
//	}
	
	FormEntry aggregateFormEntry(ClassDefinition currentClassDefinition, FormEntry currentFormEntry, List<ClassDefinition> allClassDefinitions, List<Relationship> allRelationships, boolean directionUp) {
		
		// Next ClassDefinition
		
		currentFormEntry.setClassDefinitions(new LinkedList<>());
		currentFormEntry.getClassDefinitions().add(currentClassDefinition);
		
		// Collect Properties
		currentFormEntry.getClassProperties().addAll(0, currentClassDefinition.getProperties());
		
		// grab target-side Relationships
		List<Relationship> targetRelationships = allRelationships.stream().filter(r -> r.getTarget().equals(currentClassDefinition.getId())).collect(Collectors.toList());
		// grab source-side Relationships
		List<Relationship> sourceRelationships = allRelationships.stream().filter(r -> r.getSource().equals(currentClassDefinition.getId())).collect(Collectors.toList());

		Collections.reverse(targetRelationships);
		Collections.reverse(sourceRelationships);
		Stack<Relationship> targetStack = new Stack<Relationship>();
		Stack<Relationship> sourceStack = new Stack<Relationship>();
		targetStack.addAll(targetRelationships);
		sourceStack.addAll(sourceRelationships);
		List<FormEntry> subFormEntries = new ArrayList<>();
			
		boolean unableToContinuePropertySet = false;
		ClassProperty<Object> unableToContinueProperty = new ClassProperty<Object>();
		unableToContinueProperty.setId("unableToContinue");
		unableToContinueProperty.setName("Choose which Class to Instantiate");
		unableToContinueProperty.setAllowedValues(new ArrayList<Object>());
		unableToContinueProperty.setType(PropertyType.TEXT);
		
		while (!targetStack.isEmpty()) {
			Relationship relationship = targetStack.pop();
			if (relationship.getRelationshipType().equals(RelationshipType.INHERITANCE)) {
				ClassDefinition classDefinition = allClassDefinitions.stream().filter(d -> d.getId().equals(relationship.getSource())).findFirst().get();
				currentFormEntry = aggregateFormEntry(classDefinition, currentFormEntry, allClassDefinitions, allRelationships, true);
			}
		}
		
		while (!sourceStack.isEmpty()) {
			Relationship relationship = sourceStack.pop();
			if (relationship.getRelationshipType().equals(RelationshipType.AGGREGATION)) {
				ClassDefinition classDefinition = allClassDefinitions.stream().filter(d -> d.getId().equals(relationship.getTarget())).findFirst().get();
				FormEntry subFormEntry = aggregateFormEntry(classDefinition, new FormEntry(classDefinition.getId()), allClassDefinitions, allRelationships, false);
				subFormEntries.add(subFormEntry);
			} else if (relationship.getRelationshipType().equals(RelationshipType.INHERITANCE)) {
				if (!directionUp) {
					unableToContinuePropertySet = true;
					
					ClassDefinition classDefinition = allClassDefinitions.stream().filter(cd -> cd.getId().equals(relationship.getTarget())).findFirst().get();					
					unableToContinueProperty.getAllowedValues().add(classDefinition.getName());
				}
			}	
		}
		
		if (unableToContinuePropertySet) {
			currentFormEntry.getClassProperties().add(unableToContinueProperty);
		}
		
		if (currentFormEntry.getSubEntries() == null || currentFormEntry.getSubEntries().size() <= 0) {
			currentFormEntry.setSubEntries(subFormEntries);
		} else {
			currentFormEntry.getSubEntries().addAll(subFormEntries);
		}
		
		
		// handle target Relationships
			// if next target-side Relationship: Aggregation - goto handle Aggregation
			// if next target-side  Relationship: Inheritance:
				// display selection, exit
			// if no next target-side Relationship: exit
		
		// handle source Relationships
			// if next source-side Relationship: Inheritance - goto handle Inheritance
			// if no source-side Relationship next: exit
		
		return currentFormEntry;
	}

}
