package at.jku.cis.iVolunteer.workflow.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.workflow.model.WorkflowStep;
import at.jku.cis.iVolunteer.workflow.model.WorkflowStepType;

@Service
public class WorkflowStepService {

	private static final String VOLUNTEER_ID = "volunteerId";
	private static final String SERVICE_TASK = "serviceTask";
	private static final String EXCLUSIVE_GATEWAY = "exclusiveGateway";
	private static final String PARALLEL_GATEWAY = "parallelGateway";

	@Autowired private RuntimeService runtimeService;
	@Autowired private RepositoryService repositoryService;

	public List<WorkflowStep> getNextWorkflowSteps(Task task) {
		String volunteerId = determineVolunteerId(task);
		ActivityImpl activity = findActivityByExecution(findExecutionForTask(task));
		Set<String> labels = determineWorkflowLabelsForActivity(activity);
		return labels.stream()
				.map(label -> new WorkflowStep(task.getId(), label, WorkflowStepType.valueOf(task.getCategory()), volunteerId))
				.collect(Collectors.toList());
	}

	private String determineVolunteerId(Task task) {
		VariableInstance variableInstance = runtimeService.getVariableInstance(task.getExecutionId(), VOLUNTEER_ID);
		return variableInstance == null ? null : variableInstance.getTextValue();
	}

	private Set<String> determineWorkflowLabelsForActivity(PvmActivity activity) {
		Set<String> nextWorkflowLabels = new HashSet<>();
		List<PvmTransition> transitions = activity.getOutgoingTransitions();

		for (PvmTransition transition : transitions) {
			handleTransition(nextWorkflowLabels, transition);
		}
		return nextWorkflowLabels;
	}

	private void handleTransition(Set<String> nextWorkflowLabels, PvmTransition transition) {
		String typeProperty = (String) transition.getDestination().getProperty("type");
		switch (typeProperty) {
		case EXCLUSIVE_GATEWAY:
		case PARALLEL_GATEWAY:
			nextWorkflowLabels.addAll(determineWorkflowLabelsForActivity(transition.getDestination()));
			break;
		case SERVICE_TASK:
			nextWorkflowLabels.add((String) transition.getDestination().getProperty("name"));
			break;
		default:
			throw new UnsupportedOperationException("Activity type not supported");
		}
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
