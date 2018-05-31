package at.jku.cis.iVolunteer.workflow.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowStepService {

	private static final String SERVICE_TASK = "serviceTask";
	private static final String EXCLUSIVE_GATEWAY = "exclusiveGateway";
	
	@Autowired
	private RuntimeService runtimeService;
		@Autowired
	private RepositoryService repositoryService;

	public List<WorkflowStep> getNextWorkflowSteps(Task task) {
		ActivityImpl activity = findActivityByExecution(findExecutionForTask(task));
		
		Set<String> labels = determineWorkflowLabelsForActivity(activity);
		return labels.stream().map(label -> new WorkflowStep(task.getId(), label)).collect(Collectors.toList());
	}

	private Set<String> determineWorkflowLabelsForActivity(PvmActivity activity) {
		Set<String> nextWorkflowLabels = new HashSet<>();
		List<PvmTransition> transitions = activity.getOutgoingTransitions();

		for (PvmTransition transition : transitions) {
			String typeProperty = (String) transition.getDestination().getProperty("type");
			if (typeProperty.equals(EXCLUSIVE_GATEWAY)) {
				nextWorkflowLabels.addAll(determineWorkflowLabelsForActivity(transition.getDestination()));
			} else if (typeProperty.equals(SERVICE_TASK)) {
				nextWorkflowLabels.add((String) transition.getDestination().getProperty("name"));
			} else {
				throw new UnsupportedOperationException("Activity type not supported");
			}
		}
		return nextWorkflowLabels;
	}

	private ExecutionEntity findExecutionForTask(Task task) {
		ExecutionQuery executionQuery = runtimeService.createExecutionQuery();
		return (ExecutionEntity) executionQuery.executionId(task.getExecutionId()).singleResult();
	}

	private ActivityImpl findActivityByExecution(ExecutionEntity executionEntitiy) {
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(executionEntitiy.getProcessDefinitionId());
		return processDefinition.findActivity(executionEntitiy.getActivityId());
	}

}
