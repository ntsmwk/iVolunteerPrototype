package at.jku.cis.iVolunteer.marketplace.task.template;

import java.util.List;

import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.task.template.TaskTemplate;

@RestController
public class TaskTemplateController {

	@Autowired
	private TaskTemplateRepository taskTemplateRepository;

	@GetMapping("/taskTemplate")
	public List<TaskTemplate> findAll() {
		return taskTemplateRepository.findAll();
	}

	@GetMapping("/taskTemplate/{id}")
	public TaskTemplate findById(@PathVariable("id") String id) {
		return taskTemplateRepository.findOne(id);
	}

	@PostMapping("/taskTemplate")
	public TaskTemplate create(@RequestBody TaskTemplate taskTemplate) {
		return taskTemplateRepository.insert(taskTemplate);
	}

	@PutMapping("/taskTemplate/{id}")
	public TaskTemplate update(@PathVariable("id") String id, @RequestBody TaskTemplate taskTemplate) {
		if (!taskTemplateRepository.exists(id)) {
			throw new NotAcceptableException();
		}
		return taskTemplateRepository.save(taskTemplate);
	}

	@DeleteMapping("/taskTemplate/{id}")
	public void delete(@PathVariable("id") String id) {
		taskTemplateRepository.delete(id);
	}

}
