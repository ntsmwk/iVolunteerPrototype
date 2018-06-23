package at.jku.cis.iVolunteer.core.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.task.TaskStatus;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;

@RestController("/task")
public class CoreTaskController {

	@Autowired
	private CoreTaskService coreTaskService;

	@GetMapping("/task")
	public List<TaskDTO> findAllByMarketplace(
			@RequestParam(name = "marketplaceId", required = false) String marketplaceId,
			@RequestParam(name = "status", required = false) TaskStatus status,
			@RequestHeader("Authorization") String authorization) {
		return coreTaskService.findAllByMarketplace(marketplaceId, status, authorization);
	}

	@GetMapping("/task/{marketplaceId}/{taskId}")
	public TaskDTO findByTaskId(@PathVariable("taskId") String taskId,
			@PathVariable("marketplaceId") String marketplaceId,
			@RequestParam(name = "status", required = false) TaskStatus status,
			@RequestHeader("Authorization") String authorization) {
		return coreTaskService.findByTaskId(taskId, marketplaceId, status, authorization);
	}

	@GetMapping("/task/volunteer/{volunteerId}")
	public List<TaskDTO> findByVolunteer(@PathVariable("volunteerId") String volunteerId,
			@RequestParam(name = "status", required = false) TaskStatus status,
			@RequestHeader("Authorization") String authorization) {
		return coreTaskService.findByVolunteer(volunteerId, status, authorization);
	}

	@PostMapping("/task/{marketplaceId}")
	public TaskDTO createTask(@PathVariable("marketplaceId") String marketplaceId, @RequestBody TaskDTO taskDto,
			@RequestHeader("Authorization") String authorization) {
		return coreTaskService.createTask(marketplaceId, taskDto, authorization);
	}
}
