package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassDefinitionMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.property.ClassPropertyMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipController;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.class_.dtos.ClassDefinitionDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.ClassPropertyDTO;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;
import jersey.repackaged.com.google.common.collect.Lists;

@RestController
public class ClassDefinitionController {

	@Autowired ClassDefinitionRepository classDefinitionRepository;
	@Autowired RelationshipRepository relationshipRepository;
	@Autowired PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired ClassPropertyMapper classPropertyMapper;
	@Autowired ClassDefinitionMapper classDefinitionMapper;
	@Autowired PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	@Autowired RelationshipController relationshipController;

	/*
	 * Operations on ClassDefinition
	 */

	@GetMapping("/meta/core/class/definition/all")
	private List<ClassDefinitionDTO> getAllClassDefinitions() {
		return classDefinitionMapper.toDTOs(classDefinitionRepository.findAll());
	}

	@GetMapping("/meta/core/class/definition/{id}")
	private ClassDefinitionDTO getClassDefinitionById(@PathVariable("id") String id) {
		return classDefinitionMapper.toDTO(classDefinitionRepository.findOne(id));
	}

	@PutMapping("/meta/core/class/definition/multiple")
	private List<ClassDefinitionDTO> getClassDefinitonsById(@RequestBody List<String> ids) {
		List<ClassDefinition> classDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(ids).forEach(classDefinitions::add);
		return classDefinitionMapper.toDTOs(classDefinitions);
	}

	@PostMapping("/meta/core/class/definition/new")
	private ClassDefinitionDTO newClassDefinition(@RequestBody ClassDefinitionDTO classDefinition) {
		return classDefinitionMapper
				.toDTO(classDefinitionRepository.save(classDefinitionMapper.toEntity(classDefinition)));
	}

	@PutMapping("/meta/core/class/definition/{id}/change-name")
	private ClassDefinitionDTO changeClassDefinitionName(@PathVariable("id") String id, @RequestBody String newName) {
		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		clazz.setName(newName);
		return classDefinitionMapper.toDTO(classDefinitionRepository.save(clazz));
	}

	@PutMapping("/meta/core/class/definition/delete")
	private List<ClassDefinitionDTO> deleteClassDefinition(@RequestBody List<String> idsToRemove) {
		for (String id : idsToRemove) {
			classDefinitionRepository.delete(id);
		}
		return classDefinitionMapper.toDTOs(classDefinitionRepository.findAll());

	}

	@PutMapping("/meta/core/class/definition/add-or-update")
	private List<ClassDefinitionDTO> addOrUpdateClassDefinitions(
			@RequestBody List<ClassDefinitionDTO> classDefinitions) {
		return this.classDefinitionMapper
				.toDTOs(this.classDefinitionRepository.save(classDefinitionMapper.toEntities(classDefinitions)));
	}

	@PutMapping("meta/core/class/definition/get-children")
	private List<String> getChildrenById(@RequestBody List<String> rootIds) {
		List<ClassDefinition> rootClassDefintions = new ArrayList<ClassDefinition>();
		classDefinitionRepository.findAll(rootIds).forEach(rootClassDefintions::add);

		List<String> returnIds;
		for (ClassDefinition rootClassDefinitions : rootClassDefintions) {

		}

		return null;
	}

