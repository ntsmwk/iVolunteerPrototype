package at.jku.csi.marketplace.task.interaction;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import at.jku.csi.marketplace.task.TaskStatus;

@ReadingConverter
public class String2TaskOperationConverter implements Converter<String, TaskOperation> {

	@Override
	public TaskOperation convert(String value) {
		try {
			if (TaskStatus.valueOf(value) != null) {
				return TaskStatus.valueOf(value);
			}
		} catch (IllegalArgumentException e) {
		}
		try {
			if (TaskVolunteerOperation.valueOf(value) != null) {
				return TaskVolunteerOperation.valueOf(value);
			}
		} catch (IllegalArgumentException e) {
		}
		return null;
	}
}
