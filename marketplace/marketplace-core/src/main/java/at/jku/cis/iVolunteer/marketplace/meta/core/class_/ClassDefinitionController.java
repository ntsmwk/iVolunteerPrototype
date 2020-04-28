package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.matching.MatchingCollector;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.form.EnumEntry;
import at.jku.cis.iVolunteer.model.meta.form.FormConfiguration;
import at.jku.cis.iVolunteer.model.meta.form.FormConfigurationPreviewRequest;

@RestController
public class ClassDefinitionController {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;

	@Autowired private ClassDefinitionService classDefinitionService;

	@Autowired private CollectionService collectionService;

	@GetMapping("/meta/core/class/definition/all")
	private List<ClassDefinition> getAllClassDefinitions() {
		return classDefinitionRepository.findAll();
	}

	@GetMapping("meta/core/class/definition/all/no-enum")
	public List<ClassDefinition> getAllClassDefinitionsWithoutEnums(
			@RequestParam(value = "org", required = false) String organisation) {
		return classDefinitionService.getAllClassDefinitionsWithoutEnums(organisation);
	}

	@GetMapping("meta/core/class/definition/{slotId}/with-properties")
	private List<ClassDefinition> getClassDefinitionsWithProperties(@PathVariable("slotId") String slotId) {
		return classDefinitionService.getAllClassDefinitionsWithProperties(slotId);
	}

	@GetMapping("/meta/core/class/definition/{id}")
	private ClassDefinition getClassDefinitionById(@PathVariable("id") String id) {
		return classDefinitionService.getClassDefinitionById(id);
	}

	@GetMapping("/meta/core/class/definition/archetype/{archetype}")
	public List<ClassDefinition> getClassDefinitionByArchetype(@PathVariable("archetype") ClassArchetype archetype,
			@RequestParam(value = "org", required = false) String organisation) {
		return classDefinitionService.getClassDefinitionsByArchetype(archetype, organisation);
	}

	@PutMapping("/meta/core/class/definition/multiple")
	private List<ClassDefinition> getClassDefinitonsById(@RequestBody List<String> ids) {
		return classDefinitionService.getClassDefinitonsById(ids);
	}

	@PostMapping("/meta/core/class/definition/new")
	private ClassDefinition newClassDefinition(@RequestBody ClassDefinition classDefinition) {
		return classDefinitionService.newClassDefinition(classDefinition);
	}

	@PutMapping("/meta/core/class/definition/{id}/change-name")
	private ClassDefinition changeClassDefinitionName(@PathVariable("id") String id, @RequestBody String newName) {
		return classDefinitionService.changeClassDefinitionName(id, newName);
	}

	@PutMapping("/meta/core/class/definition/delete")
	private List<ClassDefinition> deleteClassDefinition(@RequestBody List<String> idsToRemove) {
		return classDefinitionService.deleteClassDefinition(idsToRemove);
	}

	@PutMapping("/meta/core/class/definition/add-or-update")
	private List<ClassDefinition> addOrUpdateClassDefinitions(@RequestBody List<ClassDefinition> classDefinitions) {
		return classDefinitionService.addOrUpdateClassDefinitions(classDefinitions);
	}

//	@PutMapping("meta/core/class/definition/get-children")
//	private List<FormConfiguration> getChildrenById(@RequestBody List<String> rootIds) {
//		return classDefinitionService.getChildrenById(rootIds);
//	}
//
//	@PutMapping("meta/core/class/definition/get-parents")
//	private List<FormConfiguration> getParentsById(@RequestBody List<String> childIds) {
//		return classDefinitionService.getParentsById(childIds);
//	}

	@PutMapping("meta/core/class/definition/form-configuration")
	private List<FormConfiguration> getFormConfigurations(@RequestBody List<String> ids,
			@RequestParam(value = "type") String collectionType) {
		if (collectionType.equals("top-down")) {
			return classDefinitionService.aggregateChildrenById(ids);
		} else if (collectionType.equals("bottom-up")) {
			return classDefinitionService.getParentsById(ids);
		} else {
			throw new IllegalArgumentException("Invalid collection type - has to be 'top-down' or 'bottom-up'");
		}
	}

	@PutMapping("meta/core/class/definition/form-configuration-preview")
	private List<FormConfiguration> getFormConfigurationPreview(@RequestBody FormConfigurationPreviewRequest request) {
		List<FormConfiguration> ret = classDefinitionService.aggregateChildren(request.getClassDefinitions(), request.getRelationships());
		System.out.println("ret");
		System.out.println(ret.get(0).getFormEntry().getSubEntries().size());
		
		return ret;
	}

	@GetMapping("meta/core/class/definition/enum-values/{classDefinitionId}")
	public List<EnumEntry> getEnumValues(@PathVariable("classDefinitionId") String classDefinitionId) {
		return collectionService.aggregateEnums(classDefinitionId);
	}

}