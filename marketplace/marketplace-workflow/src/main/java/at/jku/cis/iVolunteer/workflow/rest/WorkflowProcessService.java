package at.jku.cis.iVolunteer.workflow.rest;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowProcessService {

	private static final String TASK_ID = "taskId";

	@Autowired
	private RuntimeService runtimeService;

	public String findInstanceIdForTaskId(String taskId) {
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().active().list();
		ProcessInstance instance = filterProcessInstanceByTaskId(processInstances, taskId);
		return instance == null ? null: instance.getProcessInstanceId();
	}

	private ProcessInstance filterProcessInstanceByTaskId(List<ProcessInstance> processInstances, String taskId) {
		return processInstances.stream().filter(processInstance -> {
			String instanceId = processInstance.getProcessInstanceId();
			String taskIdForInstanceId = runtimeService.getVariable(instanceId, TASK_ID, String.class);
			return StringUtils.equals(taskId, taskIdForInstanceId);
		}).findFirst().orElse(null);
	}

}
