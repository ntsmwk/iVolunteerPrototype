package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.form.EnumEntry;
import at.jku.cis.iVolunteer.model.meta.form.FormConfiguration;

@RestController
public class ClassDefinitionController {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;

	@Autowired private ClassDefinitionService classDefinitionService;

	@GetMapping("/meta/core/class/definition/all")
	private List<ClassDefinition> getAllClassDefinitions() {
		return classDefinitionRepository.findAll();
	}

	@GetMapping("meta/core/class/definition/all/no-enum")
	public List<ClassDefinition> getAllClassDefinitionsWithoutEnums(
			@RequestParam(value = "org", required = false) String organisation) {
		return classDefinitionService.getAllClassDefinitionsWithoutEnums(organisation);
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

	@PutMapping("meta/core/class/definition/get-children")
	private List<String> getChildrenById(@RequestBody List<String> rootIds) {
		return classDefinitionService.getChildrenById(rootIds);
	}

	@PutMapping("meta/core/class/definition/get-parents")
	private List<FormConfiguration> getParentsById(@RequestBody List<String> childIds) {
		return classDefinitionService.getParentsById(childIds);
	}

	@GetMapping("meta/core/class/definition/enum-values/{classDefinitionId}")
	public List<EnumEntry> getEnumValues(@PathVariable("classDefinitionId") String classDefinitionId) {
		return classDefinitionService.getEnumValues(classDefinitionId);
	}

}