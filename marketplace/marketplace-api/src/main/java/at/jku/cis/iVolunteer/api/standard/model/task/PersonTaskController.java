package at.jku.cis.iVolunteer.api.standard.model.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iVolunteer/PersonTasks")
public class PersonTaskController {

	@Autowired
	private PersonTaskRepository personTaskRepository;

	@GetMapping()
	public List<PersonTask> getPersonTasks() {
		return personTaskRepository.findAll();
	}

	@GetMapping("/{ID}")
	public List<PersonTask> getPersonTasks(@PathVariable String ID) {
		return personTaskRepository.findByPersonID(ID);
	}
}