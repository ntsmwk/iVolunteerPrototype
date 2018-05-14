package at.jku.cis.marketplace.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.bpmn.parser.EventSubscriptionDeclaration;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
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
	private ProcessEngine processEngine;

	// curl -H "Content-Type: application/json" -d '' http://localhost:8080/workflow/myProcess?taskId=abcdedfg
	@PostMapping("/{processKey}")
	public String startWorkflow(@PathVariable("processKey") String processKey, @RequestParam("taskId") String taskId) {
		Map<String, Object> params = new HashMap<>();
		params.put("taskId", taskId);
		return processEngine.getRuntimeService().startProcessInstanceByKey(processKey, params).getProcessInstanceId();
	}

	// curl -H "Content-Type: application/json" http://localhost:8080/workflow/myProcess/5/task
	@GetMapping("/{processKey}/{instanceId}/event")
	public List<String> getTasksByInstanceId(@PathVariable("processKey") String processKey,
			@PathVariable("instanceId") String instanceId) {
		return retrieveEventsByProcessKeyAndInstanceId(processKey, instanceId);
	}

	// curl -H "Content-Type: application/json" -d '' http://localhost:8080/workflow/myProcess/17/task/21
	@PostMapping("/{processKey}/{instanceId}/event/{eventName}")
	public void completeTask(@PathVariable("processKey") String processKey,
			@PathVariable("instanceId") String instanceId, @PathVariable("eventName") String eventName) {
		List<Execution> executions = processEngine.getRuntimeService().createExecutionQuery()
				.processInstanceId(instanceId).signalEventSubscriptionName(eventName).list();
		if (executions.isEmpty()) {
			throw new UnsupportedOperationException();
		}

		for (Execution execution : executions) {
			processEngine.getRuntimeService().signalEventReceived(eventName, execution.getId());
		}
	}

	private List<String> retrieveEventsByProcessKeyAndInstanceId(String processKey, String instanceId) {
		List<String> events = new ArrayList<>();
		List<Execution> executions = findExectutionsByProcessInstanceId(instanceId);
		executions.forEach(execution -> events.addAll(extractEventsOfExecution(execution)));
		return events.stream().distinct().collect(Collectors.toList());
	}

	private List<Execution> findExectutionsByProcessInstanceId(String instanceId) {
		return processEngine.getRuntimeService().createExecutionQuery().processInstanceId(instanceId).list().stream()
				.filter(t -> t.getParentId() != null).collect(Collectors.toList());
	}

	private List<String> extractEventsOfExecution(Execution execution) {
		List<EventSubscriptionDeclaration> eventDefinitions = extractEventDefinitions(execution);
		return eventDefinitions.stream().map(event -> event.getEventName()).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	private List<EventSubscriptionDeclaration> extractEventDefinitions(Execution execution) {
		ExecutionEntity executionEntity = (ExecutionEntity) execution;
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) processEngine.getRepositoryService()
				.getProcessDefinition(executionEntity.getProcessDefinitionId());
		ActivityImpl activity = processDefinition.findActivity(execution.getActivityId());
		return (List<EventSubscriptionDeclaration>) activity.getProperties().get("eventDefinitions");
	}

}
