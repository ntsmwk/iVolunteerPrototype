package at.jku.cis.iVolunteer.marketplace.task;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

@Service
public class XTaskInstanceService {

	@Autowired XTaskInstanceRepository xTaskInstanceRepository;

	public TaskInstance getTaskInstance(String id) {
		return xTaskInstanceRepository.findOne(id);
	}

	public List<TaskInstance> getAll() {
		return xTaskInstanceRepository.findAll();
	}

	public List<TaskInstance> getAllByYear(int year) {
		List<TaskInstance> taskInstances = xTaskInstanceRepository.findAll();
		List<TaskInstance> filteredList = taskInstances.stream().filter(tI -> {
			PropertyInstance<Object> startDateProperty = tI.findProperty("Starting Date");
			if (startDateProperty != null && startDateProperty.getValues().size() == 1) {
				LocalDate date = LocalDate.parse("" + startDateProperty.getValues().get(0));
				return date.getYear() == year;
			}
			return false;
		}).collect(Collectors.toList());
		return filteredList;
	}

	public List<TaskInstance> getTaskInstance(List<String> ids) {
		if (ids == null) {
			return null;
		}
		List<TaskInstance> instances = new LinkedList<>();
		xTaskInstanceRepository.findAll(ids).forEach(instances::add);
		return instances;
	}

	public List<TaskInstance> getTaskInstanceByTenantId(String tenantId) {
		if (tenantId == null) {
			return null;
		}
		List<TaskInstance> instances = xTaskInstanceRepository.findByIssuerId(tenantId);
		return instances;
	}

	public TaskInstance addOrOverwriteTaskInstance(TaskInstance taskInstance) {
		if (taskInstance == null) {
			return null;
		}
		return xTaskInstanceRepository.save(taskInstance);
	}

	public TaskInstance updateTaskInstance(TaskInstance newTaskInstance) {
		TaskInstance existingTaskInstance = xTaskInstanceRepository.findOne(newTaskInstance.getId());
		return updateTaskInstance(newTaskInstance, existingTaskInstance);
	}

	public TaskInstance updateTaskInstance(TaskInstance newTaskInstance, TaskInstance existingTaskInstance) {
		if (newTaskInstance == null || existingTaskInstance == null) {
			return null;
		}
		TaskInstance updateInstance = existingTaskInstance.updateTaskInstance(newTaskInstance);

		return xTaskInstanceRepository.save(updateInstance);
	}

	public void deleteTaskInstance(String id) {
		xTaskInstanceRepository.delete(id);
	}
}
