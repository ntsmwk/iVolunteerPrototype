package at.jku.cis.iVolunteer.marketplace.configurator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.Slice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.configurable.ConfigurableObject;
import at.jku.cis.iVolunteer.model.configurable.class_.ConfigurableClass;
import at.jku.cis.iVolunteer.model.configurable.configurables.MatchingRule;
import at.jku.cis.iVolunteer.model.configurable.configurables.Relationship;
import at.jku.cis.iVolunteer.model.configurable.configurables.property.Property;
import at.jku.cis.iVolunteer.model.configurable.configurables.property.TextProperty;

@RestController
public class ConfiguratorController {

	
	@Autowired ConfigurableClassRepository configurableClassRepository;
	@Autowired ConfigurableObjectRepository configurableObjectRepository;
	
	
	
	@GetMapping("/configurator/test")
	public void test() {
		ConfigurableObject o = new ConfigurableObject();
		Property p = new TextProperty();
		Relationship r = new Relationship();
		MatchingRule m = new MatchingRule(); 
		
		this.configurableObjectRepository.save(o);
		this.configurableObjectRepository.save(p);
		this.configurableObjectRepository.save(r);
		this.configurableObjectRepository.save(m);
	
	
	
	}
	
	@GetMapping("/configclass/all")
	private List<ConfigurableClass> getAllConfigClasses() {
		System.out.println("/configclass/all");
		return configurableClassRepository.findAll();
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
	
	@PutMapping("/configclass/{id}/add-objects")
	private ConfigurableClass addConfigObjectToClass(@PathVariable("id") String id, @RequestBody List<ConfigurableObject> objectsToAdd) {
		ConfigurableClass clazz = configurableClassRepository.findOne(id);

		System.out.println("/configclass/" + id + "/add-objects");

		//TODO add
		
		
		
		return clazz;
	}
	
	@PutMapping("/configclass/{id}/remove-objects")
	private ConfigurableClass removeConfigObjectFromClass(@PathVariable("id") String id, @RequestBody List<String> idsToRemove) {
		ConfigurableClass clazz = configurableClassRepository.findOne(id);
		
		//TODO remove
		
		System.out.println("/configclass/" + id + "/remove-objects");

		
		return clazz;
		
	}
	
	@DeleteMapping("/configclass/{id}/delete")
	private void deleteConfigClass(@PathVariable("id") String id) {
		
		System.out.println("/configclass/" + id + "/delete");

		configurableClassRepository.delete(id);
		
	}
	

	
	
	
}
