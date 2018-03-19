package at.jku.csi.marketplace.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.csi.marketplace.blockchain.BlockchainRestClient;
import at.jku.csi.marketplace.exception.BadRequestException;
import at.jku.csi.marketplace.participant.Volunteer;
import at.jku.csi.marketplace.participant.VolunteerRepository;
import at.jku.csi.marketplace.security.LoginService;
import at.jku.csi.marketplace.task.interaction.TaskInteraction;
import at.jku.csi.marketplace.task.interaction.TaskInteractionController;
import at.jku.csi.marketplace.task.interaction.TaskInteractionRepository;

@RestController
public class TaskOperationController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskInteractionRepository taskInteractionRepository;

	@Autowired
	private VolunteerRepository volunteerRepository;

	@Autowired
	private BlockchainRestClient blockchainRestClient;
	
	@Autowired
	private TaskInteractionController taskInteractionController;

	@PostMapping("/task/{id}/start")
	public void startTask(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null || task.getStatus() != TaskStatus.CREATED) {
			throw new BadRequestException();
		}
		updateTaskStatus(task, TaskStatus.RUNNING);
	}

	@PostMapping("/task/{id}/suspend")
	public void suspendTask(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null || task.getStatus() != TaskStatus.RUNNING) {
			throw new BadRequestException();
		}
		updateTaskStatus(task, TaskStatus.SUSPENDED);
	}

	@PostMapping("/task/{id}/resume")
	public void resumeTask(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null || task.getStatus() != TaskStatus.SUSPENDED) {
			throw new BadRequestException();
		}
		updateTaskStatus(task, TaskStatus.RUNNING);
	}

	
	// {hash: 'sadfasdfasdf'}
	// 'asdfasdfasdf'
	
	
	@PostMapping("/task/{id}/finish")
	public void finishTask(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null || task.getStatus() != TaskStatus.RUNNING) {
			throw new BadRequestException(); 
		}
		TaskInteraction taskInteraction = updateTaskStatus(task, TaskStatus.FINISHED);
		recordAcquiredCompetencies(task);
		writeTaskToBc(taskInteraction);
	}

	private void writeTaskToBc(TaskInteraction taskInteraction) {
		CompletedTask completedTask = CompletedTaskBuilder.build(taskInteraction);
		blockchainRestClient.postSimpleHash(completedTask);
	}

	@PostMapping("/task/{id}/abort")
	public void abortTask(@PathVariable("id") String id) {
		Task task = taskRepository.findOne(id);
		if (task == null || task.getStatus() == TaskStatus.FINISHED) {
			throw new BadRequestException();
		}
		updateTaskStatus(task, TaskStatus.ABORTED);
	}

	private TaskInteraction updateTaskStatus(Task task, TaskStatus taskStatus) {
		task.setStatus(taskStatus);
		Task updatedTask = taskRepository.save(task);
		return insertTaskInteraction(updatedTask);
	}

	private TaskInteraction insertTaskInteraction(Task task) {
		TaskInteraction taskInteraction = new TaskInteraction();
		taskInteraction.setTask(task);
		taskInteraction.setParticipant(loginService.getLoggedInParticipant());
		taskInteraction.setTimestamp(new Date());
		taskInteraction.setOperation(task.getStatus());
		taskInteractionRepository.insert(taskInteraction);
		
		return taskInteraction;
	}

	private void recordAcquiredCompetencies(Task task) {
		taskInteractionController.findAssignedVolunteersByTaskId(task.getId()).stream()
				.forEach(volunteer -> recordAcquiredCompetencies(task, (Volunteer) volunteer));
	}

	private void recordAcquiredCompetencies(Task task, Volunteer volunteer) {
		task.getType().getAcquirableCompetences().stream()
				.forEach(competence ->  volunteer.getCompetenceList().add(competence));
		volunteerRepository.save(volunteer);
	}
}
