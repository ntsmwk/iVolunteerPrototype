package at.jku.cis.iVolunteer.model.volunteer.profile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassInstance;
import at.jku.cis.iVolunteer.model.user.Volunteer;

@Document
public class VolunteerProfile {

	@Id private String id;
	@DBRef private Volunteer volunteer;

	private Set<TaskEntry> taskList = new HashSet<>();
	private List<CompetenceClassInstance> competenceList = new ArrayList<>();

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

	public Set<TaskEntry> getTaskList() {
		return taskList;
	}

	public void setTaskList(Set<TaskEntry> taskList) {
		this.taskList = taskList;
	}

	public List<CompetenceClassInstance> getCompetenceList() {
		return competenceList;
	}

	public void setCompetenceList(List<CompetenceClassInstance> competenceList) {
		this.competenceList = competenceList;
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
