package at.jku.cis.workflow.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.workflow.service.WorkflowMangementService;

@RestController("/workflow")
public class WorkflowResource {

	@Autowired
	private WorkflowMangementService workflowManagementService;

	@PostMapping("/execute/{taskId}/{componentId}")
	public ResponseEntity<Boolean> createTask(@PathVariable("taskId") String taskId,
			@PathVariable("componentId") String componentId) {
		workflowManagementService.executeComponent(taskId, componentId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
