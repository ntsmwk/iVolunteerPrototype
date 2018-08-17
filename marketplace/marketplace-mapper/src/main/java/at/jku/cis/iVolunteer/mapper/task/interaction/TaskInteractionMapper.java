package at.jku.cis.iVolunteer.mapper.task.interaction;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.participant.EmployeeMapper;
import at.jku.cis.iVolunteer.mapper.participant.VolunteerMapper;
import at.jku.cis.iVolunteer.mapper.task.TaskMapper;
import at.jku.cis.iVolunteer.model.participant.Employee;
import at.jku.cis.iVolunteer.model.participant.Participant;
import at.jku.cis.iVolunteer.model.participant.Volunteer;
import at.jku.cis.iVolunteer.model.participant.dto.EmployeeDTO;
import at.jku.cis.iVolunteer.model.participant.dto.ParticipantDTO;
import at.jku.cis.iVolunteer.model.participant.dto.VolunteerDTO;
import at.jku.cis.iVolunteer.model.task.TaskOperation;
import at.jku.cis.iVolunteer.model.task.interaction.String2TaskOperationConverter;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.task.interaction.dto.TaskInteractionDTO;

@Mapper(uses = { TaskMapper.class })
public abstract class TaskInteractionMapper implements AbstractMapper<TaskInteraction, TaskInteractionDTO> {

	@Autowired
	private VolunteerMapper volunteerMapper;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private String2TaskOperationConverter string2TaskOperationConverter;

	protected String map(TaskOperation value) {
		return value.name();
	}

	protected TaskOperation map(String value) {
		return string2TaskOperationConverter.convert(value);
	}

	protected ParticipantDTO mapToParticipantDTO(Participant participant) {
		if (participant instanceof Employee) {
			return employeeMapper.toDTO((Employee) participant);
		}
		return volunteerMapper.toDTO((Volunteer) participant);
	}

	protected Participant mapToParticipant(ParticipantDTO participant) {
		if (participant instanceof EmployeeDTO) {
			return employeeMapper.toEntity((EmployeeDTO) participant);
		}
		return volunteerMapper.toEntity((VolunteerDTO) participant);
	}

}
