package at.jku.cis.workflow.rest;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.workflow.model.WorkflowType;
import at.jku.cis.workflow.service.WorkflowMangementService;

@RestController("/workflow")
public class WorkflowResource {

	@Autowired
	private WorkflowMangementService workflowManagementService;
	
	@PostMapping("/create/{taskId}")
	public void createTask(@PathVariable("taskId") String taskId) {
		ProcessInstance instance = workflowManagementService.createProcessInstance(taskId, WorkflowType.DEFAULT_WORKFLOW);
	}
	
	@PostMapping("/publish/{taskId}")
	public void publishTask(@PathVariable("taskId") String taskId) {
		String processId = workflowManagementService.getTaskId2ProcessIdMap().get(taskId);
		if(processId == null) {
			return;
		}
		//handle publish...
	}
	

}
