package at.jku.cis.iVolunteer.marketplace.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

@Controller
@RequestMapping("/tasktemplate")
public class TaskDefinitionController {

	@Autowired private ClassDefinitionService classDefinitionService;
	
	@GetMapping("/tenant/{tenantId}")
	public List<ClassDefinition> getTaskClassDefinitionsByTenantId(@PathVariable String tenantId){
//		TODO ALEX Mapper
		return classDefinitionService.getClassDefinitionsByArchetype(ClassArchetype.TASK, tenantId);
	}
}
