package at.jku.cis.marketplace.task.interaction;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import at.jku.cis.marketplace.task.TaskStatus;

@ReadingConverter
public class String2TaskOperationConverter implements Converter<String, TaskOperation> {

	private final Map<String, TaskOperation> string2TaskOperation = new HashMap<>();

	public String2TaskOperationConverter() {
		initalize();
	}

	private void initalize() {
		for (TaskStatus status : TaskStatus.values()) {
			string2TaskOperation.put(status.name(), status);
		}
		for (TaskVolunteerOperation volunteerOperation : TaskVolunteerOperation.values()) {
			string2TaskOperation.put(volunteerOperation.name(), volunteerOperation);
		}
	}

	@Override
	public TaskOperation convert(String value) {
		return string2TaskOperation.get(value);
	}
}
