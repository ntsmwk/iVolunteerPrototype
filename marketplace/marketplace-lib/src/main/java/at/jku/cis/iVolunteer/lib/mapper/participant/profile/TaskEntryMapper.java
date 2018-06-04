package at.jku.cis.iVolunteer.lib.mapper.participant.profile;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.lib.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.participant.profile.TaskEntry;
import at.jku.cis.iVolunteer.model.participant.profile.dto.TaskEntryDTO;

@Mapper
public abstract class TaskEntryMapper implements AbstractMapper<TaskEntry, TaskEntryDTO> {

	
}
