package at.jku.cis.iVolunteer.marketplace.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.user.UserController;
import at.jku.cis.iVolunteer.model._mapper.xnet.XClassInstanceToTaskCertificateMapper;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.task.XTaskCertificate;
import at.jku.cis.iVolunteer.model.user.User;

//TODO xnet done - test
@RestController
public class XTaskCertificateController {

	@Autowired private ClassInstanceService classInstanceService;
	@Autowired private XClassInstanceToTaskCertificateMapper classInstanceToTaskCertificateMapper;
	@Autowired private CoreTenantRestClient coreTenantRestClient;
	@Autowired private UserController userController;

	// GET TASKCERTIFICATE OF ONE TENANT BY ID (BEI 1 TENANT ZEIGE ALLE TASKS IN UI)
	// (Sortierung: die zuletzt ausgestellten taskzertifikate als 1.)
	// GET {marketplaceUrl}/taskCertificate/tenant/{tenantId}/
	// Req: {}
	// Res: TaskCertificate[]
	@GetMapping("taskCertificate/tenant/{tenantId}")
	public List<XTaskCertificate> getTaskCertificatesByTenantId(@PathVariable String tenantId) {
		List<ClassInstance> classInstances = classInstanceService.getClassInstanceByArchetype(ClassArchetype.TASK,
				tenantId);
		
		Tenant tenant = coreTenantRestClient.getTenantById(tenantId);
		
		List<XTaskCertificate> certs = new ArrayList<>();
		for (ClassInstance classInstance : classInstances) {
			User user = userController.findUserById(classInstance.getId());
			certs.add(classInstanceToTaskCertificateMapper.toTarget(classInstance, tenant, user));
		}
		return certs;

	}

	// GET TASKCERTIFICATE BY ID
	// GET {marketplaceUrl}/taskCertificate/{taskId}/
	// Req: {}
	// Res: TaskCertificate
	@GetMapping("taskCertificate/{taskId}")
	public XTaskCertificate getTaskCertificate(@PathVariable String taskId) {
		ClassInstance classInstance = classInstanceService.getClassInstanceById(taskId);
		
		Tenant tenant = coreTenantRestClient.getTenantById(classInstance.getTenantId());
		User user = userController.findUserById(classInstance.getUserId());
		
		XTaskCertificate cert = classInstanceToTaskCertificateMapper.toTarget(classInstance, tenant, user);
		return cert;
	}

}
