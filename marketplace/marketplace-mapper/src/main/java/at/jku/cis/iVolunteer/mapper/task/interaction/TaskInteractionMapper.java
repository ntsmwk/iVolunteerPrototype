package at.jku.cis.iVolunteer.mapper.task.interaction;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.task.TaskMapper;
import at.jku.cis.iVolunteer.mapper.user.EmployeeMapper;
import at.jku.cis.iVolunteer.mapper.user.VolunteerMapper;
import at.jku.cis.iVolunteer.model.task.TaskOperation;
import at.jku.cis.iVolunteer.model.task.interaction.String2TaskOperationConverter;
import at.jku.cis.iVolunteer.model.task.interaction.TaskInteraction;
import at.jku.cis.iVolunteer.model.task.interaction.dto.TaskInteractionDTO;
import at.jku.cis.iVolunteer.model.user.Employee;
import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.Volunteer;
import at.jku.cis.iVolunteer.model.user.dto.EmployeeDTO;
import at.jku.cis.iVolunteer.model.user.dto.UserDTO;
import at.jku.cis.iVolunteer.model.user.dto.VolunteerDTO;

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

	protected UserDTO mapToParticipantDTO(User participant) {
		if (participant instanceof Employee) {
			return employeeMapper.toDTO((Employee) participant);
		}
		return volunteerMapper.toDTO((Volunteer) participant);
	}

	protected User mapToParticipant(UserDTO participant) {
		if (participant instanceof EmployeeDTO) {
			return employeeMapper.toEntity((EmployeeDTO) participant);
		}
		return volunteerMapper.toEntity((VolunteerDTO) participant);
	}

}
