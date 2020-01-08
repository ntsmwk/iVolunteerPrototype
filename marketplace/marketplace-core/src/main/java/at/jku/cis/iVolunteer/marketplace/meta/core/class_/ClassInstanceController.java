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

import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassDefinitionToInstanceMapper;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@RestController
public class ClassInstanceController {

	@Autowired ClassInstanceRepository classInstanceRepository;

	@Autowired ClassDefinitionToInstanceMapper classDefinition2InstanceMapper;

	@Autowired private ClassDefinitionService classDefinitionService;

	@GetMapping("/meta/core/class/instance/all")
	private List<ClassInstance> getAllClassInstances() {
		return classInstanceRepository.findAll();
	}

	@GetMapping("/meta/core/class/instance/{id}")
	private ClassInstance getClassInstanceById(@PathVariable("id") String id) {
		return classInstanceRepository.findOne(id);
	}

	@GetMapping("/meta/core/class/instance/all/by-archetype/{archetype}")
	private List<ClassInstance> getClassInstancesByClassDefinitionId(
			@PathVariable("archetype") ClassArchetype archeType) {
		List<ClassInstance> classInstances = new ArrayList<>();
		List<ClassDefinition> classDefinitions = classDefinitionService.getClassDefinitionsByArchetype(archeType);

		for (ClassDefinition cd : classDefinitions) {
			classInstances.addAll(classInstanceRepository.getByClassDefinitionId(cd.getId()));
		}

		return classInstances;
	}

	@GetMapping("/meta/core/class/instance/by-userid/{userId}")
	private List<ClassInstance> getClassInstanceByUserId(@PathVariable("userId") String userId) {
		return classInstanceRepository.getByUserId(userId);
	}

	@GetMapping("/meta/core/class/instance/by-userid/{userId}/inbox")
	private List<ClassInstance> getClassInstanceByUserIdInInbox(@PathVariable("userId") String userId) {
		return classInstanceRepository.getByUserIdAndInRepository(userId, false);
	}

	@GetMapping("/meta/core/class/instance/by-userid/{userId}/repository")
	private List<ClassInstance> getClassInstanceByUserIdInRepostory(@PathVariable("userId") String userId) {
		return classInstanceRepository.getByUserIdAndInRepository(userId, true);
	}

	@PutMapping("/meta/core/class/instance/set-inRepository-state/{inRepository}")
	private List<ClassInstance> setClassInstancesInRepository(@PathVariable("inRepository") boolean inRepository,
			@RequestBody List<String> classInstanceIds) {
		List<ClassInstance> classInstances = new ArrayList<>();
		classInstanceRepository.findAll(classInstanceIds).forEach(classInstances::add);

		for (ClassInstance classInstance : classInstances) {
			classInstance.setInRepository(true);
		}

		return classInstanceRepository.save(classInstances);
	}

	@PostMapping("/meta/core/class/instance/new")
	List<ClassInstance> createNewClassInstances(@RequestBody List<ClassInstance> classInstances) {

		// TODO split into different archetypes
		return classInstanceRepository.save(classInstances);

	}

	@PostMapping("/meta/core/class/instance/{id}/new")
	private ClassInstance createNewClassInstanceById() {
		// TODO
		return null;
	}

	@PutMapping("/meta/core/class/instance/{id}/update")
	private ClassInstance updateClassInstance() {
		// TODO
		return null;
	}

	@DeleteMapping("/meta/core/class/instance/delete")
	private void deleteClassInstance() {
		// TODO
	}

}
