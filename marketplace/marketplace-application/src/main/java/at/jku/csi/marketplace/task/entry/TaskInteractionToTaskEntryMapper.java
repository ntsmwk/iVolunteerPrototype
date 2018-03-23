package at.jku.csi.marketplace.task.entry;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import at.jku.csi.marketplace.task.interaction.TaskInteraction;

@Mapper()
public interface TaskInteractionToTaskEntryMapper {

	@Mappings({ 
		@Mapping(target = "id", source = "id"), 
		@Mapping(target = "taskId", source = "task.id"),
		@Mapping(target = "taskName", source = "task.type.name"),
		@Mapping(target = "taskDescription", source = "task.type.description"),
		@Mapping(target = "timestamp", source = "timestamp") })
	TaskEntry map(TaskInteraction entity);
}