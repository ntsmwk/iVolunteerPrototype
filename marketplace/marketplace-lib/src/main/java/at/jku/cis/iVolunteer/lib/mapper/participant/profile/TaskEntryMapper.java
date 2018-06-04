package at.jku.cis.iVolunteer.lib.mapper.participant.profile;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.lib.mapper.AbstractSpringMapper;
import at.jku.cis.iVolunteer.model.participant.profile.TaskEntry;
import at.jku.cis.iVolunteer.model.participant.profile.dto.TaskEntryDTO;

@Service
public class TaskEntryMapper extends AbstractSpringMapper<TaskEntry, TaskEntryDTO> {

	public TaskEntryMapper() {
		super(TaskEntry.class, TaskEntryDTO.class);
	}
}
