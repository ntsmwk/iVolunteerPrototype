package at.jku.cis.iVolunteer.mapper.task;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.competence.CompetenceMapper;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;

@Mapper(uses = { CompetenceMapper.class })
public abstract class TaskMapper implements AbstractMapper<Task, TaskDTO> {

}
