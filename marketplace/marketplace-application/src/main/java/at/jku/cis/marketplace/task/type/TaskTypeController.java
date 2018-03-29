package at.jku.cis.marketplace.task.type;

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

@RestController
public class TaskTypeController {

	@Autowired
	private TaskTypeRepository taskTypeRepository;

	@GetMapping("/taskType")
	public List<TaskType> findAll() {
		return taskTypeRepository.findAll();
	}

	@GetMapping("/taskType/{id}")
	public TaskType findById(@PathVariable("id") String id) {
		return taskTypeRepository.findOne(id);
	}

	@PostMapping("/taskType")
	public TaskType create(@RequestBody TaskType taskType) {
		return taskTypeRepository.insert(taskType);
	}

	@PutMapping("/taskType/{id}")
	public TaskType update(@PathVariable("id") String id, @RequestBody TaskType taskType) {
		if (!taskTypeRepository.exists(id)) {
			throw new NotAcceptableException();
		}
		return taskTypeRepository.save(taskType);
	}

	@DeleteMapping("/taskType/{id}")
	public void delete(@PathVariable("id") String id) {
		taskTypeRepository.delete(id);
	}

}
