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
import at.jku.cis.iVolunteer.marketplace.configurable.ConfigurableObjectRepository;
import at.jku.cis.iVolunteer.marketplace.configurable.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.property.PropertyRepository;
import at.jku.cis.iVolunteer.model.configurable.ConfigurableObject;
import at.jku.cis.iVolunteer.model.configurable.class_.ConfigurableClass;
import at.jku.cis.iVolunteer.model.configurable.class_.relationship.Association;
import at.jku.cis.iVolunteer.model.configurable.class_.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.configurable.class_.relationship.Relationship;
import at.jku.cis.iVolunteer.model.configurable.configurables.MatchingRule;
import at.jku.cis.iVolunteer.model.configurable.configurables.property.Property;
import at.jku.cis.iVolunteer.model.configurable.configurables.property.TextProperty;
import at.jku.cis.iVolunteer.model.property.dto.PropertyDTO;
import jersey.repackaged.com.google.common.collect.Lists;

@RestController
public class ConfigurableClassController {

	
	@Autowired ConfigurableClassRepository configurableClassRepository;
	@Autowired ConfigurableObjectRepository configurableObjectRepository;
	@Autowired RelationshipRepository relationshipRepository;
	
	@Autowired PropertyRepository propertyRepository;
	@Autowired PropertyMapper propertyMapper;
	

	/*
	 * Operations on ConfigurableClasses
	 */
	
	@GetMapping("/configclass/all")
	private List<ConfigurableClass> getAllConfigClasses() {
		System.out.println("/configclass/all");
		
		List<ConfigurableClass> ret =  configurableClassRepository.findAll();
		return ret;
		
	}
	
	@GetMapping("/configclass/{id}") 
	private ConfigurableClass getConfigClassByID(@PathVariable("id") String id) {
		System.out.println("/configclass/" + id);

		return configurableClassRepository.findOne(id);
	
	}
	
	@PostMapping("/configclass/new")
	private ConfigurableClass newConfigClass(@RequestBody ConfigurableClass configurableClass) {
		System.out.println("/configclass/new");

		return configurableClassRepository.save(configurableClass);
	}
	
	@PutMapping("/configclass/{id}/change-name") 
	private ConfigurableClass changeConfigClassName(@PathVariable("id") String id, @RequestBody String newName) {

		ConfigurableClass clazz = configurableClassRepository.findOne(id);
		clazz.setName(newName);
		return configurableClassRepository.save(clazz);
	}
	
	@PutMapping("/configclass/delete")
	private List<ConfigurableClass> deleteConfigClass(@RequestBody List<String> idsToRemove) {
		
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
	private ConfigurableClass addPropertiesToClassById(@PathVariable("id") String id, @RequestBody List<String> propIds) {
		ConfigurableClass clazz = configurableClassRepository.findOne(id);
		
		List<Property> props = Lists.newLinkedList(propertyRepository.findAll(propIds));
		
		clazz.getProperties().addAll(props);
		return configurableClassRepository.save(clazz);
	}
	
	
	@PutMapping("configclass/{id}/add-properties")
	private ConfigurableClass addPropertiesToClass(@PathVariable("id") String id, @RequestBody List<PropertyDTO<Object>> properties) {
		
		ConfigurableClass clazz = configurableClassRepository.findOne(id);
		
		List<Property> props= propertyMapper.toEntities(properties);
		
		clazz.getProperties().addAll(props);
		
		
		return configurableClassRepository.save(clazz);
	}
	
	
	@PutMapping("/configclass/{id}/remove-properties")
	private ConfigurableClass removeConfigObjectFromClass(@PathVariable("id") String id, @RequestBody List<String> idsToRemove) {
		
		System.out.println("/configclass/" + id + "/remove-objects");
		ConfigurableClass clazz = configurableClassRepository.findOne(id);
		
		
		ArrayList<Property> remainingObjects = clazz.getProperties().stream()
				.filter(c -> idsToRemove.stream().noneMatch(remId -> c.getId().equals(remId))).collect(Collectors.toCollection(ArrayList::new));
		
		clazz.setProperties(remainingObjects);

		
		return clazz;
		
	}
	

	
	
	
	
	

	
	
	
}
