package at.jku.cis.iVolunteer.marketplace.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.user.User;

@Controller
@RequestMapping("task")
public class TaskInstanceController {

	@Autowired private ClassInstanceService classInstanceService;
	@Autowired private LoginService loginService;

	@GetMapping("/tenant/{tenantId}")
	public List<ClassInstance> getTaskClassInstanceByTenantId(@PathVariable String tenantId) {
//		TODO ALEX Mapper
		return classInstanceService.getClassInstanceByArchetype(ClassArchetype.TASK, tenantId);
	}

	@GetMapping("/tenant/{tenantId}/subscribed")
	public List<ClassInstance> getSubscribedTaskClassInstancesByTenantId(@PathVariable String tenantId) {
//		TODO ALEX Mapper
		User user = loginService.getLoggedInUser();
		return classInstanceService.getClassInstanceByArchetypeAndUserId(ClassArchetype.TASK, user.getId(), tenantId);
	}

	@GetMapping("/subscribed")
	public List<ClassInstance> getSubscribedTaskClassInstances(@PathVariable String tenantId) {
//		TODO ALEX Mapper
		User user = loginService.getLoggedInUser();
		return classInstanceService.getClassInstanceByArchetypeAndUserId(ClassArchetype.TASK, user.getId());
	}

	@GetMapping("/{taskId}")
	public ClassInstance getTask(@PathVariable String taskId) {
//		TODO Alex Mapper
		return classInstanceService.getClassInstanceById(taskId);
	}

	@PostMapping("/new")
	public ClassInstance createTask(@RequestBody ClassInstance task) {
//		TODO Alex Mapper
		return this.classInstanceService.saveClassInstance(task);
	}

	@PutMapping("/{taskId}")
	public ClassInstance updateTask(@PathVariable String taskId, @RequestBody ClassInstance task) {
//		TODO Alex Mapper
		return this.classInstanceService.saveClassInstance(task);
	}

}
