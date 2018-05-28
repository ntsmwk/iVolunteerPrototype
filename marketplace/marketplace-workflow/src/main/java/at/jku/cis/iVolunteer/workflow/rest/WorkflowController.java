package at.jku.cis.iVolunteer.workflow.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {

	private static final String ACCESS_TOKEN = "accessToken";

	private static final String WORKFLOW_ACTIVITY_LABEL = "label";

	@Autowired
	private TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private WorkflowStepService workflowStepService;

	@Autowired
	private WorkflowTypeService workfowTypeService;

	@GetMapping("/type")
	public List<WorkflowType> getWorkflowTypes() {
		return workfowTypeService.getWorkflowTypes();
	}

	@GetMapping("/processId")
	public String getProcessId(@RequestParam("taskId") String taskId) {
		ProcessInstance instance = runtimeService.createProcessInstanceQuery().active().list().stream()
				.filter(processInstance -> {
					if (runtimeService.getVariable(processInstance.getProcessInstanceId(), "taskId") != null) {
						return runtimeService.getVariable(processInstance.getProcessInstanceId(), "taskId")
								.equals(taskId);
					}
					return false;
				}).findFirst().orElse(null);
		if (instance != null) {
			return instance.getProcessInstanceId();
		}
		return null;
	}

	@PostMapping("/{workflowKey}")
	public String startWorkflow(@PathVariable("workflowKey") String workflowKey,
			@RequestParam("taskId") String taskId) {
		Map<String, Object> params = new HashMap<>();
		params.put("taskId", taskId);
		return runtimeService.startProcessInstanceByKey(workflowKey, params).getProcessInstanceId();
	}

	@GetMapping("/{workflowKey}/{instanceId}/step")
	public List<WorkflowStep> getNextWorkflowSteps(@PathVariable("workflowKey") String workflowKey,
			@PathVariable("instanceId") String instanceId) {
		List<Task> tasks = retrieveActiveTaskByWorkflowKeyAndInstanceId(workflowKey, instanceId);
		List<WorkflowStep> steps = new ArrayList<>();
		tasks.forEach(task -> steps.addAll(workflowStepService.getNextWorkflowSteps(task)));
		return steps;
	}

	@PostMapping("/{workflowKey}/{instanceId}/step")
	public void completeWorkflowStep(@PathVariable("workflowKey") String workflowKey,
			@PathVariable("instanceId") String instanceId, @RequestBody WorkflowStep workflowStep,
			@RequestHeader("Authorization") String authorization) {

		List<Task> tasks = retrieveActiveTaskByWorkflowKeyAndInstanceId(workflowKey, instanceId);
		if (tasks.stream().anyMatch(task -> !StringUtils.equals(task.getId(), workflowStep.getTaskId()))) {
			throw new UnsupportedOperationException();
		}

		Map<String, Object> params = new HashMap<>();
		params.put(WORKFLOW_ACTIVITY_LABEL, workflowStep.getLabel());
		params.put(ACCESS_TOKEN, authorization);
		if (workflowStep.getParams() != null) {
			workflowStep.getParams().forEach((name, value) -> params.put(name, value));
		}
		taskService.complete(workflowStep.getTaskId(), params);
	}

	@DeleteMapping("/{workflowKey}/{instanceId}")
	public void cancelWorkflow(@PathVariable("workflowKey") String workflowKey,
			@PathVariable("instanceId") String instanceId) {
		runtimeService.deleteProcessInstance(instanceId, "Workflow is aborted");
	}

	private List<Task> retrieveActiveTaskByWorkflowKeyAndInstanceId(String workflowKey, String instanceId) {
		return taskService.createTaskQuery().processInstanceId(instanceId).active().list();
	}
}