package at.jku.cis.iVolunteer.marketplace.configurable.class_;

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

import at.jku.cis.iVolunteer.mapper.property.PropertyMapper;
import at.jku.cis.iVolunteer.marketplace.configurable.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.property.PropertyRepository;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.Property;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.TextProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.dto.PropertyDTO;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.matching.MatchingRule;
import jersey.repackaged.com.google.common.collect.Lists;

@RestController
public class ConfigurableClassController {

	
	@Autowired ConfigurableClassRepository configurableClassRepository;
//	@Autowired ConfigurableObjectRepository configurableObjectRepository;
	@Autowired RelationshipRepository relationshipRepository;
	
	@Autowired PropertyRepository propertyRepository;
	@Autowired PropertyMapper propertyMapper;
	

	/*
	 * Operations on ConfigurableClasses
	 */
	
	@GetMapping("/configclass/all")
	private List<ClassDefinition> getAllConfigClasses() {
		System.out.println("/configclass/all");
		
		List<ClassDefinition> ret =  configurableClassRepository.findAll();
		return ret;
		
	}
	
	@GetMapping("/configclass/{id}") 
	private ClassDefinition getConfigClassByID(@PathVariable("id") String id) {
		System.out.println("/configclass/" + id);

		return configurableClassRepository.findOne(id);
	
	}
	
	@PostMapping("/configclass/new")
	private ClassDefinition newConfigClass(@RequestBody ClassDefinition configurableClass) {
		System.out.println("/configclass/new");

		return configurableClassRepository.save(configurableClass);
	}
	
	@PutMapping("/configclass/{id}/change-name") 
	private ClassDefinition changeConfigClassName(@PathVariable("id") String id, @RequestBody String newName) {

		ClassDefinition clazz = configurableClassRepository.findOne(id);
		clazz.setName(newName);
		return configurableClassRepository.save(clazz);
	}
	
	@PutMapping("/configclass/delete")
	private List<ClassDefinition> deleteConfigClass(@RequestBody List<String> idsToRemove) {
		
		System.out.println("/configclass/delete");

		for (String id : idsToRemove) {
			configurableClassRepository.delete(id);
		}
		
		return configurableClassRepository.findAll();
		
	}


	/*
	 * Operations on Properties
	 */
	
	@PutMapping("configclass/{id}/add-properties-by-id")
	private ClassDefinition addPropertiesToClassById(@PathVariable("id") String id, @RequestBody List<String> propIds) {
		ClassDefinition clazz = configurableClassRepository.findOne(id);
		
		List<Property> props = Lists.newLinkedList(propertyRepository.findAll(propIds));
		
		clazz.getProperties().addAll(props);
		return configurableClassRepository.save(clazz);
	}
	
	
	@PutMapping("configclass/{id}/add-properties")
	private ClassDefinition addPropertiesToClass(@PathVariable("id") String id, @RequestBody List<PropertyDTO<Object>> properties) {
		
		ClassDefinition clazz = configurableClassRepository.findOne(id);
		
		List<Property> props= propertyMapper.toEntities(properties);
		
		clazz.getProperties().addAll(props);
		
		
		return configurableClassRepository.save(clazz);
	}
	
	
	@PutMapping("/configclass/{id}/remove-properties")
	private ClassDefinition removeConfigObjectFromClass(@PathVariable("id") String id, @RequestBody List<String> idsToRemove) {
		
		System.out.println("/configclass/" + id + "/remove-objects");
		ClassDefinition clazz = configurableClassRepository.findOne(id);
		
		
		ArrayList<Property> remainingObjects = clazz.getProperties().stream()
				.filter(c -> idsToRemove.stream().noneMatch(remId -> c.getId().equals(remId))).collect(Collectors.toCollection(ArrayList::new));
		
		clazz.setProperties(remainingObjects);

		
		return clazz;
		
	}
	

	
	
	
	
	

	
	
	
}
