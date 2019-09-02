package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassDefinitionMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.property.ClassPropertyMapper;
import at.jku.cis.iVolunteer.mapper.property.PropertyMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.property.PropertyRepository;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.class_.dtos.ClassDefinitionDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.ClassPropertyDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.Property;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.dto.PropertyDTO;
import jersey.repackaged.com.google.common.collect.Lists;

@RestController
public class ClassDefinitionController {

	
	@Autowired ClassDefinitionRepostiory classDefinitionRepository;
	@Autowired RelationshipRepository relationshipRepository;
	
	@Autowired PropertyRepository propertyRepository;
	@Autowired PropertyDefinitionRepository propertyDefinitionRepository;
	
	@Autowired ClassPropertyMapper classPropertyMapper;
	@Autowired PropertyMapper propertyMapper;
	

	/*
	 * Operations on ConfigurableClasses
	 */
	
	@GetMapping("/meta/core/class/definition/all")
	private List<ClassDefinition> getAllClassDefinitions() {
		System.out.println("/configclass/all");
		
		List<ClassDefinition> ret =  classDefinitionRepository.findAll();
		return ret;
		
	}
	
	@GetMapping("/meta/core/class/definition/{id}") 
	private ClassDefinition getClassDefinitionById(@PathVariable("id") String id) {
		System.out.println("/configclass/" + id);

		return classDefinitionRepository.findOne(id);
	
	}
	
	@PostMapping("/meta/core/class/definition/new")
	private ClassDefinition newClassDefinition(@RequestBody ClassDefinition configurableClass) {
		System.out.println("/configclass/new");

		return classDefinitionRepository.save(configurableClass);
	}
	
	@PutMapping("/meta/core/class/definition/{id}/change-name") 
	private ClassDefinition changeClassDefinitionName(@PathVariable("id") String id, @RequestBody String newName) {

		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		clazz.setName(newName);
		return classDefinitionRepository.save(clazz);
	}
	
	@PutMapping("/meta/core/class/definition/delete")
	private List<ClassDefinition> deleteClassDefinition(@RequestBody List<String> idsToRemove) {
		
		System.out.println("/configclass/delete");

		for (String id : idsToRemove) {
			classDefinitionRepository.delete(id);
		}
		
		return classDefinitionRepository.findAll();
		
	}
	
	@Autowired ClassDefinitionMapper classDefinitionMapper;
	//LEGACY
	@PutMapping("/meta/core/class/definition/{id}/save-properties-legacy")
	private ClassDefinitionDTO legacysave(@PathVariable("id") String id, @RequestBody List<PropertyDTO<Object>> properties) {
		System.out.println("legacysave");
		ClassDefinition def = this.classDefinitionRepository.findOne(id);
		
		if (def == null) {
			return null;
		}
		def.setProperties(propertyMapper.toEntities(properties));
		return classDefinitionMapper.toDTO(this.classDefinitionRepository.save(def));
	}
	
	
	
	
	//------


	/*
	 * Operations on Properties
	 */
	
//	@PutMapping("meta/core/class/definition/{id}/add-properties-by-id")
//	private ClassDefinition addPropertiesToClassDefinitionById(@PathVariable("id") String id, @RequestBody List<String> propIds) {
//		ClassDefinition clazz = classDefinitionRepository.findOne(id);
//		
//		List<PropertyDefinition<Object>> dProps = Lists.newLinkedList(propertyDefinitionRepository.findAll(propIds));
//		
//		List<ClassProperty<Object>> cProps = createClassPropertiesFromDefinitions(dProps);
//		
//		clazz.getProperties().addAll(cProps);
//		return classDefinitionRepository.save(clazz);
//	}
//	
//	
//	@PutMapping("meta/core/class/definition/{id}/add-properties")
//	private ClassDefinition addPropertiesToClassDefinition(@PathVariable("id") String id, @RequestBody List<ClassPropertyDTO<Object>> properties) {
//		
//		ClassDefinition clazz = classDefinitionRepository.findOne(id);
//		
//		List<ClassProperty<Object>> props = classPropertyMapper.toEntities(properties);
//
//		clazz.getProperties().addAll(props);
//		
//		
//		return classDefinitionRepository.save(clazz);
//	}
	
	private List<ClassProperty<Object>> createClassPropertiesFromDefinitions(List<PropertyDefinition<Object>> propertyDefinitions) {
		List<ClassProperty<Object>> cProps = new ArrayList<>();
		
		for (PropertyDefinition<Object> d : propertyDefinitions) {
			cProps.add(new ClassProperty<Object>(d));
		}
		
		return cProps;
	}
	
	
//	@PutMapping("/meta/core/class/definition/{id}/remove-properties")
//	private ClassDefinition removePropertiesFromClassDefinition(@PathVariable("id") String id, @RequestBody List<String> idsToRemove) {
//		
//		System.out.println("/configclass/" + id + "/remove-objects");
//		ClassDefinition clazz = classDefinitionRepository.findOne(id);
//		
//		
//		ArrayList<ClassProperty<Object>> remainingObjects = clazz.getProperties().stream()
//				.filter(c -> idsToRemove.stream().noneMatch(remId -> c.getId().equals(remId))).collect(Collectors.toCollection(ArrayList::new));
//		
//		clazz.setProperties(remainingObjects);
//
//		
//		return clazz;
//		
//	}
	

	
	
	
	
	

	
	
	
}
