package at.jku.cis.iVolunteer.marketplace.task;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.commons.DateTimeService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstanceStatus;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

@Service
public class XTaskInstanceService {

	@Autowired XTaskInstanceRepository xTaskInstanceRepository;
	@Autowired DateTimeService dateTimeService;

	public TaskInstance getTaskInstance(String id) {
		return xTaskInstanceRepository.findOne(id);
	}

	public List<TaskInstance> getAll(String taskType, int startYear, String status, String loggedInUserId) {
		List<TaskInstance> taskInstances = xTaskInstanceRepository.findAll();
		taskInstances = filterTaskInstancesByTaskType(taskType, taskInstances, loggedInUserId);
		taskInstances = filterTaskInstancesByStartYear(startYear, taskInstances);
		taskInstances = filterTaskInstancesByStatus(status, taskInstances);

		return taskInstances;
	}

	public List<TaskInstance> getTaskInstance(List<String> ids) {
		if (ids == null) {
			return null;
		}
		List<TaskInstance> instances = new LinkedList<>();
		xTaskInstanceRepository.findAll(ids).forEach(instances::add);
		return instances;
	}

	public List<TaskInstance> getTaskInstanceByTenantId(String tenantId, String taskType, int startYear,
			String status, String loggedInUserId) {
		if (tenantId == null) {
			return null;
		}
		List<TaskInstance> taskInstances = xTaskInstanceRepository.findByTenantId(tenantId);
		taskInstances = filterTaskInstancesByTaskType(taskType, taskInstances, loggedInUserId);
		taskInstances = filterTaskInstancesByStartYear(startYear, taskInstances);
		taskInstances = filterTaskInstancesByStatus(status, taskInstances);
		return taskInstances;
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

	private List<TaskInstance> filterTaskInstancesByTaskType(String taskType, List<TaskInstance> taskInstances, String loggedInUserId) {
		if(taskType.equals("SUBSCRIBED")) {
			return taskInstances.stream().filter(ti -> ti.getSubscribedVolunteerIds().contains(loggedInUserId)).collect(Collectors.toList());
		}else if(taskType.equals("UNSUBSCRIBED")) {
			return taskInstances.stream().filter(ti -> !ti.getSubscribedVolunteerIds().contains(loggedInUserId)).collect(Collectors.toList());
		}
		
		return taskInstances;
	}

	private List<TaskInstance> filterTaskInstancesByStartYear(int year, List<TaskInstance> taskInstances) {
		if(year == 0) {
			return taskInstances;
		}
		List<TaskInstance> filteredList = taskInstances.stream().filter(tI -> {
			PropertyInstance<Object> startDateProperty = tI.findProperty("Starting Date");
			if (startDateProperty != null && startDateProperty.getValues().size() == 1) {
				Date date = new Date((long) startDateProperty.getValues().get(0));
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				return calendar.get(Calendar.YEAR) == year;
			}
			return false;
		}).collect(Collectors.toList());
		return filteredList;
	}

	private List<TaskInstance> filterTaskInstancesByStatus(String status, List<TaskInstance> taskInstances) {
		if(status.equals("OPEN")) {
			return taskInstances.stream().filter(ti -> ti.getStatus() == TaskInstanceStatus.OPEN).collect(Collectors.toList());
		}else if (status.equals("CLOSED")){
			return taskInstances.stream().filter(ti -> ti.getStatus() == TaskInstanceStatus.CLOSED).collect(Collectors.toList());
		}
		return taskInstances;
	}
}
