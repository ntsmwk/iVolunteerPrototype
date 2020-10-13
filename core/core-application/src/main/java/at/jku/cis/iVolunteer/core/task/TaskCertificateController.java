package at.jku.cis.iVolunteer.core.task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.tenant.TenantController;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model._httprequests.GetAllTaskCertificateRequest;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.task.XTaskCertificate;

@RestController
public class TaskCertificateController {

	
	@Autowired LoginService loginService;
	@Autowired TenantController tenantController;
		
	//	GET ALL TASKCERTIFICATE (PUBLIC TENANTS + PRIVATE TENANTS)
	//	(Sortierung: die zuletzt ausgestellten taskzertifikate als 1.)
	//	GET /core/taskCertificate/all/tenant/
	//	Req: {
	//	taskType: 'SUBSCRIBED', 'UNSUBSCRIBED' (OHNE PARAMETER IST: DEFAULT ALL)
	//	onlyOpened: boolean (OHNE PARAMTER IST: DEFAULT true)
	//	}
	//	Res: TaskCertificate[]
	@GetMapping("taskCertificate/all/tenant")
	private ResponseEntity<Object> getAllTaskCertificates(@RequestBody GetAllTaskCertificateRequest body) {
		if (body == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("body must not be null"));
		}
		
		CoreUser user = loginService.getLoggedInUser();
		
		if (user == null) {
			return new ResponseEntity<Object>(new ErrorResponse("user must be logged in"), HttpStatus.UNAUTHORIZED);
		}
		
		List<Tenant> tenants = new ArrayList<>();
//		tenantController.getSubscribedTenants();
		
		
		
		
		
		List<XTaskCertificate> certificates = new LinkedList<>();
		
		
		return null;
	}

}
