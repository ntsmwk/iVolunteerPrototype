package at.jku.cis.iVolunteer.marketplace.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.core.CoreStorageRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceController;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.marketplace.user.UserController;
import at.jku.cis.iVolunteer.marketplace.user.UserService;
import at.jku.cis.iVolunteer.model._httprequests.PostTaskRequest;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model._httpresponses.HttpErrorMessages;
import at.jku.cis.iVolunteer.model._httpresponses.StringResponse;
import at.jku.cis.iVolunteer.model._mapper.xnet.XTaskInstanceToPostTaskRequestMapper;
import at.jku.cis.iVolunteer.model._mapper.xnet.XTaskInstanceToTaskMapper;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstanceStatus;
import at.jku.cis.iVolunteer.model.task.XTask;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.XUser;

@RestController
@RequestMapping("/task")
public class XTaskController {

	@Autowired
	private ClassInstanceController classInstanceController;
	@Autowired
	private LoginService loginService;
	@Autowired
	private XTaskInstanceToTaskMapper xTaskInstanceToTaskMapper;
	@Autowired
	private XTaskInstanceService xTaskInstanceService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserController userController;
	@Autowired
	private XTaskInstanceToPostTaskRequestMapper xTaskInstanceToPostTaskRequestMapper;
	@Autowired
	private CoreStorageRestClient coreStorageRestClient;

	@PostMapping("/new")
	private ResponseEntity<Object> createOpenedTask(@RequestBody PostTaskRequest task,
			@RequestHeader("Authorization") String authorization) {
		if (task == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse(HttpErrorMessages.BODY_NOT_NULL));
		}

		TaskInstance instance = xTaskInstanceToPostTaskRequestMapper.toSource(task);
		instance.setStatus(TaskInstanceStatus.OPEN);
		if (task.getImage() != null && !task.getImage().isEmpty()) {
			ResponseEntity<StringResponse> resp = coreStorageRestClient.storeImageBase64(null, task.getImage(),
					authorization);

			if (!resp.getStatusCode().is2xxSuccessful()) {
				return ResponseEntity.badRequest().body(new ErrorResponse(resp.getBody().getMessage()));
			}

			String imageUrl = resp.getBody().getMessage();
			instance.setImagePath(imageUrl);
		}
		instance = xTaskInstanceService.addOrOverwriteTaskInstance(instance);

