package at.jku.cis.marketplace.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private TaskService taskService;

	@Autowired
	private WorkflowStepService workflowStepService;

	// curl -H "Content-Type: application/json" -d '' http://localhost:8080/workflow/myProcessMwe?taskId=abcdedfg
	@PostMapping("/{processKey}")
	public String startWorkflow(@PathVariable("processKey") String processKey, @RequestParam("taskId") String taskId) {
		Map<String, Object> params = new HashMap<>();
		params.put("taskId", taskId);
		return processEngine.getRuntimeService().startProcessInstanceByKey(processKey, params).getProcessInstanceId();
	}

	// curl -H "Content-Type: application/json" http://localhost:8080/workflow/myProcessMwe/8/task
	@GetMapping("/{processKey}/{instanceId}/task")
	public List<WorkflowStep> getTasksByInstanceId(@PathVariable("processKey") String processKey,
			@PathVariable("instanceId") String instanceId) {
		
		return workflowStepService
				.getNextWorkflowSteps(retrieveActiveTaskByProcessKeyAndInstanceId(processKey, instanceId));
	}

	// curl -H "Content-Type: application/json" -d '' http://localhost:8080/workflow/myProcessMwe/8/task
	@PostMapping("/{processKey}/{instanceId}/task")
	public void completeTask(@PathVariable("processKey") String processKey,
			@PathVariable("instanceId") String instanceId, @RequestBody WorkflowStep workflowStep) {
		Task task = retrieveActiveTaskByProcessKeyAndInstanceId(processKey, instanceId);
		if (StringUtils.equals(task.getId(), workflowStep.getTaskId())) {
			throw new UnsupportedOperationException();
		}

		Map<String, Object> params = new HashMap<>();
		params.put("label", workflowStep.getLabel());
		taskService.complete(workflowStep.getTaskId(), params);
	}

	private Task retrieveActiveTaskByProcessKeyAndInstanceId(String processKey, String instanceId) {
		return taskService.createTaskQuery().processInstanceId(instanceId).active().singleResult();
	}
}