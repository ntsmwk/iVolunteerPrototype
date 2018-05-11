package at.jku.cis.workflow.rest;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/workflow")
public class WorkflowResource {

	@Autowired
	private RuntimeService runtimeService;

	@PostMapping("/{taskId}")
	public void startTask(@PathVariable("taskId") String taskId) {
		ProcessInstance instance = runtimeService.startProcessInstanceById("asdf");
	}

}
