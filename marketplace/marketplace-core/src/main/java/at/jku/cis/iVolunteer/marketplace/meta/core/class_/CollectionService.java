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

import at.jku.cis.iVolunteer.marketplace.meta.configurator.ConfiguratorRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.matching.MatchingClassDefinitionCollection;
import at.jku.cis.iVolunteer.model.matching.MatchingClassDefinitionCollectionEntry;
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
public class CollectionService {

	@Autowired ConfiguratorRepository configuratorRepository;
	@Autowired ClassDefinitionRepository classDefinitionRepository;
	@Autowired RelationshipRepository relationshipRepository;

	public List<ClassDefinition> collectAllClassDefinitionsWithPropertiesAsSingleCollection(String slotId) {
		Configurator configurator = configuratorRepository.findOne(slotId);
		if (configurator == null) {
			return null;
		}

		List<ClassDefinition> collectors = new ArrayList<ClassDefinition>();
		classDefinitionRepository.findAll(configurator.getClassDefinitionIds()).forEach(c -> {
			if (c.getClassArchetype() == ClassArchetype.FLEXPROD_COLLECTOR) {
				collectors.add(c);
			}
		});

		for (ClassDefinition collector : collectors) {
			collector.setProperties(this.aggregateAllPropertiesDFS(collector, 0, collector.getProperties()));
		}

		return collectors;
	}

	public List<MatchingClassDefinitionCollection> collectAllClassDefinitionsWithPropertiesAsCollections(
			String slotId) {
		Configurator configurator = configuratorRepository.findOne(slotId);

		if (configurator == null) {
			return null;
		}

		List<MatchingClassDefinitionCollection> collections = new ArrayList<>();
		classDefinitionRepository.findAll(configurator.getClassDefinitionIds()).forEach(c -> {
			if (c.getClassArchetype() == ClassArchetype.FLEXPROD_COLLECTOR) {
				MatchingClassDefinitionCollection collection = new MatchingClassDefinitionCollection();
				collection.setCollector(c);
				collection.setNumberOfProperties(c.getProperties().size());
				collections.add(collection);
			}
		});
		
		for (MatchingClassDefinitionCollection collection : collections) {
			
			
			collection.setCollectionEntries(this.aggregateAllClassDefinitionsWithPropertiesDFS(collection.getCollector(),
					0, new ArrayList<MatchingClassDefinitionCollectionEntry>(),
					getPathFromRoot(collection.getCollector())));

			for (MatchingClassDefinitionCollectionEntry entry : collection.getCollectionEntries()) {
				collection.setNumberOfProperties(
						collection.getNumberOfProperties() + entry.getClassDefinition().getProperties().size());
			}

			collection.setNumberOfDefinitions(collection.getCollectionEntries().size());
		

		}

		return collections;

	}
	
	String getPathFromRoot(ClassDefinition classDefinition) {
		ArrayList<String> pathArray = new ArrayList<>();
		pathArray.add(classDefinition.getId());

		while (!classDefinition.isRoot()) {
			List<Relationship> relationships = this.relationshipRepository.findByTargetAndRelationshipType(classDefinition.getId(), RelationshipType.AGGREGATION);
			if (relationships.size() >= 1) {
				classDefinition = classDefinitionRepository.findOne(relationships.get(0).getSource());
				pathArray.add(".");
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

	List<MatchingClassDefinitionCollectionEntry> aggregateAllClassDefinitionsWithPropertiesDFS(ClassDefinition root, int level,
			List<MatchingClassDefinitionCollectionEntry> list, String path) {
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
					list.add(new MatchingClassDefinitionCollectionEntry(classDefinition, path));
				}
				this.aggregateAllClassDefinitionsWithPropertiesDFS(classDefinition, level + 1, list,
						path + "." + classDefinition.getId());
			}
		}
		return list;
	}

	public List<EnumEntry> aggregateEnums(String classDefinitionId) {
		ClassDefinition enumHead = classDefinitionRepository.findOne(classDefinitionId);
		return aggregateAllEnumEntriesDFS(enumHead, 0, new ArrayList<>());
	}

	FormEntry aggregateClassDefinitions(ClassDefinition rootClassDefinition, FormEntry rootFormEntry) {

		rootFormEntry.setClassDefinitions(new LinkedList<>());
		rootFormEntry.getClassDefinitions().add(rootClassDefinition);

		rootFormEntry.setClassProperties(rootClassDefinition.getProperties());

		List<Relationship> relationships = relationshipRepository
				.findBySourceAndRelationshipType(rootClassDefinition.getId(), RelationshipType.AGGREGATION);

		Collections.reverse(relationships);

		Stack<Relationship> stack = new Stack<Relationship>();
		stack.addAll(relationships);

		List<FormEntry> subFormEntries = new ArrayList<>();

		if (stack == null || stack.size() <= 0) {
			return rootFormEntry;
		} else {
			while (!stack.isEmpty()) {
				Relationship relationship = stack.pop();
				ClassDefinition classDefinition = classDefinitionRepository.findOne(relationship.getTarget());

				FormEntry subFormEntry = aggregateClassDefinitions(classDefinition, new FormEntry());
				subFormEntries.add(subFormEntry);
			}

			rootFormEntry.setSubEntries(subFormEntries);
		}
		return rootFormEntry;

	}

}
