package at.jku.cis.iVolunteer.model.task.template;

import java.util.List;

import at.jku.cis.iVolunteer.model.competence.dto.CompetenceDTO;

public class TaskTemplateDTO {

	private String id;
	private String name;
	private String description;
	private String workflowKey;
	private List<CompetenceDTO> acquirableCompetences;
	private List<CompetenceDTO> requiredCompetences;

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

	public String getWorkflowKey() {
		return workflowKey;
	}

	public void setWorkflowKey(String workflowKey) {
		this.workflowKey = workflowKey;
	}

	public List<CompetenceDTO> getAcquirableCompetences() {
		return acquirableCompetences;
	}

	public void setAcquirableCompetences(List<CompetenceDTO> acquirableCompetences) {
		this.acquirableCompetences = acquirableCompetences;
	}

	public List<CompetenceDTO> getRequiredCompetences() {
		return requiredCompetences;
	}

	public void setRequiredCompetences(List<CompetenceDTO> requiredCompetences) {
		this.requiredCompetences = requiredCompetences;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TaskTemplateDTO)) {
			return false;
		}
		return ((TaskTemplateDTO) obj).id.equals(id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
