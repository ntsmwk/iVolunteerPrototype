	package at.jku.cis.iVolunteer.workflow;
	
	import java.util.List;
	import java.util.stream.Collectors;
	
	import org.activiti.engine.RepositoryService;
	import org.activiti.engine.repository.ProcessDefinition;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	
	@Service
	public class WorkflowTypeService {
	
		@Autowired
		private RepositoryService repositoryService;
	
		public List<String> getWorkflowTypes() {
			List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
			return processDefinitions.stream().map(definition -> definition.getKey()).collect(Collectors.toList());
		}
	}
