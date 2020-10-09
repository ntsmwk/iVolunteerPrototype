package at.jku.cis.iVolunteer.marketplace.task;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace._mapper.xnet.ClassInstanceToTaskInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.task.TaskInstance;
import at.jku.cis.iVolunteer.model.task.XTaskCertificate;
import at.jku.cis.iVolunteer.model.user.User;

//TODO xnet done - test
@RestController
public class XTaskCertificateController {

	@Autowired private ClassInstanceService classInstanceService;
	@Autowired private LoginService loginService;
	@Autowired private ClassInstanceToTaskInstanceMapper classInstanceToTaskInstanceMapper;

	// GET TASKCERTIFICATE OF ONE TENANT BY ID (BEI 1 TENANT ZEIGE ALLE TASKS IN UI)
	// (Sortierung: die zuletzt ausgestellten taskzertifikate als 1.)
	// GET {marketplaceUrl}/taskCertificate/tenant/{tenantId}/
	// Req: {}
	// Res: TaskCertificate[]
	@GetMapping("taskCertificate/tenant/{tenantId}")
	public List<XTaskCertificate> getTaskClassInstancesByTenantId(@PathVariable String tenantId) {
		List<ClassInstance> classInstances = classInstanceService.getClassInstanceByArchetype(ClassArchetype.TASK,
				tenantId);
//		return classInstanceToTaskInstanceMapper.toTargets(classInstances);
//TODO
		
		
		return null;

	}

	// GET TASKCERTIFICATE BY ID
	// GET {marketplaceUrl}/taskCertificate/{taskId}/
	// Req: {}
	// Res: TaskCertificate
	@GetMapping("taskCertificate/{taskId}")
	public XTaskCertificate getTask(@PathVariable String taskId) {
		ClassInstance classInstance = classInstanceService.getClassInstanceById(taskId);
//		return classInstanceToTaskInstanceMapper.toTargets(classInstances);
//TODO
		return null;
	}

}
