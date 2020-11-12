package at.jku.cis.iVolunteer.marketplace.task;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;

@RestController
public class XTaskInstanceController {

	@Autowired XTaskInstanceService xTaskInstanceService;
	@Autowired private LoginService loginService;

	@GetMapping("/meta/core/task-instance/{taskId}")
	public TaskInstance getTaskInstance(@PathVariable("taskId") String id) {
		return xTaskInstanceService.getTaskInstance(id);
	}

	@GetMapping("/meta/core/task-instance/all")
	public List<TaskInstance> getTaskInstances(@RequestParam(value = "taskType", defaultValue = "ALL") String taskType,
			@RequestParam(value = "startYear", defaultValue = "0") int startYear,
			@RequestParam(value = "status", defaultValue = "ALL") String status) {
		String loggedInUserId = loginService.getLoggedInUser().getId();
		return xTaskInstanceService.getAll(taskType, startYear, status,loggedInUserId);

	}

	@PutMapping("/meta/core/task-instance/tenant")
	public List<TaskInstance> getTaskInstancesByTenant(@RequestBody List<String> tenantIds,
			@RequestParam(value = "taskType", defaultValue = "ALL") String taskType,
			@RequestParam(value = "startYear", defaultValue = "0") int startYear,
			@RequestParam(value = "status", defaultValue = "ALL") String status) {
		List<TaskInstance> instances = new LinkedList<>();
		String loggedInUserId = loginService.getLoggedInUser().getId();

		for (String id : tenantIds) {
			instances.addAll(xTaskInstanceService.getTaskInstanceByTenantId(id, taskType, startYear, status, loggedInUserId));
		}
		return instances;
	}

}
