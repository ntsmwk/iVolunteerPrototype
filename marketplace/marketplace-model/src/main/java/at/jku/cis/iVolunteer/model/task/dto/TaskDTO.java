package at.jku.cis.iVolunteer.model.task.dto;

import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;

import at.jku.cis.iVolunteer.model.competence.dto.CompetenceDTO;
import at.jku.cis.iVolunteer.model.hash.IHashObject;
import at.jku.cis.iVolunteer.model.project.dto.ProjectDTO;
import at.jku.cis.iVolunteer.model.task.TaskStatus;

public class TaskDTO implements IHashObject {
	private String id;
	private String name;
	//private NamePropertyDTO name;
	private String description;
	private String workflowKey;
	private String marketplaceId;
	private TaskStatus status;

	private Date startDate;
	private Date endDate;
	private List<CompetenceDTO> acquirableCompetences;
	private List<CompetenceDTO> requiredCompetences;

	private ProjectDTO project;

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

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}

	@Override
	public String toHashObject() {
		JsonObject json = new JsonObject();
		json.addProperty("id", id);
		json.addProperty("name", name);
		json.addProperty("description", description);
		json.addProperty("marketplaceId", marketplaceId);
		// json.addProperty("parent", parent.getId());
		json.addProperty("startDate", startDate.toString());
		// json.addProperty("endDate", endDate.toString());
		// json.addProperty("acquirableCompetences", acquirableCompetences.toString());
		// json.addProperty("requiredCompetences", requiredCompetences.toString());
		return json.toString();
	}

	@Override
	public String toString() {
		return "TaskDTO [id=" + id + ", name=" + name + ", description=" + description + ", workflowKey=" + workflowKey
				+ ", marketplaceId=" + marketplaceId + ", status=" + status + ", startDate=" + startDate + ", endDate="
				+ endDate + ", acquirableCompetences=" + acquirableCompetences + ", requiredCompetences="
				+ requiredCompetences + ", project=" + project + "]";
	}

}
