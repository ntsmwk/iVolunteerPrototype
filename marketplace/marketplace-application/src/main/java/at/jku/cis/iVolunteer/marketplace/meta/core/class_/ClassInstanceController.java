package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassDefinitionMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassDefinitionToInstanceMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassInstanceMapper;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.class_.dtos.ClassDefinitionDTO;
import at.jku.cis.iVolunteer.model.meta.core.class_.dtos.ClassInstanceDTO;

@Controller
public class ClassInstanceController {

	@Autowired ClassInstanceRepository classInstanceRepository;
	@Autowired ClassInstanceMapper classInstanceMapper;
	@Autowired ClassDefinitionMapper classDefinitionMapper;

	@Autowired ClassDefinitionToInstanceMapper classDefinition2InstanceMapper;

	@GetMapping("/meta/core/class/instance/all")
	private List<ClassInstanceDTO> getAllClassInstances() {
		return classInstanceMapper.toDTOs(classInstanceRepository.findAll());
	}

	@GetMapping("/meta/core/class/instance/{id}")
	private ClassInstanceDTO getClassInstanceById(@PathVariable("id") String id) {
		return classInstanceMapper.toDTO(classInstanceRepository.findOne(id));
	}

	@PostMapping("/meta/core/class/instance/new")
	private ClassInstanceDTO createNewClassInstance(@RequestBody ClassDefinitionDTO classDefinitionDTO) {
		ClassDefinition classDefinition = classDefinitionMapper.toEntity(classDefinitionDTO);

		// TODO create instances from from bottom to top
		ClassInstance classInstance = classDefinition2InstanceMapper.toTarget(classDefinition);
		return classInstanceMapper.toDTO(classInstance);
	}

	@PostMapping("/meta/core/class/instance/{id}/new")
	private ClassInstanceDTO createNewClassInstanceById() {
		// TODO
		return null;
	}

	@PutMapping("/meta/core/class/instance/{id}/update")
	private ClassInstanceDTO updateClassInstance() {
		// TODO
		return null;
	}

	@DeleteMapping("/meta/core/class/instance/delete")
	private void deleteClassInstance() {
		// TODO
	}

}
