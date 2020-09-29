package at.jku.cis.iVolunteer.marketplace.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace._mapper.task.FormEntryToTaskDefinitionMapper;
import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationController;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.CollectionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipController;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.form.FormEntry;
import at.jku.cis.iVolunteer.model.task.TaskDefinition;

@RestController
@RequestMapping("tasktemplate")
public class TaskDefinitionController {

	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private ClassConfigurationController classConfigurationController;
	@Autowired private FormEntryToTaskDefinitionMapper formEntryToTaskDefinitionMapper;
	@Autowired private CollectionService collectionService;
	@Autowired private RelationshipController relationshipController;
	
	@GetMapping("/tenant/{tenantId}")
	public List<TaskDefinition> getTaskClassDefinitionsByTenantId(@PathVariable String tenantId){		
		List<ClassConfiguration> allTenantClassConfigurations = classConfigurationController.getClassConfigurationsByTenantId(tenantId);
		List<TaskDefinition> ret = new ArrayList<>();
		
		for (ClassConfiguration classConfiguration : allTenantClassConfigurations) {
			List<ClassDefinition> allClassDefinitions = classDefinitionService.getClassDefinitonsById(classConfiguration.getClassDefinitionIds(), tenantId);
			List<Relationship> allRelationships = relationshipController.getRelationshipsById(classConfiguration.getRelationshipIds());
			List<ClassDefinition> eligibleClassDefinitions = allClassDefinitions.stream()
				.filter(cd -> cd.getClassArchetype().equals(ClassArchetype.TASK) && cd.getLevel() >= 1)
				.collect(Collectors.toList());
			
			for (ClassDefinition classDefinition : eligibleClassDefinitions) {
				FormEntry formEntry = collectionService.aggregateFormEntry(classDefinition, new FormEntry(), allClassDefinitions, allRelationships, true);	
				ret.add(formEntryToTaskDefinitionMapper.toTarget(formEntry));
			}
		}
		return ret;
	}
	
	@GetMapping("/tenant/{tenantId}/template/{templateId}")
	public TaskDefinition getClassDefinition(@PathVariable("tenantId") String tenantId, @PathVariable("templateId") String templateId) {		
		ClassDefinition currentClassDefinition = classDefinitionService.getClassDefinitionById(templateId, tenantId);
		ClassConfiguration classConfiguration = classConfigurationController.getClassConfigurationById(currentClassDefinition.getConfigurationId());
		List<ClassDefinition> allClassDefinitions = classDefinitionService.getClassDefinitonsById(classConfiguration.getClassDefinitionIds(), tenantId);
		List<Relationship> allRelationships = relationshipController.getRelationshipsById(classConfiguration.getRelationshipIds());
		
		FormEntry formEntry = collectionService.aggregateFormEntry(currentClassDefinition, new FormEntry(), allClassDefinitions, allRelationships, true);	

		return formEntryToTaskDefinitionMapper.toTarget(formEntry);	
	}

}