	@PutMapping("meta/core/class/defintiion/get-parents")
	private List<String> getParentsById(@RequestBody List<String> childIds) {
		List<ClassDefinition> childClassDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(childIds).forEach(childClassDefinitions::add);

		List<Relationship> allRelationships = relationshipRepository.findAll();
		List<String> returnIds = new ArrayList<>();

		// Pre-Condition: Graph must be acyclic - a child can only have one parent, one
		// parent can have multiple children
		// Work our way up the chain until we are at the root

		for (ClassDefinition childClassDefinition : childClassDefinitions) {
			Map<String, String> returnIdMap = new HashMap<String, String>();
			int i = 0;
			ClassDefinition currentClassDefinition = childClassDefinition;
			do {
				// add to map
				returnIdMap.put(i + "", currentClassDefinition.getId());
				// find relationship connecting this child with its parents
				List<Relationship> relationshipList = relationshipRepository.findByClassId1AndRelationshipType(
						currentClassDefinition.getId(), RelationshipType.INHERITANCE);

				if (relationshipList == null || relationshipList.size() == 0) {
					throw new NotAcceptableException("getParentById: child has no parent");
				}
				// traverse - find and assign new currentClassDefinition
				currentClassDefinition = classDefinitionRepository.findOne(relationshipList.get(0).getClassId2());

			} while (!currentClassDefinition.isRoot());
			// TODO turn map into JSON
			// TODO append JSON to String List
		}

		return null;
	}

	/*
	 * Operations on Properties
	 */

	@PutMapping("meta/core/class/definition/get-classproperty-from-propertydefinition-by-id")
	private List<ClassPropertyDTO<Object>> getClassPropertyFromPropertyDefinitionById(
			@RequestBody List<String> propertyIds) {
		List<PropertyDefinition<Object>> definitionProperties = Lists
				.newLinkedList(propertyDefinitionRepository.findAll(propertyIds));
		List<ClassProperty<Object>> classProperties = createClassPropertiesFromDefinitions(definitionProperties);
		return classPropertyMapper.toDTOs(classProperties);
	}

	@PutMapping("meta/core/class/definition/{id}/add-properties-by-id")
	private List<ClassPropertyDTO<Object>> addPropertiesToClassDefinitionById(@PathVariable("id") String id,
			@RequestBody List<String> propertyIds) {
		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		List<PropertyDefinition<Object>> definitionProperties = Lists
				.newLinkedList(propertyDefinitionRepository.findAll(propertyIds));
		List<ClassProperty<Object>> classProperties = createClassPropertiesFromDefinitions(definitionProperties);

		if (clazz.getProperties() == null) {
			clazz.setProperties(new ArrayList<>());
		}

		clazz.getProperties().addAll(classProperties);
		clazz = classDefinitionRepository.save(clazz);
		return classPropertyMapper.toDTOs(clazz.getProperties());
	}

	@PutMapping("meta/core/class/definition/{id}/add-properties")
	private List<ClassPropertyDTO<Object>> addPropertiesToClassDefinition(@PathVariable("id") String id,
			@RequestBody List<ClassPropertyDTO<Object>> properties) {

		ClassDefinition clazz = classDefinitionRepository.findOne(id);

		List<ClassProperty<Object>> props = classPropertyMapper.toEntities(properties);
		clazz.getProperties().addAll(props);
		clazz = classDefinitionRepository.save(clazz);
		return classPropertyMapper.toDTOs(clazz.getProperties());
	}

	private List<ClassProperty<Object>> createClassPropertiesFromDefinitions(
			List<PropertyDefinition<Object>> propertyDefinitions) {
		List<ClassProperty<Object>> cProps = new ArrayList<>();

		for (PropertyDefinition<Object> d : propertyDefinitions) {
			cProps.add(propertyDefinitionToClassPropertyMapper.toTarget(d));
		}

		return cProps;
	}

	@PutMapping("/meta/core/class/definition/{id}/remove-properties")
	private ClassDefinitionDTO removePropertiesFromClassDefinition(@PathVariable("id") String id,
			@RequestBody List<String> idsToRemove) {

		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		ArrayList<ClassProperty<Object>> remainingObjects = clazz.getProperties().stream()
				.filter(c -> idsToRemove.stream().noneMatch(remId -> c.getId().equals(remId)))
				.collect(Collectors.toCollection(ArrayList::new));

		clazz.setProperties(remainingObjects);
		clazz = classDefinitionRepository.save(clazz);
		return classDefinitionMapper.toDTO(clazz);
	}
}
