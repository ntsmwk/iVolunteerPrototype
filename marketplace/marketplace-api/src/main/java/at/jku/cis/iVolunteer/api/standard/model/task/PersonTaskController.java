package at.jku.cis.iVolunteer.api.standard.model.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iVolunteer/PersonTasks")
public class PersonTaskController {

	@Autowired private PersonTaskService personTaskService;

	@PutMapping
	public void savePersonTask(List<PersonTask> tasks) {
		personTaskService.savePersonTasks(tasks);
	}
}