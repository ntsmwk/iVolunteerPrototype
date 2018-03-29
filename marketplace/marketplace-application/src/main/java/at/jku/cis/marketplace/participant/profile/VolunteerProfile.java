package at.jku.cis.marketplace.participant.profile;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.marketplace.participant.Volunteer;

@Document
public class VolunteerProfile {

	@Id
	private String id;
	@DBRef
	private Volunteer volunteer;

	private Set<TaskEntry> taskList = new HashSet<>();
	private Set<CompetenceEntry> competenceList = new HashSet<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Volunteer getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	public Set<CompetenceEntry> getCompetenceList() {
		return competenceList;
	}

	public void setCompetenceList(Set<CompetenceEntry> competenceList) {
		this.competenceList = competenceList;
	}

	public Set<TaskEntry> getTaskList() {
		return taskList;
	}

	public void setTaskList(Set<TaskEntry> taskList) {
		this.taskList = taskList;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof VolunteerProfile)) {
			return false;
		}
		return ((VolunteerProfile) obj).id.equals(id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
