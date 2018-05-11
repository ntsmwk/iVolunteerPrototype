package at.jku.cis.workflow.service;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.workflow.model.WorkflowType;

@Service
public class WorkflowMangementService {

	private final Map<String, String> taskId2ProcessIdMap = new HashMap<>();

	@Autowired
	RuntimeService runtimeService;

	public Map<String, String> getTaskId2ProcessIdMap() {
		return taskId2ProcessIdMap;
	}

	public ProcessInstance createProcessInstance(String taskId, WorkflowType workflowType) {
		ProcessInstance instance = runtimeService.createProcessInstanceById(workflowType.getValue()).execute();
		this.taskId2ProcessIdMap.put(taskId, instance.getId());
		return instance;
	}
	
	

}
