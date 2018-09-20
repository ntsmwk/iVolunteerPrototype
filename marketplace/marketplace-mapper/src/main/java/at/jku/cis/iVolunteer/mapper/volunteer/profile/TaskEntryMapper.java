package at.jku.cis.iVolunteer.mapper.volunteer.profile;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.volunteer.profile.TaskEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.TaskEntryDTO;

@Mapper
public abstract class TaskEntryMapper implements AbstractMapper<TaskEntry, TaskEntryDTO> {

	
}
