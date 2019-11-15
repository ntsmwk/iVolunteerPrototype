package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassDefinitionMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.meta.core.class_.dtos.ClassDefinitionDTO;

@RestController
public class ClassDefinitionController {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private ClassDefinitionMapper classDefinitionMapper;

	@Autowired private ClassDefinitionService classDefinitionService;

	@GetMapping("/meta/core/class/definition/all")
	private List<ClassDefinitionDTO> getAllClassDefinitions() {
		return classDefinitionMapper.toDTOs(classDefinitionRepository.findAll());
	}

	@GetMapping("/meta/core/class/definition/{id}")
	private ClassDefinitionDTO getClassDefinitionById(@PathVariable("id") String id) {
		return classDefinitionService.getClassDefinitionById(id);
	}

	@PutMapping("/meta/core/class/definition/multiple")
	private List<ClassDefinitionDTO> getClassDefinitonsById(@RequestBody List<String> ids) {
		return classDefinitionService.getClassDefinitonsById(ids);
	}

	@PostMapping("/meta/core/class/definition/new")
	private ClassDefinitionDTO newClassDefinition(@RequestBody ClassDefinitionDTO classDefinition) {
		return classDefinitionService.newClassDefinition(classDefinition);
	}

	@PutMapping("/meta/core/class/definition/{id}/change-name")
	private ClassDefinitionDTO changeClassDefinitionName(@PathVariable("id") String id, @RequestBody String newName) {
		return classDefinitionService.changeClassDefinitionName(id, newName);
	}

	@PutMapping("/meta/core/class/definition/delete")
	private List<ClassDefinitionDTO> deleteClassDefinition(@RequestBody List<String> idsToRemove) {
		return classDefinitionService.deleteClassDefinition(idsToRemove);
	}

	@PutMapping("/meta/core/class/definition/add-or-update")
	private List<ClassDefinitionDTO> addOrUpdateClassDefinitions(
			@RequestBody List<ClassDefinitionDTO> classDefinitions) {
		return classDefinitionService.addOrUpdateClassDefinitions(classDefinitions);
	}

	// TODO @Alex ????
//	@PutMapping("meta/core/class/definition/get-children")
//	private List<String> getChildrenById(@RequestBody List<String> rootIds) {
//		List<ClassDefinition> rootClassDefintions = new ArrayList<ClassDefinition>();
//		classDefinitionRepository.findAll(rootIds).forEach(rootClassDefintions::add);
//
//		List<String> returnIds;
//		for (ClassDefinition rootClassDefinitions : rootClassDefintions) {
//
//		}
//
//		return null;
//	}

	@PutMapping("meta/core/class/defintiion/get-parents")
	private List<String> getParentsById(@RequestBody List<String> childIds) {
		return classDefinitionService.getParentsById(childIds);
	}

}
