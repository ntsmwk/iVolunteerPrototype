package at.jku.cis.iVolunteer.workflow.rest;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Function;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowTypeService {

	@Autowired
	private RepositoryService repositoryService;

	public List<WorkflowType> getWorkflowTypes() {
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
		return processDefinitions.stream().map(buildMappingForProcessDefintionToWorkflowType()).collect(toList());
	}

	private Function<ProcessDefinition, WorkflowType> buildMappingForProcessDefintionToWorkflowType() {
		return definition -> new WorkflowType(definition.getKey(), definition.getName());
	}
}
