package at.jku.cis.iVolunteer.marketplace.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace._mapper.task.ClassInstanceToTaskInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceService;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.task.TaskInstance;
import at.jku.cis.iVolunteer.model.user.User;


//TODO xnet done - test
@RestController
@RequestMapping("/task")
public class TaskInstanceController {

	@Autowired private ClassInstanceService classInstanceService;
	@Autowired private LoginService loginService;
	@Autowired private ClassInstanceToTaskInstanceMapper classInstanceToTaskInstanceMapper;
	
	/** ADD OR REDO
	 * task/all 
	 * task/tenantId/tid/all
	 * task/tenantId/tid/subscribed
	 * task/tenantId/tid/unsubscribed
	 */
	
	@GetMapping("/all")
	public List<TaskInstance> getAllTaskInstances() {
		List<ClassInstance> classInstances = classInstanceService.getAllClassInstances();
		return classInstanceToTaskInstanceMapper.toTargets(classInstances);
	}

	@GetMapping("/tenant/{tenantId}/all")
	public List<TaskInstance> getTaskClassInstancesByTenantId(@PathVariable String tenantId) {
		List<ClassInstance> classInstances = classInstanceService.getClassInstanceByArchetype(ClassArchetype.TASK, tenantId);
		return classInstanceToTaskInstanceMapper.toTargets(classInstances);
	}

	@GetMapping("/tenant/{tenantId}/subscribed")
	public List<TaskInstance> getSubscribedTaskClassInstancesByTenantId(@PathVariable String tenantId) {
		User user = loginService.getLoggedInUser();
	
		//TODO DEBUG TESTING
//		user = new User();
//		user.setId("5f71ca22e5ccdd629ee45d47");
		//--------
		if (user == null) {
			return null;
		}
		List<ClassInstance> classInstances = classInstanceService.getClassInstanceByArcheTypeAndUserIdAndTenantIdAndSubscribed(ClassArchetype.TASK, user.getId(), tenantId, true);
		return classInstanceToTaskInstanceMapper.toTargets(classInstances);
	}
	
	@GetMapping("/tenant/{tenantId}/unsubscribed")
	public List<TaskInstance> getUnsubscribedTaskClassInstancesByTenantId(@PathVariable String tenantId) {
		User user = loginService.getLoggedInUser();
	
		//TODO DEBUG TESTING
//		user = new User();
//		user.setId("5f71ca22e5ccdd629ee45d47");
		//--------
		if (user == null) {
			return null;
		}
		List<ClassInstance> classInstances = classInstanceService.getClassInstanceByArcheTypeAndUserIdAndTenantIdAndSubscribed(ClassArchetype.TASK, user.getId(), tenantId, false);
		return classInstanceToTaskInstanceMapper.toTargets(classInstances);
	}

	@GetMapping("/subscribed")
	public List<TaskInstance> getSubscribedTaskClassInstances() {
		User user = loginService.getLoggedInUser();
		
		//TODO DEBUG TESTING
//		user = new User();
//		user.setId("5f71ca22e5ccdd629ee45d47");
		//--------
		if (user == null) {
			return null;
		}
		List<ClassInstance> classInstances = classInstanceService.getClassInstanceByArcheTypeAndUserIdAndSubscribed(ClassArchetype.TASK, user.getId(), true);
		return classInstanceToTaskInstanceMapper.toTargets(classInstances);
	}
	
	@GetMapping("/unsubscribed")
	public List<TaskInstance> getUnsubscribedTaskClassInstances() {
		User user = loginService.getLoggedInUser();
		
		//TODO DEBUG TESTING
//		user = new User();
//		user.setId("5f71ca22e5ccdd629ee45d47");
		//--------
		if (user == null) {
			return null;
		}
		List<ClassInstance> classInstances = classInstanceService.getClassInstanceByArcheTypeAndUserIdAndSubscribed(ClassArchetype.TASK, user.getId(), false);
		return classInstanceToTaskInstanceMapper.toTargets(classInstances);
	}


	
	
	@GetMapping("/{taskId}")
	public TaskInstance getTask(@PathVariable String taskId) {
		ClassInstance classInstance = classInstanceService.getClassInstanceById(taskId);
		return classInstanceToTaskInstanceMapper.toTarget(classInstance);
	}
	

	@PutMapping("/{taskId}/subscribe")
	 public ResponseEntity<Object> subscribeToTask(@PathVariable String taskId) {
		if (taskId == null) {
			return new ResponseEntity<Object>("taskId must not be null", HttpStatus.NOT_ACCEPTABLE);
		}
		ClassInstance classInstance = classInstanceService.getClassInstanceById(taskId);
		if (classInstance == null) {
			return new ResponseEntity<Object>("no such task", HttpStatus.NOT_ACCEPTABLE);
		}
		
		classInstance.setSubscribed(true);
		classInstance = classInstanceService.saveClassInstance(classInstance);
		return new ResponseEntity<Object>(classInstanceToTaskInstanceMapper.toTarget(classInstance), HttpStatus.OK);
		
	}
	
	@PutMapping("/{taskId}/unsubscribe")
	 public ResponseEntity<Object> unsubscribeFromTask(@PathVariable String taskId) {
		if (taskId == null) {
			return new ResponseEntity<Object>("taskId must not be null", HttpStatus.NOT_ACCEPTABLE);
		}
		
		ClassInstance classInstance = classInstanceService.getClassInstanceById(taskId);
		if (classInstance == null) {
			return new ResponseEntity<Object>("no such task", HttpStatus.NOT_ACCEPTABLE);
		}
		
		classInstance.setSubscribed(false);
		classInstance = classInstanceService.saveClassInstance(classInstance);
		return new ResponseEntity<Object>(classInstanceToTaskInstanceMapper.toTarget(classInstance), HttpStatus.OK);
	}
	
	
	

	@PostMapping("/new") 
//	return id
	public TaskInstance createTask(@RequestBody TaskInstance task) {
		ClassInstance classInstance = classInstanceToTaskInstanceMapper.toSource(task);
		classInstance = classInstanceService.saveClassInstance(classInstance);
		return classInstanceToTaskInstanceMapper.toTarget(classInstance);
	}

// TODO xnet	200 oder 400 und put
	@PostMapping("/{taskId}")
	public ResponseEntity<Object> updateTask(@PathVariable String taskId, @RequestBody TaskInstance task) {
		if (task == null || taskId == null) {
			return new ResponseEntity<Object>("task / taskId must not be null", HttpStatus.NOT_ACCEPTABLE);
		}
		
		task.getRequired().setId(taskId);
		ClassInstance classInstance = classInstanceToTaskInstanceMapper.toSource(task);
		classInstance = classInstanceService.saveClassInstance(classInstance);
		return new ResponseEntity<Object>(classInstanceToTaskInstanceMapper.toTarget(classInstance), HttpStatus.OK);

	}
}
