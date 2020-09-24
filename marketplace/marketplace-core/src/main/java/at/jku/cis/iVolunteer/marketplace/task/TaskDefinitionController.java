package at.jku.cis.iVolunteer.marketplace.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@Controller
@RequestMapping("/tasktemplate")
public class TaskDefinitionController {

	@Autowired private ClassInstanceService classInstanceService;
	
	@GetMapping("/tenant/{tenantId}")
	public List<ClassInstance> getTaskClassDefinitionsByTenantId(@PathVariable String tenantId){
//		TODO ALEX Mapper
		return classInstanceService.getClassInstanceByArchetype(ClassArchetype.TASK, tenantId);
	}
}
