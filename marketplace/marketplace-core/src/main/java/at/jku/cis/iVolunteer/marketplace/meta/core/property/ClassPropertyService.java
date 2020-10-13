package at.jku.cis.iVolunteer.marketplace.meta.core.property;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import at.jku.cis.iVolunteer.marketplace._mapper.property.TreePropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace._mapper.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.treeProperty.TreePropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;

@Service
public class ClassPropertyService {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private FlatPropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	@Autowired private TreePropertyDefinitionRepository treePropertyDefinitionRepository;
	@Autowired private TreePropertyDefinitionToClassPropertyMapper enumDefinitionToClassPropertyMapper;

	
	public List<ClassProperty<Object>> getAllClassPropertiesFromClass(String classDefinitionId) {
		ClassDefinition classDefinition = classDefinitionRepository.findOne(classDefinitionId);
		List<ClassProperty<Object>> properties = new ArrayList<ClassProperty<Object>>();
		if (classDefinition != null) {
			properties.addAll(classDefinition.getProperties());

			// add class properties from parent
			List<Relationship> relationships = relationshipRepository.
					  findByTargetAndRelationshipType(classDefinitionId, RelationshipType.INHERITANCE);
			relationships.stream().
			        forEach(r -> properties.addAll(getAllClassPropertiesFromClass(r.getSource())));	      
		
			// add class properties via aggregation, composition
			relationships = relationshipRepository.
					       findBySource(classDefinitionId);	
			relationships.stream()
			             .filter(t -> t.getRelationshipType().equals(RelationshipType.ASSOCIATION))
			             .forEach(r -> properties.addAll(getAllClassPropertiesFromClass(r.getTarget())));
		}
		return properties;
	}

	public ClassProperty<Object> getClassPropertyById(String classDefinitionId, String classPropertyId) {
		ClassDefinition classDefinition = classDefinitionRepository.findOne(classDefinitionId);
		if (classDefinition != null) {
			return findClassProperty(classDefinition, classPropertyId);
		}
		return null;
	}
	
	public ClassProperty<Object> getClassPropertyByName(String classDefinitionId, String classPropertyName, String tenantId) {
		ClassDefinition classDefinition = classDefinitionRepository.getByIdAndTenantId(classDefinitionId, tenantId);
		if (classDefinition != null) {
			return findClassPropertyByName(classDefinition, classPropertyName);
		}
		return null;
	}

	public ClassProperty<Object> updateClassProperty(String classDefinitionId, String classPropertyId,
			ClassProperty<Object> updatedClassProperty) {
		ClassDefinition classDefinition = classDefinitionRepository.findOne(classDefinitionId);

		if (classDefinition != null) {
			int index = findIndexOfClassProperty(classDefinition, classPropertyId);
			classDefinition.getProperties().set(index, updatedClassProperty);
			classDefinitionRepository.save(classDefinition);
			return classDefinition.getProperties().get(index);

		}
		return null;
	}

	private ClassProperty<Object> findClassProperty(ClassDefinition classDefinition, String classPropertyId) {
		// @formatter:off
		return classDefinition
				.getProperties()
				.stream()
				.filter(p -> p.getId().equals(classPropertyId))
				.findFirst().orElse(null);
		// @formatter:on
	}
	
	private ClassProperty<Object> findClassPropertyByName(ClassDefinition classDefinition, String classPropertyName) {
		// @formatter:off
		return classDefinition
				.getProperties()
				.stream()
				.filter(p -> p.getName().equals(classPropertyName))
				.findFirst().orElse(null);
		// @formatter:on
	}

	private int findIndexOfClassProperty(ClassDefinition classDefinition, String classPropertyId) {
		// @formatter:off
		return IntStream.range(0, classDefinition.getProperties().size())
			     .filter(i -> classPropertyId.equals(classDefinition.getProperties().get(i).getId()))
			     .findFirst()
			     .orElse(-1);
		// @formatter:on		
	}
	
