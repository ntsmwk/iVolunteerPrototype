package at.jku.cis.iVolunteer.marketplace.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace._mapper.xnet.XFormEntryToTaskTemplateMapper;
import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.CollectionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipService;
import at.jku.cis.iVolunteer.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.form.FormEntry;
import at.jku.cis.iVolunteer.model.task.XTaskTemplate;

@RestController
@RequestMapping("taskTemplate")
public class XTaskTemplateController {

	@Autowired
	private ClassDefinitionService classDefinitionService;
	@Autowired
	private ClassConfigurationService classConfigurationController;
//	@Autowired
//	private FormEntryToTaskDefinitionMapper formEntryToTaskDefinitionMapper;
	@Autowired private XFormEntryToTaskTemplateMapper formEntryToTaskTemplateMapper;
	@Autowired
	private CollectionService collectionService;
	@Autowired
	private RelationshipService relationshipController;
	
//	GET ALL TASKTEMPLATES OF TENANT BY ID
//	GET {marketplaceUrl}/taskTemplate/tenant/{tenantId}/
//	Req: {}
//	Res: TaskTemplate[]
	@GetMapping("/tenant/{tenantId}")
	public List<XTaskTemplate> getTaskClassDefinitionsByTenantId(@PathVariable String tenantId) {
		List<ClassConfiguration> allTenantClassConfigurations = classConfigurationController
				.getClassConfigurationsByTenantId(tenantId);
		List<XTaskTemplate> ret = new ArrayList<>();

		for (ClassConfiguration classConfiguration : allTenantClassConfigurations) {
			List<ClassDefinition> allClassDefinitions = classDefinitionService
					.getClassDefinitonsById(classConfiguration.getClassDefinitionIds());
			List<Relationship> allRelationships = relationshipController
					.getRelationshipsById(classConfiguration.getRelationshipIds());
			List<ClassDefinition> eligibleClassDefinitions = allClassDefinitions.stream()
					.filter(cd -> cd.getClassArchetype().equals(ClassArchetype.TASK) && cd.getLevel() >= 1)
					.collect(Collectors.toList());

			for (ClassDefinition classDefinition : eligibleClassDefinitions) {
				FormEntry formEntry = collectionService.aggregateFormEntry(classDefinition, new FormEntry(),
						allClassDefinitions, allRelationships, true);
				ret.add(formEntryToTaskTemplateMapper.toTarget(formEntry));
			}
		}
		return ret;
	}

//	GET TASKTEMPLATE BY ID
//	GET {marketplaceUrl}/taskTemplate/{taskTemplateId}/
//	Req: {}
//	Res: TaskTemplate
	@GetMapping("/{templateId}")
	public XTaskTemplate getClassDefinition(
			@PathVariable("templateId") String templateId) {
		ClassDefinition currentClassDefinition = classDefinitionService.getClassDefinitionById(templateId);
		ClassConfiguration classConfiguration = classConfigurationController
				.getClassConfigurationById(currentClassDefinition.getConfigurationId());
		List<ClassDefinition> allClassDefinitions = classDefinitionService
				.getClassDefinitonsById(classConfiguration.getClassDefinitionIds());
		List<Relationship> allRelationships = relationshipController
				.getRelationshipsById(classConfiguration.getRelationshipIds());

		FormEntry formEntry = collectionService.aggregateFormEntry(currentClassDefinition, new FormEntry(),
				allClassDefinitions, allRelationships, true);

		return formEntryToTaskTemplateMapper.toTarget(formEntry);
	}

}