		return ResponseEntity.ok(Collections.singletonMap("id", instance.getId()));
	}

	@PostMapping("/new/closed")
	private ResponseEntity<Object> createClosedTask(@RequestBody PostTaskRequest task,
			@RequestHeader("Authorization") String authorization) {
		if (task == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse(HttpErrorMessages.BODY_NOT_NULL));
		}

		TaskInstance taskInstance = xTaskInstanceToPostTaskRequestMapper.toSource(task);
		taskInstance.setStatus(TaskInstanceStatus.CLOSED);
		if (task.getImage() != null && !task.getImage().isEmpty()) {

			ResponseEntity<StringResponse> resp = coreStorageRestClient.storeImageBase64(null, task.getImage(),
					authorization);

			if (!resp.getStatusCode().is2xxSuccessful()) {
				return ResponseEntity.badRequest().body(new ErrorResponse(resp.getBody().getMessage()));
			}

			String imageUrl = resp.getBody().getMessage();
			taskInstance.setImagePath(imageUrl);
		}
		taskInstance = xTaskInstanceService.addOrOverwriteTaskInstance(taskInstance);

		if (task.getSubscribedUsers() != null) {
			List<ClassInstance> addedClassInstances = new ArrayList<>();
			for (String userId : task.getSubscribedUsers()) {
				ClassInstance classInstance = new ClassInstance(taskInstance);

				classInstance.setUserId(userId);
				classInstance.setIssuerId(task.getTenantId());
				classInstance.setId(null);
				addedClassInstances.add(classInstance);
				// TODO BadgeCertificates ausstellen
			}

			addedClassInstances = classInstanceController.createNewClassInstances(addedClassInstances);

		}
		return ResponseEntity.ok(Collections.singletonMap("id", taskInstance.getId()));

	}

	@PostMapping("/{taskId}/close")
	private ResponseEntity<Object> finalizeTask(@PathVariable("taskId") String taskId) {
		TaskInstance taskInstance = xTaskInstanceService.getTaskInstance(taskId);
		if (taskInstance == null) {
			return ResponseEntity.badRequest().body(HttpErrorMessages.NOT_FOUND_TASK);
		}

		taskInstance.setStatus(TaskInstanceStatus.CLOSED);
		xTaskInstanceService.addOrOverwriteTaskInstance(taskInstance);

		if (taskInstance.getSubscribedVolunteerIds() != null) {
			List<ClassInstance> addedClassInstances = new ArrayList<>();
			for (String id : taskInstance.getSubscribedVolunteerIds()) {
				ClassInstance classInstance = new ClassInstance(taskInstance);

				classInstance.setUserId(id);
				classInstance.setId(null);
				addedClassInstances.add(classInstance);
				// TODO BadgeCertificates ausstellen
			}

			addedClassInstances = classInstanceController.createNewClassInstances(addedClassInstances);

			return ResponseEntity.ok().build();

		} else {
			return ResponseEntity.ok().build();
		}

	}

	@GetMapping("/{taskId}")
	public ResponseEntity<Object> getTaskInstancesById(@PathVariable String taskId) {
		TaskInstance task = xTaskInstanceService.getTaskInstance(taskId);
		if(task == null) {
			return ResponseEntity.badRequest().body(HttpErrorMessages.NOT_FOUND_TASK);
		}
		List<User> users = userService.getUsers(task.getSubscribedVolunteerIds());
		return ResponseEntity.ok(xTaskInstanceToTaskMapper.toTarget(task, users));
	}
	
	@GetMapping("/tenant/{tenantId}")
	public List<XTask> getTaskInstancesByTenantId(@PathVariable String tenantId) {
		List<TaskInstance> tasks = xTaskInstanceService.getTaskInstanceByTenantId(tenantId);
		// TODO fix multiple db accesses...
		return tasks.stream().map(t -> {
			List<User> users = userService.getUsers(t.getSubscribedVolunteerIds());
			return xTaskInstanceToTaskMapper.toTarget(t, users);
		}).collect(Collectors.toList());
	}

	@GetMapping("/tenant/{tenantId}/{year}")
	public List<XTask> getTaskInstancesByTenantIdByYear(@PathVariable String tenantId, @PathVariable int year) {
		List<TaskInstance> tasks = xTaskInstanceService.getTaskInstanceByTenantIdByYear(tenantId, year);
		// TODO fix multiple db accesses...
		return tasks.stream().map(t -> {
			List<User> users = userService.getUsers(t.getSubscribedVolunteerIds());
			return xTaskInstanceToTaskMapper.toTarget(t, users);
		}).collect(Collectors.toList());
	}

	public ResponseEntity<Object> getTask(@PathVariable String taskId) {
		TaskInstance task = xTaskInstanceService.getTaskInstance(taskId);

		if (task == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_FOUND_TASK),
					HttpStatus.NOT_FOUND);
		}

		List<User> users = userService.getUsers(task.getSubscribedVolunteerIds());
		return ResponseEntity.ok(xTaskInstanceToTaskMapper.toTarget(task, users));
	}

	@PostMapping("/update/{taskId}")
	public ResponseEntity<Object> updateTask(@PathVariable String taskId, @RequestBody XTask changes) {
		TaskInstance existingTask = xTaskInstanceService.getTaskInstance(taskId);
		if (existingTask == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_FOUND_TASK),
					HttpStatus.NOT_FOUND);
		} else if (existingTask.getStatus().equals(TaskInstanceStatus.CLOSED)) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.ALREADY_CLOSED),
					HttpStatus.BAD_REQUEST);
		}
		existingTask = xTaskInstanceService.updateTaskInstance(xTaskInstanceToTaskMapper.toSource(changes),
				existingTask);

		return ResponseEntity.ok().build();
	}

	@PostMapping("{taskId}/subscribe")
	public ResponseEntity<Object> subscribeUserToTask(@PathVariable String taskId) {
		User user = loginService.getLoggedInUser();
		User finalUser = user;

		if (user == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_LOGGED_IN),
					HttpStatus.UNAUTHORIZED);
		}
		TaskInstance existingTask = xTaskInstanceService.getTaskInstance(taskId);

		if (existingTask == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_FOUND_TASK),
					HttpStatus.NOT_FOUND);
		}
		if (existingTask.getSubscribedVolunteerIds().stream().anyMatch(id -> id.equals(finalUser.getId()))) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.ALREADY_SUBSCRIBED),
					HttpStatus.BAD_REQUEST);
		}

		existingTask.getSubscribedVolunteerIds().add(user.getId());
		xTaskInstanceService.addOrOverwriteTaskInstance(existingTask);

		return ResponseEntity.ok().build();
	}

	@PostMapping("{taskId}/unsubscribe")
	public ResponseEntity<Object> unsubscribeUserFromTask(@PathVariable String taskId) {
		User user = loginService.getLoggedInUser();

		User finalUser = user;

		if (user == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_LOGGED_IN),
					HttpStatus.UNAUTHORIZED);
		}
		TaskInstance existingTask = xTaskInstanceService.getTaskInstance(taskId);

		if (existingTask == null) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.NOT_FOUND_TASK),
					HttpStatus.NOT_FOUND);
		}

		if (existingTask.getSubscribedVolunteerIds().stream().noneMatch(id -> id.equals(finalUser.getId()))) {
			return new ResponseEntity<Object>(new ErrorResponse(HttpErrorMessages.ALREADY_UNSUBSCRIBED),
					HttpStatus.BAD_REQUEST);
		}

		existingTask.setSubscribedVolunteerIds(existingTask.getSubscribedVolunteerIds().stream()
				.filter(id -> !id.equals(finalUser.getId())).collect(Collectors.toList()));

		xTaskInstanceService.addOrOverwriteTaskInstance(existingTask);

		return ResponseEntity.ok().build();
	}

}