	// search class property in all inherited properties up the hierarchy
	public ClassProperty<Object> getClassPropertyFromAllClassProperties(String classDefinitionId, String classPropertyId){
		List<ClassProperty<Object>> classProperties = getAllClassPropertiesFromClass(classDefinitionId);
		return classProperties.stream()
		               .filter(entry -> entry.getId().equals(classPropertyId))
		               .findFirst().orElse(null);
		
	}
	
	List<ClassProperty<Object>> getClassPropertyFromDefinitionById(List<String> flatPropertyIds, List<String> treePropertyIds) {
		List<FlatPropertyDefinition<Object>> flatProperties = new ArrayList<>();
		List<TreePropertyDefinition> treeProperties = new ArrayList<>();
		
		if (flatPropertyIds != null) {
			propertyDefinitionRepository.findAll(flatPropertyIds).forEach(flatProperties::add);
		}
		if (treePropertyIds != null) {
			treePropertyDefinitionRepository.findAll(treePropertyIds).forEach(treeProperties::add);
		}
		
		List<ClassProperty<Object>> flatClassProperties = createClassPropertiesFromDefinitions(flatProperties);
		List<ClassProperty<Object>> treeClassProperties = createClassPropertiesFromEnumDefinitions(treeProperties);
		
		flatClassProperties.addAll(treeClassProperties);
		return flatClassProperties;
	}

	// TODO: Philipp: tenantId check required?
	List<ClassProperty<Object>> addPropertiesToClassDefinitionById(String id, @RequestBody List<String> propertyIds) {
		
		List<FlatPropertyDefinition<Object>> properties = new ArrayList<>();
		propertyDefinitionRepository.findAll(propertyIds).forEach(properties::add);;
		List<ClassProperty<Object>> classProperties = createClassPropertiesFromDefinitions(properties);

		ClassDefinition clazz = storeClassProperties(id, classProperties);
		return clazz.getProperties();
	}

	List<ClassProperty<Object>> addPropertiesToClassDefinition(String id, List<ClassProperty<Object>> properties) {
		ClassDefinition clazz = storeClassProperties(id, properties);
		return clazz.getProperties();
	}

	ClassDefinition removePropertiesFromClassDefinition(String id, List<String> idsToRemove) {
		ClassDefinition clazz = classDefinitionRepository.findOne(id);

		// @formatter:off
		ArrayList<ClassProperty<Object>> remainingObjects = 
				clazz
					.getProperties()
					.stream()
					.filter(c -> idsToRemove.stream().noneMatch(remId -> c.getId().equals(remId)))
					.collect(Collectors.toCollection(ArrayList::new));
		// @formatter:on

		clazz.setProperties(remainingObjects);
		return classDefinitionRepository.save(clazz);
	}

	private ClassDefinition storeClassProperties(String id, List<ClassProperty<Object>> classProperties) {
		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		if (clazz.getProperties() == null) {
			clazz.setProperties(new ArrayList<>());
		}
		clazz.getProperties().addAll(classProperties);
		clazz = classDefinitionRepository.save(clazz);
		return clazz;
	}

	private List<ClassProperty<Object>> createClassPropertiesFromDefinitions(List<FlatPropertyDefinition<Object>> propertyDefinitions) {
		List<ClassProperty<Object>> cProps = new ArrayList<>();
		for (FlatPropertyDefinition<Object> pd : propertyDefinitions) {
			cProps.add(propertyDefinitionToClassPropertyMapper.toTarget(pd));
		}
		return cProps;
	}
	
	private List<ClassProperty<Object>> createClassPropertiesFromEnumDefinitions(List<TreePropertyDefinition> enums) {
		List<ClassProperty<Object>> cProps = new ArrayList<>();
		for (TreePropertyDefinition ed : enums) {
			cProps.add(enumDefinitionToClassPropertyMapper.toTarget(ed));
		}
		return cProps;
	}

}
