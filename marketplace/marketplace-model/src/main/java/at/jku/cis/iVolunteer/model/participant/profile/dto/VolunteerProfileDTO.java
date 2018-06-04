package at.jku.cis.iVolunteer.model.participant.profile.dto;

import java.util.HashSet;
import java.util.Set;

import at.jku.cis.iVolunteer.model.participant.dto.VolunteerDTO;

public class VolunteerProfileDTO {

	private String id;
	private VolunteerDTO volunteer;

	private Set<TaskEntryDTO> taskList = new HashSet<>();
	private Set<CompetenceEntryDTO> competenceList = new HashSet<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public VolunteerDTO getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(VolunteerDTO volunteer) {
		this.volunteer = volunteer;
	}

	public Set<CompetenceEntryDTO> getCompetenceList() {
		return competenceList;
	}

	public void setCompetenceList(Set<CompetenceEntryDTO> competenceList) {
		this.competenceList = competenceList;
	}

	public Set<TaskEntryDTO> getTaskList() {
		return taskList;
	}

	public void setTaskList(Set<TaskEntryDTO> taskList) {
		this.taskList = taskList;
	}
}
