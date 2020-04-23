package at.jku.cis.iVolunteer.marketplace.meta.configurator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyDefinitionToPropertyInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.PropertyDefinitionRepository;
import at.jku.cis.iVolunteer.model.meta.configurator.Configurator;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;

@RestController
public class ConfiguratorController {

	@Autowired private ConfiguratorRepository configuratorRepository;
	@Autowired private PropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;
	
	@GetMapping("meta/configurator/all")
	List<Configurator> getAllConfigurators() {
		List<Configurator> configurators =  configuratorRepository.findAllWithSort(new Sort(Sort.Direction.ASC, "name"));
		return configurators;
	}
	
	@GetMapping("meta/configurator/{id}")
	Configurator getConfiguratorById(@PathVariable("id") String id) {
		return configuratorRepository.findOne(id);
	}
	
	@GetMapping("meta/configurator/by-name/{name}")
	List<Configurator> getConfiguratorByName(@PathVariable("name") String name) {
		return configuratorRepository.findByName(name);
	}
	
	@PostMapping("meta/configurator/new-empty")
	Configurator createNewEmptyConfigurator(@RequestBody String[] params) {
		if (params.length != 2) {
			return null;
		}
		
		Configurator c = new Configurator();
		c.setName(params[0]);
		c.setDescription(params[1]);
		
		return configuratorRepository.save(c);
	}
	
	@GetMapping("meta/configurator/new/")
	Configurator createNewConfigurator() {
		return null;
		
	}
	
	@PutMapping("meta/configurator/save")
	Configurator saveConfigurator(@RequestBody Configurator updatedConfigurator) {
		return configuratorRepository.save(updatedConfigurator);
	}
	
	@DeleteMapping("meta/configurator/{id}/delete")
	void deleteConfigurator(@PathVariable("id") String id) {
		configuratorRepository.delete(id);
	}
	
	
	
}
