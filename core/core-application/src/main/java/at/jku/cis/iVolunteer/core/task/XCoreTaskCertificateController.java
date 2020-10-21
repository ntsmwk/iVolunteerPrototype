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
import org.springframework.web.bind.annotation.RequestParam;
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
import at.jku.cis.iVolunteer.model._httpresponses.HttpErrorMessages;
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

	// GET ALL TASKCERTIFICATE (PUBLIC TENANTS + PRIVATE TENANTS)
	// (Sortierung: die zuletzt ausgestellten taskzertifikate als 1.)
	// GET /core/taskCertificate/all/tenant/
	// Req: {
	// taskType: 'SUBSCRIBED', 'UNSUBSCRIBED' (OHNE PARAMETER IST: DEFAULT ALL)
	// onlyOpened: boolean (OHNE PARAMTER IST: DEFAULT true)
	// }
	// Res: TaskCertificate[]
	@GetMapping("/taskCertificate")
	private ResponseEntity<Object> getAllTaskCertificates(@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "taskType", required = false, defaultValue = "ALL") String taskType,
			@RequestParam(value = "onlyOpened", required = false, defaultValue = "true") boolean onlyOpened) {

		CoreUser user = loginService.getLoggedInUser();

		// TODO DEBUG
//		if (user == null) {
//			user = coreUserService.getByUserName("mweixlbaumer");
//		}
		// ----

		if (user == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_LOGGED_IN),
					HttpStatus.UNAUTHORIZED);
		}

		List<Tenant> tenants = new ArrayList<>();
			if (taskType.equals("ALL")) {
				tenants = tenantService.getTenantsByUser(user.getId());
			} else if (taskType.equals("SUBSCRIBED")) {
				tenants = tenantService.getSubscribedTenants(user);
			} else if (taskType.equals("UNSUBSCRIBED")) {
				tenants = tenantService.getUnsubscribedTenants(user);
			}

		List<XTaskCertificate> certificates = new LinkedList<>();
		if (tenants.size() > 0) {
			for (Tenant tenant : tenants) {
				Marketplace mp = marketplaceService.findById(tenant.getMarketplaceId());
				certificates.addAll(aggregateDataRestClient.getClassAndTaskInstances(mp.getUrl(), authorization,
						new GetClassAndTaskInstancesRequest(tenant, user, onlyOpened)));
			}
		}

		return ResponseEntity.ok(certificates);
	}

}
