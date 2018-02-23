package at.jku.csi.marketplace.task;

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

	@GetMapping("/tasktype")
	public List<TaskType> findAll() {
		return taskTypeRepository.findAll();
	}
	
	@GetMapping("/tasktype/{id}")
	public TaskType findById(@PathVariable("id") String id) {
		return taskTypeRepository.findOne(id);
	}
	
	@PostMapping("/tasktype")
	public TaskType createTaskType(@RequestBody TaskType task) {
		return taskTypeRepository.insert(task);
	}

	@PutMapping("/tasktype/{id}")
	public TaskType updateTaskType(@PathVariable("id") String id, @RequestBody TaskType taskType) {
		if (taskTypeRepository.exists(id)) {
			throw new NotAcceptableException();
		}
		return taskTypeRepository.save(taskType);
	}

	@DeleteMapping("/tasktype/{id}")
	public void deleteTaskType(@PathVariable("id") String id) {
		taskTypeRepository.delete(id);
	}


}
