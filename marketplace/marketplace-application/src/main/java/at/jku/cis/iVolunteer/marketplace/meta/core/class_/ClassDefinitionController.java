package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.class_.dtos.ClassDefinitionDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.ClassPropertyDTO;
import jersey.repackaged.com.google.common.collect.Lists;

@RestController
public class ClassDefinitionController {

	
	@Autowired ClassDefinitionRepostiory classDefinitionRepository;
	@Autowired RelationshipRepository relationshipRepository;
	
	@Autowired PropertyDefinitionRepository propertyDefinitionRepository;
	
	@Autowired ClassPropertyMapper classPropertyMapper;
	@Autowired ClassDefinitionMapper classDefinitionMapper;
	@Autowired PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	

	/*
	 * Operations on ConfigurableClasses
	 */
	
	@GetMapping("/meta/core/class/definition/all")
	private List<ClassDefinitionDTO> getAllClassDefinitions() {
		System.out.println("/configclass/all");
		
		List<ClassDefinitionDTO> ret =  classDefinitionMapper.toDTOs(classDefinitionRepository.findAll());
		return ret;
		
	}
	
	@GetMapping("/meta/core/class/definition/{id}") 
	private ClassDefinitionDTO getClassDefinitionById(@PathVariable("id") String id) {
		System.out.println("/configclass/" + id);

		return classDefinitionMapper.toDTO(classDefinitionRepository.findOne(id));
	
	}
	
	@PutMapping("/meta/core/class/definition/multiple")
	private List<ClassDefinitionDTO> getClassDefinitonsById(@RequestBody List<String> ids) {
		System.out.println("abc");
		List<ClassDefinition> classDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(ids).forEach(classDefinitions::add);
		return classDefinitionMapper.toDTOs(classDefinitions);
	}
	
	@PostMapping("/meta/core/class/definition/new")
	private ClassDefinitionDTO newClassDefinition(@RequestBody ClassDefinitionDTO classDefinition) {
		System.out.println("/configclass/new");

		return classDefinitionMapper.toDTO(classDefinitionRepository.save(classDefinitionMapper.toEntity(classDefinition)));
	}
	
	@PutMapping("/meta/core/class/definition/{id}/change-name") 
	private ClassDefinitionDTO changeClassDefinitionName(@PathVariable("id") String id, @RequestBody String newName) {

		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		clazz.setName(newName);
		return classDefinitionMapper.toDTO(classDefinitionRepository.save(clazz));
	}
	
	@PutMapping("/meta/core/class/definition/delete")
	private List<ClassDefinitionDTO> deleteClassDefinition(@RequestBody List<String> idsToRemove) {
		
		System.out.println("/configclass/delete");

		for (String id : idsToRemove) {
			classDefinitionRepository.delete(id);
		}
		
		return classDefinitionMapper.toDTOs(classDefinitionRepository.findAll());
		
	}
	
	@PutMapping("/meta/core/class/definition/add-or-update")
	private List<ClassDefinitionDTO> addOrUpdateClassDefinitions(@RequestBody List<ClassDefinitionDTO> classDefinitions) {
		
		System.out.println("add-or-update ClassDefinitions");
		System.out.println("ClassDefinitions #" + classDefinitions.size());
//		this.classDefinitionRepository.deleteAll();
		
		return this.classDefinitionMapper.toDTOs(this.classDefinitionRepository.save(classDefinitionMapper.toEntities(classDefinitions)));
				
	}
	
	
	
	//LEGACY
//	@PutMapping("/meta/core/class/definition/{id}/save-properties-legacy")
//	private ClassDefinitionDTO legacysave(@PathVariable("id") String id, @RequestBody List<PropertyDTO<Object>> properties) {
//		System.out.println("legacysave");
//		ClassDefinition def = this.classDefinitionRepository.findOne(id);
//		
//		if (def == null) {
//			return null;
//		}
//		def.setProperties(propertyMapper.toEntities(properties));
//		return classDefinitionMapper.toDTO(this.classDefinitionRepository.save(def));
//	}
	
	
	
	
	//------


	/*
	 * Operations on Properties
	 */
	
	@PutMapping("meta/core/class/definition/get-classproperty-from-propertydefinition-by-id")
	private List<ClassPropertyDTO<Object>> getClassPropertyFromPropertyDefinitionById(@RequestBody List<String> propIds) {
		List<PropertyDefinition<Object>> dProps = Lists.newLinkedList(propertyDefinitionRepository.findAll(propIds));
		List<ClassProperty<Object>> cProps = createClassPropertiesFromDefinitions(dProps);
		return classPropertyMapper.toDTOs(cProps);
	}
	
	@PutMapping("meta/core/class/definition/{id}/add-properties-by-id")
	private List<ClassPropertyDTO<Object>> addPropertiesToClassDefinitionById(@PathVariable("id") String id, @RequestBody List<String> propIds) {
		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		
		List<PropertyDefinition<Object>> dProps = Lists.newLinkedList(propertyDefinitionRepository.findAll(propIds));
		
		List<ClassProperty<Object>> cProps = createClassPropertiesFromDefinitions(dProps);
		
		if (clazz.getProperties() == null) {
			clazz.setProperties(new ArrayList<>());
		}
		
		clazz.getProperties().addAll(cProps);
		clazz = classDefinitionRepository.save(clazz);
		return classPropertyMapper.toDTOs(clazz.getProperties());
	}
	
	
	@PutMapping("meta/core/class/definition/{id}/add-properties")
	private List<ClassPropertyDTO<Object>> addPropertiesToClassDefinition(@PathVariable("id") String id, @RequestBody List<ClassPropertyDTO<Object>> properties) {
		
		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		
		List<ClassProperty<Object>> props = classPropertyMapper.toEntities(properties);
		clazz.getProperties().addAll(props);
		clazz = classDefinitionRepository.save(clazz);
		return classPropertyMapper.toDTOs(clazz.getProperties());
	}
	
	private List<ClassProperty<Object>> createClassPropertiesFromDefinitions(List<PropertyDefinition<Object>> propertyDefinitions) {
		List<ClassProperty<Object>> cProps = new ArrayList<>();
		
		for (PropertyDefinition<Object> d : propertyDefinitions) {
			cProps.add(propertyDefinitionToClassPropertyMapper.toTarget(d));
		}
		
		return cProps;
	}
	
	
	@PutMapping("/meta/core/class/definition/{id}/remove-properties")
	private ClassDefinitionDTO removePropertiesFromClassDefinition(@PathVariable("id") String id, @RequestBody List<String> idsToRemove) {
		
		System.out.println("/configclass/" + id + "/remove-objects");
		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		
		
		ArrayList<ClassProperty<Object>> remainingObjects = clazz.getProperties().stream()
				.filter(c -> idsToRemove.stream().noneMatch(remId -> c.getId().equals(remId))).collect(Collectors.toCollection(ArrayList::new));
		
		clazz.setProperties(remainingObjects);

		clazz = classDefinitionRepository.save(clazz);
		
		
		return classDefinitionMapper.toDTO(clazz);
		
	}
	

	
	
	
	
	

	
	
	
}
