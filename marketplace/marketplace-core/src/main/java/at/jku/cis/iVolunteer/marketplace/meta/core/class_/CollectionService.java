package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

	FormEntry aggregateClassDefinitions(ClassDefinition rootClassDefinition, FormEntry rootFormEntry, List<ClassDefinition> allClassDefinitions, List<Relationship> allRelationships) {

		rootFormEntry.setClassDefinitions(new LinkedList<>());
		rootFormEntry.getClassDefinitions().add(rootClassDefinition);

		rootFormEntry.setClassProperties(rootClassDefinition.getProperties());
//
//		List<Relationship> relationships = relationshipRepository
//				.findBySourceAndRelationshipType(rootClassDefinition.getId(), RelationshipType.AGGREGATION);

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
		} else {
			while (!stack.isEmpty()) {
				Relationship relationship = stack.pop();
//				ClassDefinition classDefinition = classDefinitionRepository.findOne(relationship.getTarget());
				
				ClassDefinition classDefinition = allClassDefinitions.stream().filter(d -> d.getId().equals(relationship.getTarget())).findFirst().get();
				
				FormEntry subFormEntry = aggregateClassDefinitions(classDefinition, new FormEntry(), allClassDefinitions, allRelationships);
				subFormEntries.add(subFormEntry);
			}

			rootFormEntry.setSubEntries(subFormEntries);
		}
		return rootFormEntry;

	}

}
