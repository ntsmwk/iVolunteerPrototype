package at.jku.cis.iVolunteer.core.task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.aggregate.AggregateDataRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.core.tenant.TenantController;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model._httprequests.GetAllTaskCertificateRequest;
import at.jku.cis.iVolunteer.model._httprequests.GetClassAndTaskInstancesRequest;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model._mapper.xnet.XClassInstanceToTaskCertificateMapper;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import at.jku.cis.iVolunteer.model.task.XTaskCertificate;

@RestController
public class XCoreTaskCertificateController {

	
	@Autowired LoginService loginService;
	@Autowired TenantService tenantService;
	@Autowired ClassInstanceRestClient classInstanceRestClient;
	@Autowired MarketplaceService marketplaceService;
	@Autowired XClassInstanceToTaskCertificateMapper classInstanceToTaskCertificateMapper;
	@Autowired CoreUserService coreUserService;
	@Autowired TaskInstanceRestClient taskInstanceRestClient;
	@Autowired AggregateDataRestClient aggregateDataRestClient;
		
	//	GET ALL TASKCERTIFICATE (PUBLIC TENANTS + PRIVATE TENANTS)
	//	(Sortierung: die zuletzt ausgestellten taskzertifikate als 1.)
	//	GET /core/taskCertificate/all/tenant/
	//	Req: {
	//	taskType: 'SUBSCRIBED', 'UNSUBSCRIBED' (OHNE PARAMETER IST: DEFAULT ALL)
	//	onlyOpened: boolean (OHNE PARAMTER IST: DEFAULT true)
	//	}
	//	Res: TaskCertificate[]
	@GetMapping("taskCertificate/all/tenant")
	private ResponseEntity<Object> getAllTaskCertificates(@RequestBody GetAllTaskCertificateRequest body, @RequestHeader("Authorization") String authorization) {		
		if (body == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("body must not be null"));
		}
		
		CoreUser user = loginService.getLoggedInUser();
		
		
		//DEBUG
		if (user == null) {
			user = coreUserService.getByUserName("mweixlbaumer");
		}
		//----		
		
		if (user == null) {
			return new ResponseEntity<Object>(new ErrorResponse("user must be logged in"), HttpStatus.UNAUTHORIZED);
		}
		
		List<Tenant> tenants = new ArrayList<>();
//		tenantController.getSubscribedTenants();
		if (body.getTaskType().equals("ALL")) {
			tenants = tenantService.getTenantsByUser(user.getId());
		} else if (body.getTaskType().equals("SUBSCRIBED")) {
			tenants = tenantService.getSubscribedTenants(user);
		} else if (body.getTaskType().equals("UNSUBSCRIBED")) {
			tenants = tenantService.getUnsubscribedTenants(user);
		}
		
		List<XTaskCertificate> certificates = new LinkedList<>();
		if (tenants.size() > 0) {
			for (Tenant tenant : tenants) {
				List<ClassInstance> classInstances = new LinkedList<>();
				Marketplace mp = marketplaceService.findById(tenant.getMarketplaceId());
				
				certificates.addAll(aggregateDataRestClient.getClassAndTaskInstances(mp.getUrl(), authorization, new GetClassAndTaskInstancesRequest(tenant, user)));
				
//				classInstances.addAll(classInstanceRestClient.getClassInstancesByUserAndTenant(mp.getUrl(), authorization, ClassArchetype.TASK, user.getId(), tenant.getId()));
//				for (ClassInstance ci : classInstances) {
//					TaskInstance ti = taskInstanceRestClient.getTaskInstanceById(mp.getUrl(), authorization, ci.getId());
//					certificates.add(classInstanceToTaskCertificateMapper.toTarget(ci, ti, tenant, user));
//				}
			}
		}
		
		return ResponseEntity.ok(certificates);
	}

}
