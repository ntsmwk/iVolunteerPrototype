package at.jku.cis.iVolunteer.core.task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.core.user.CoreUserService;
import at.jku.cis.iVolunteer.core.user.LoginService;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model._httpresponses.HttpErrorMessages;
import at.jku.cis.iVolunteer.model._mapper.xnet.XTaskInstanceToTaskMapper;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import at.jku.cis.iVolunteer.model.task.XTask;
import at.jku.cis.iVolunteer.model.user.User;

@RestController
public class XCoreTaskController {

	@Autowired TaskInstanceRestClient taskInstanceRestClient;
	@Autowired MarketplaceService marketplaceService;
	@Autowired XTaskInstanceToTaskMapper xTaskInstanceToTaskMapper;
	@Autowired CoreUserService coreUserService;
	@Autowired TenantService tenantService;
	@Autowired LoginService loginService;

//	CORE PART ##############
//	(
//	GILT FÜR ALLE 3 CALLS:
//	PARAMETER taskType: 'SUBSCRIBED', 'UNSUBSCRIBED' (OHNE PARAMETER IST: DEFAULT 'ALL')
//	PARAMETER onlyOpened: boolean (OHNE PARAMTER IST: DEFAULT true)
//	)

//	GET ALL TASKS (PUBLIC TENANTS + PRIVATE TENANTS)
//	(Sortierung: nach Startdatum - die die am frühesten starten zuerst)
//	GET /core/task
//	Req: {}
//	Res: Task[]

	@GetMapping("/task")
	private ResponseEntity<Object> getTasks(@RequestHeader("Authorization") String authorization) {

		List<Marketplace> marketplaces = marketplaceService.findAll();

		if (marketplaces == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_FOUND_MARKETPLACE),
					HttpStatus.BAD_REQUEST);
		}

		List<TaskInstance> taskInstances = new ArrayList<>();

		for (Marketplace mp : marketplaces) {
			List<TaskInstance> ret = taskInstanceRestClient.getTaskInstances(mp.getUrl(), authorization);
			if (ret != null) {
				taskInstances.addAll(ret);
			}
		}

		List<XTask> tasks = new LinkedList<>();
		for (TaskInstance ti : taskInstances) {
			List<CoreUser> users = coreUserService.findByIds(ti.getSubscribedVolunteerIds());
			tasks.add(xTaskInstanceToTaskMapper.toTarget(ti, users));
		}

		return ResponseEntity.ok(tasks);
	}

//	GET ALL TASKS OF SUBSCRIBED TENANTS (MEINE TASKS LISTE UI)
//	(Sortierung: nach Startdatum - die die am frühesten starten zuerst)
//	GET /core/task/tenant/subscribed
//	Req: {}
//	Res: Task[]
	@GetMapping("/task/tenant/subscribed")
	private ResponseEntity<Object> getTasksOfSubscribedTenants(@RequestHeader("Authorization") String authorization) {
		List<Marketplace> marketplaces = marketplaceService.findAll();
		if (marketplaces == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_FOUND_MARKETPLACE),
					HttpStatus.BAD_REQUEST);
		}

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

		List<Tenant> tenants = tenantService.getSubscribedTenants(user);

		if (tenants == null) {
			return ResponseEntity.ok().build();
		}

		List<TaskInstance> taskInstances = new LinkedList<>();

		for (Marketplace mp : marketplaces) {
			List<TaskInstance> ret = taskInstanceRestClient.getTaskInstancesByTenant(mp.getUrl(),
					tenants.stream().map(t -> t.getId()).collect(Collectors.toList()), authorization);
			if (ret != null) {
				taskInstances.addAll(ret);
			}
		}
		
		List<XTask> tasks = new LinkedList<>();

		for (TaskInstance ti : taskInstances) {
			List<CoreUser> users = coreUserService.findByIds(ti.getSubscribedVolunteerIds());
			tasks.add(xTaskInstanceToTaskMapper.toTarget(ti, users));		
		}
		
		return ResponseEntity.ok(tasks);
	}

//	GET ALL TASKS OF UNSUBSCRIBED TENANTS (ANDERE TENANT TASKS LISTE UI)
//	(Sortierung: nach Startdatum - die die am frühesten starten zuerst)
//	GET /core/task/tenant/unsubscribed
//	Req: {}
//	Res: Task[]
	@GetMapping("/core/task/tenant/unsubscribed")
	private ResponseEntity<Object> getTasksOfUnsubscribedTenants(@RequestHeader("Authorization") String authorization) {
		
		List<Marketplace> marketplaces = marketplaceService.findAll();
		if (marketplaces == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_FOUND_MARKETPLACE),
					HttpStatus.BAD_REQUEST);
		}

		CoreUser user = loginService.getLoggedInUser();

		// TODO DEBUG
//		if (user == null) {
//			user = coreUserService.getByUserName("mweixlbaumer");
//		}
		// ----

		List<Tenant> tenants = tenantService.getUnsubscribedTenants(user);
		
		if (tenants == null) {
			return ResponseEntity.ok(new LinkedList<>());
		}

		List<TaskInstance> taskInstances = new LinkedList<>();

		for (Marketplace mp : marketplaces) {
			List<TaskInstance> ret = taskInstanceRestClient.getTaskInstancesByTenant(mp.getUrl(),
					tenants.stream().map(t -> t.getId()).collect(Collectors.toList()), authorization);
			if (ret != null) {
				taskInstances.addAll(ret);
			}
		}
		
		List<XTask> tasks = new LinkedList<>();
		
		for (TaskInstance ti : taskInstances) {
			List<CoreUser> users = coreUserService.findByIds(ti.getSubscribedVolunteerIds());
			tasks.add(xTaskInstanceToTaskMapper.toTarget(ti, users));		
		}
		
		return ResponseEntity.ok(tasks);
	}

}
