package at.jku.cis.iVolunteer.core.task;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.aggregate.AggregateDataRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model._httprequests.GetClassAndTaskInstancesRequest;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model._httpresponses.HttpErrorMessages;
import at.jku.cis.iVolunteer.model._mapper.xnet.XClassInstanceToTaskCertificateMapper;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
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

	@GetMapping("/taskCertificate")
	private ResponseEntity<Object> getAllTaskCertificates(@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startYear", required = false, defaultValue = "0") int startYear) {

		CoreUser user = loginService.getLoggedInUser();

		if (user == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_LOGGED_IN),
					HttpStatus.UNAUTHORIZED);
		}
		List<Tenant> tenants = tenantService.getTenantsByUser(user.getId());
		List<XTaskCertificate> certificates = new LinkedList<>();
		if (tenants.size() > 0) {
			for (Tenant tenant : tenants) {
				Marketplace mp = marketplaceService.findById(tenant.getMarketplaceId());
				certificates.addAll(aggregateDataRestClient.getClassAndTaskInstances(mp.getUrl(), authorization,
						startYear, new GetClassAndTaskInstancesRequest(tenant, user)));
			}
		}
		return ResponseEntity.ok(certificates);
	}

}
