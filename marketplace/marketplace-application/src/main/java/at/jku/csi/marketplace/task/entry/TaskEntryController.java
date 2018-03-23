package at.jku.csi.marketplace.task.entry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.jku.csi.marketplace.blockchain.BlockchainRestClient;
import at.jku.csi.marketplace.exception.ForbiddenException;
import at.jku.csi.marketplace.task.Task;
import at.jku.csi.marketplace.task.TaskRepository;
import at.jku.csi.marketplace.task.TaskStatus;
import at.jku.csi.marketplace.task.interaction.TaskInteraction;
import at.jku.csi.marketplace.task.interaction.TaskInteractionRepository;

@RestController
public class TaskEntryController {

	@Autowired
	private BlockchainRestClient blockchainRestClient;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskEntryRepository taskEntryRepository;
	@Autowired
	private TaskInteractionRepository taskInteractionRepository;
	@Autowired
	private TaskInteractionToTaskEntryMapper taskInteractionToTaskEntryMapper;

	@GetMapping("/taskEntry/{id}")
	public TaskEntry syncTask(@PathVariable("id") String taskId) {
		Task task = taskRepository.findOne(taskId);
		List<TaskInteraction> taskInteractions = taskInteractionRepository.findByTaskAndOperation(task,
				TaskStatus.FINISHED);

		if (taskInteractions.size() != 1) {
			throw new ForbiddenException();
		}
		return taskEntryRepository.save(taskInteractionToTaskEntryMapper.map(taskInteractions.get(0)));
		//blockchainRestClient.postSimpleHash(taskEntry);
	}

}
