package at.jku.cis.iVolunteer.marketplace.task;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.marketplace.competence.Competence;

@Document
public class Task {

	@Id
	private String id;
	private String name;
	private String description;
	private String workflowKey;
	private Address address;
	private Material material;
	private TaskStatus status;
	@DBRef
	private Task parent;
	private Date startDate;
	private Date endDate;
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

	public String getWorkflowKey() {
		return workflowKey;
	}

	public void setWorkflowKey(String workflowKey) {
		this.workflowKey = workflowKey;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public Task getParent() {
		return parent;
	}

	public void setParent(Task parent) {
		this.parent = parent;
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
		if (!(obj instanceof Task)) {
			return false;
		}
		return ((Task) obj).id.equals(id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
