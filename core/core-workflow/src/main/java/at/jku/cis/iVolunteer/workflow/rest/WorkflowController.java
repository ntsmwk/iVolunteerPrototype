package at.jku.cis.iVolunteer.workflow.rest;

import static at.jku.cis.iVolunteer.workflow.task.ServiceTask.EMPOYEE_ID;
import static at.jku.cis.iVolunteer.workflow.task.ServiceTask.TASK_ID;
import static at.jku.cis.iVolunteer.workflow.task.ServiceTask.TOKEN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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

import at.jku.cis.iVolunteer.workflow.model.WorkflowStep;
import at.jku.cis.iVolunteer.workflow.model.WorkflowType;
import at.jku.cis.iVolunteer.workflow.service.WorkflowProcessService;
import at.jku.cis.iVolunteer.workflow.service.WorkflowStepService;
import at.jku.cis.iVolunteer.workflow.service.WorkflowTypeService;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private WorkflowTypeService workfowTypeService;

	@Autowired
	private WorkflowStepService workflowStepService;

	@Autowired
	private WorkflowProcessService workflowProcessService;

	@GetMapping("/type")
	public List<WorkflowType> getWorkflowTypes() {
		return workfowTypeService.getWorkflowTypes();
	}

	@GetMapping("/processId")
	public String getProcessId(@RequestParam("taskId") String taskId) {
		return workflowProcessService.findInstanceIdForTaskId(taskId);
	}

	@PostMapping("/{workflowKey}")
	public String startWorkflow(@PathVariable("workflowKey") String workflowKey, @RequestParam("taskId") String taskId,
			@RequestParam("employeeId") String employeeId) {
		Map<String, Object> params = new HashMap<>();
		params.put(TASK_ID, taskId);
		params.put(EMPOYEE_ID, employeeId);
		return runtimeService.startProcessInstanceByKey(workflowKey, params).getProcessInstanceId();
	}

	@GetMapping("/{workflowKey}/{instanceId}/step")
	public List<WorkflowStep> getNextWorkflowSteps(@PathVariable("workflowKey") String workflowKey,
			@PathVariable("instanceId") String instanceId, @RequestParam("participantId") String participantId) {
		List<Task> tasks = retrieveActiveTaskByInstanceIdAndParticipantId(instanceId, participantId);
		List<WorkflowStep> steps = new ArrayList<>();
		tasks.forEach(task -> steps.addAll(workflowStepService.getNextWorkflowSteps(task)));
		return steps;
	}

	@PostMapping("/{workflowKey}/{instanceId}/step")
	public void completeWorkflowStep(@PathVariable("workflowKey") String workflowKey,
			@PathVariable("instanceId") String instanceId, @RequestBody WorkflowStep workflowStep,
			@RequestParam("participantId") String participantId, @RequestHeader("Authorization") String token) {

		List<Task> tasks = retrieveActiveTaskByInstanceIdAndParticipantId(instanceId, participantId);
		if (!tasks.stream().anyMatch(task -> StringUtils.equals(task.getId(), workflowStep.getTaskId()))) {
			throw new UnsupportedOperationException();
		}

		Map<String, Object> params = new HashMap<>();
		params.put(TOKEN, token);
		taskService.complete(workflowStep.getTaskId(), params);
	}

	@DeleteMapping("/{workflowKey}/{instanceId}")
	public void cancelWorkflow(@PathVariable("workflowKey") String workflowKey,
			@PathVariable("instanceId") String instanceId) {
		runtimeService.deleteProcessInstance(instanceId, "Workflow is aborted");
	}

	private List<Task> retrieveActiveTaskByInstanceIdAndParticipantId(String instanceId, String participantId) {
		return taskService.createTaskQuery().processInstanceId(instanceId).taskAssignee(participantId).active().list();
	}
}