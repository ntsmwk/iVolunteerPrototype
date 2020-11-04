package at.jku.cis.iVolunteer.core.task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

@RestController
public class XCoreTaskController {

	@Autowired TaskInstanceRestClient taskInstanceRestClient;
	@Autowired MarketplaceService marketplaceService;
	@Autowired XTaskInstanceToTaskMapper xTaskInstanceToTaskMapper;
	@Autowired CoreUserService coreUserService;
	@Autowired TenantService tenantService;
	@Autowired LoginService loginService;

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

		List<XTask> tasks = mapToXTasks(taskInstances);
		return ResponseEntity.ok(tasks);
	}

	@GetMapping("/task/{year}")
	private ResponseEntity<Object> getTasksByYear(@PathVariable("year") int year,
			@RequestHeader("Authorization") String authorization) {

		List<Marketplace> marketplaces = marketplaceService.findAll();

		if (marketplaces == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_FOUND_MARKETPLACE),
					HttpStatus.BAD_REQUEST);
		}

		List<TaskInstance> taskInstances = new ArrayList<>();

		for (Marketplace mp : marketplaces) {
			List<TaskInstance> ret = taskInstanceRestClient.getTaskInstancesByYear(mp.getUrl(), year, authorization);
			if (ret != null) {
				taskInstances.addAll(ret);
			}
		}

		List<XTask> tasks = mapToXTasks(taskInstances);
		return ResponseEntity.ok(tasks);
	}

	@GetMapping("/task/tenant/subscribed")
	private ResponseEntity<Object> getTasksOfSubscribedTenants(@RequestHeader("Authorization") String authorization) {
		List<Marketplace> marketplaces = marketplaceService.findAll();
		if (marketplaces == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_FOUND_MARKETPLACE),
					HttpStatus.BAD_REQUEST);
		}
		CoreUser user = loginService.getLoggedInUser();
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
		List<XTask> tasks = mapToXTasks(taskInstances);
		return ResponseEntity.ok(tasks);
	}

	@GetMapping("/task/tenant/subscribed/{year}")
	private ResponseEntity<Object> getTasksOfSubscribedTenantsByYear(@PathVariable("year") int year,
			@RequestHeader("Authorization") String authorization) {
		List<Marketplace> marketplaces = marketplaceService.findAll();
		if (marketplaces == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_FOUND_MARKETPLACE),
					HttpStatus.BAD_REQUEST);
		}

		CoreUser user = loginService.getLoggedInUser();

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
			List<TaskInstance> ret = taskInstanceRestClient.getTaskInstancesByTenantByYear(mp.getUrl(),
					tenants.stream().map(t -> t.getId()).collect(Collectors.toList()), year, authorization);
			if (ret != null) {
				taskInstances.addAll(ret);
			}
		}

		List<XTask> tasks = mapToXTasks(taskInstances);
		return ResponseEntity.ok(tasks);
	}

	@GetMapping("/task/tenant/unsubscribed")
	private ResponseEntity<Object> getTasksOfUnsubscribedTenants(@RequestHeader("Authorization") String authorization) {

		List<Marketplace> marketplaces = marketplaceService.findAll();
		if (marketplaces == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_FOUND_MARKETPLACE),
					HttpStatus.BAD_REQUEST);
		}

		CoreUser user = loginService.getLoggedInUser();

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

		List<XTask> tasks = mapToXTasks(taskInstances);
		return ResponseEntity.ok(tasks);
	}

	@GetMapping("/task/tenant/unsubscribed/{year}")
	private ResponseEntity<Object> getTasksOfUnsubscribedTenantsByYear(@PathVariable int year,
			@RequestHeader("Authorization") String authorization) {

		List<Marketplace> marketplaces = marketplaceService.findAll();
		if (marketplaces == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_FOUND_MARKETPLACE),
					HttpStatus.BAD_REQUEST);
		}

		CoreUser user = loginService.getLoggedInUser();

		List<Tenant> tenants = tenantService.getUnsubscribedTenants(user);

		if (tenants == null) {
			return ResponseEntity.ok(new LinkedList<>());
		}

		List<TaskInstance> taskInstances = new LinkedList<>();

		for (Marketplace mp : marketplaces) {
			List<TaskInstance> ret = taskInstanceRestClient.getTaskInstancesByTenantByYear(mp.getUrl(),
					tenants.stream().map(t -> t.getId()).collect(Collectors.toList()), year, authorization);
			if (ret != null) {
				taskInstances.addAll(ret);
			}
		}

		List<XTask> tasks = mapToXTasks(taskInstances);
		return ResponseEntity.ok(tasks);
	}

	private List<XTask> mapToXTasks(List<TaskInstance> taskInstances) {
		List<XTask> tasks = new LinkedList<>();
		for (TaskInstance ti : taskInstances) {
			List<CoreUser> users = coreUserService.findByIds(ti.getSubscribedVolunteerIds());
			tasks.add(xTaskInstanceToTaskMapper.toTarget(ti, users));
		}
		return tasks;
	}

}
