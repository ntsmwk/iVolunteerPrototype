package at.jku.cis.workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.workflow.model.WorkflowProcess;

@Service
public class WorkflowMangementService {

	@Autowired
	private TaskWorkflowProcessService taskWorkflowProcessService;

	public void executeComponent(String taskId, String componentId) {
		WorkflowProcess workflowProcess = taskWorkflowProcessService.getWorkflowProcess(taskId);

		if (workflowProcess.getFollowingWorkflowStep().getWorkflowComponent().getComponentId().equals(componentId)) {
			workflowProcess.executeFollowingWorkflowStep();
		}
	}
}
