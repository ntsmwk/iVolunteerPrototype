package at.jku.cis.trustifier.model.task.type;

import java.util.List;

import at.jku.cis.trustifier.model.competence.Competence;

public class TaskType {

	private String id;
	private String name;
	private String description;

	private List<Competence> acquirableCompetences;
	private List<Competence> requiredCompetences;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Competence> getAcquirableCompetences() {
		return acquirableCompetences;
	}

	public void setAcquirableCompetences(List<Competence> acquirableCompetences) {
		this.acquirableCompetences = acquirableCompetences;
	}

	public List<Competence> getRequiredCompetences() {
		return requiredCompetences;
	}

	public void setRequiredCompetences(List<Competence> requiredCompetences) {
		this.requiredCompetences = requiredCompetences;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TaskType)) {
			return false;
		}
		return ((TaskType) obj).id.equals(id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
