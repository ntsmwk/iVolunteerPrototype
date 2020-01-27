package at.jku.cis.iVolunteer.api.standard.model.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/standard/PersonTasks")
public class PersonTaskController {

	private static final int MV = 3;
	private static final int FF_NEW = 2;
	private static final int FF_OLD = 1;
	@Autowired private PersonTaskService personTaskService;

	@PutMapping("/1")
	public void savePersonTask(@RequestBody List<PersonTask> tasks) {
		personTaskService.savePersonTasks(tasks, FF_OLD);
	}
	

	@PutMapping("/2")
	public void savePersonTask2(@RequestBody List<PersonTask> tasks) {
		personTaskService.savePersonTasks(tasks, FF_NEW);
	}
	
	@PutMapping("/3")
	public void savePersonTask3(@RequestBody List<PersonTask> tasks) {
		personTaskService.savePersonTasks(tasks, MV);
	}
	
}