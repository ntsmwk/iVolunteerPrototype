package at.jku.cis.marketplace.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;

	//curl  -H "Content-Type: application/json" -d '' http://localhost:8080/workflow/myProcess?taskId=abcdedfg
	@PostMapping("/{processKey}")
	public String startWorkflow(@PathVariable("processKey") String processKey, @RequestParam("taskId") String taskId) {
		Map<String, Object> params = new HashMap<>();
		params.put("taskId", taskId);
		return runtimeService.startProcessInstanceByKey(processKey, params).getProcessInstanceId();
	}

	//curl  -H "Content-Type: application/json"  http://localhost:8080/workflow/myProcess/17/task
	@GetMapping("/{processKey}/{instanceId}/task")
	public List<String> getTasksByInstanceId(@PathVariable("processKey") String processKey,
			@PathVariable("instanceId") String instanceId) {
		List<Task> activeTasks = retrieveActiveTasksByProcessKeyAndInstanceId(processKey, instanceId);
		return activeTasks.stream().map(activeTask -> activeTask.getName()).collect(Collectors.toList());
	}

	//curl  -H "Content-Type: application/json" -d '' http://localhost:8080/workflow/myProcess/17/task/21
	@PostMapping("/{processKey}/{instanceId}/task/{taskId}")
	public void completeTask(@PathVariable("processKey") String processKey, @PathVariable("instanceId") String instanceId,
			@PathVariable("taskId") String taskId) {
		List<Task> activeTasks = retrieveActiveTasksByProcessKeyAndInstanceId(processKey, instanceId);
		if (activeTasks.stream().noneMatch(task -> StringUtils.equals(task.getId(), taskId))) {
			throw new UnsupportedOperationException();
		}

		taskService.complete(taskId);
	}

	private List<Task> retrieveActiveTasksByProcessKeyAndInstanceId(String processKey, String instanceId) {
		return taskService.createTaskQuery().processInstanceId(instanceId).active().list();
	}

}
