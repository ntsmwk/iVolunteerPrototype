package at.jku.cis.iVolunteer.workflow.service;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.workflow.model.WorkflowType;

@Service
public class WorkflowTypeService {

	@Autowired
	private RepositoryService repositoryService;

	public List<WorkflowType> getWorkflowTypes() {
		return mappToWorkflowTypes(repositoryService.createProcessDefinitionQuery().active().latestVersion().list());
	}

	private List<WorkflowType> mappToWorkflowTypes(List<ProcessDefinition> processDefinitions) {
		return processDefinitions.stream()
				.map(buildMappingForProcessDefintionToWorkflowType())
				.sorted(buildSortingByName())
				.collect(toList());
	}

	private Comparator<? super WorkflowType> buildSortingByName() {
		return (w1, w2) -> StringUtils.compare(w1.getName(), w2.getName());
	}

	private Function<ProcessDefinition, WorkflowType> buildMappingForProcessDefintionToWorkflowType() {
		return definition -> new WorkflowType(definition.getKey(), definition.getName());
	}
}
