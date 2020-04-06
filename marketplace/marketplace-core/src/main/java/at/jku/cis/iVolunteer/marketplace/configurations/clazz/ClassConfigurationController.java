package at.jku.cis.iVolunteer.marketplace.configurations.clazz;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.configurations.matching.collector.MatchingCollectorConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.fake.IsSunburstFakeDocument;
import at.jku.cis.iVolunteer.marketplace.fake.IsSunburstFakeRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.CollectionService;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.configurations.matching.collector.MatchingCollectorConfiguration;
import at.jku.cis.iVolunteer.model.matching.MatchingCollector;

@RestController
public class ClassConfigurationController {

	@Autowired private ClassConfigurationRepository classConfigurationRepository;
	@Autowired private CollectionService collectionService;
	
	@Autowired private MatchingCollectorConfigurationRepository matchingCollectorConfigurationRepository;
	
	
	
	@GetMapping("class-configuration/all")
	List<ClassConfiguration> getAllClassConfigurations() {
		return classConfigurationRepository.findAll();
	}
	
	@GetMapping("class-configuration/all/sort")
	List<ClassConfiguration> getAllClassConfigurationsWithSort(@RequestParam(value = "sorted", required = false) String sortType) {
		
//		if (sortType.equalsIgnoreCase("asc")) {
//			return configuratorRepository.findAllWithSort(new Sort(Sort.Direction.ASC, "date"));
//
//		} else if (sortType.equalsIgnoreCase("desc")) {
//			return configuratorRepository.findAllWithSort(new Sort(Sort.Direction.DESC, "date"));
//		} 
		
		
		
		List<ClassConfiguration> classConfigurations =  classConfigurationRepository.findAllWithSort(new Sort(Sort.Direction.ASC, "name"));
		return classConfigurations;
	}
	
	@GetMapping("class-configuration/{id}")
	ClassConfiguration getClassConfigurationById(@PathVariable("id") String id) {
		return classConfigurationRepository.findOne(id);
	}
	
	@GetMapping("class-configuration/by-name/{name}")
	List<ClassConfiguration> getClassConfigurationByName(@PathVariable("name") String name) {
		return classConfigurationRepository.findByName(name);
	}
	
	@PostMapping("class-configuration/new-empty")
	ClassConfiguration createNewEmptyClassConfiguration(@RequestBody String[] params) {
		if (params.length != 2) {
			return null;
		}
		
		ClassConfiguration classConfiguration = new ClassConfiguration();
		classConfiguration.setName(params[0]);
		classConfiguration.setDescription(params[1]);
		classConfiguration.setTimestamp(new Date());
		
		return saveClassConfiguration(classConfiguration);
	}
	
	@PostMapping("class-configuration/new")
	ClassConfiguration createNewClassConfiguration(@RequestBody ClassConfiguration newClassConfiguration) {
		return saveClassConfiguration(newClassConfiguration);
	}
	
	@PutMapping("class-configuration/save")
	public ClassConfiguration saveClassConfiguration(@RequestBody ClassConfiguration updatedClassConfiguration) {
		updatedClassConfiguration.setTimestamp(new Date());
		
		ClassConfiguration classConfiguration = classConfigurationRepository.save(updatedClassConfiguration);
	
		//TODO aggregate and build 
		List<MatchingCollector> collectors = collectionService.collectAllClassDefinitionsWithPropertiesAsMultipleCollections(classConfiguration.getId());
		
		MatchingCollectorConfiguration matchingCollectorConfiguration = new MatchingCollectorConfiguration();
		matchingCollectorConfiguration.setId(classConfiguration.getId());
		matchingCollectorConfiguration.setClassConfigurationId(classConfiguration.getId());
		matchingCollectorConfiguration.setCollectors(collectors);
		
		matchingCollectorConfigurationRepository.save(matchingCollectorConfiguration);
		
		return classConfiguration;
	}
	
	@DeleteMapping("class-configuration/{id}/delete")
	void deleteClassConfiguration(@PathVariable("id") String id) {
		classConfigurationRepository.delete(id);
	}
	
	@PutMapping("class-configuration/delete-multiple")
	List<ClassConfiguration> deleteMultipleClassConfigurations(@RequestBody List<String> ids) {
		ids.forEach(this.classConfigurationRepository::delete);
		return this.classConfigurationRepository.findAll();
	}
	
	
}
