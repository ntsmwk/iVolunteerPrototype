package at.jku.cis.iVolunteer.model.task.dto;

import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;

import at.jku.cis.iVolunteer.model.competence.dto.CompetenceDTO;
import at.jku.cis.iVolunteer.model.hash.IHashObject;

public class TaskDTO implements IHashObject {
	private String id;
	private String name;
	private String description;
	private String marketplaceId;
	private AddressDTO address;
	private MaterialDTO material;
	private TaskDTO parent;
	private Date startDate;
	private Date endDate;
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

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public MaterialDTO getMaterial() {
		return material;
	}

	public void setMaterial(MaterialDTO material) {
		this.material = material;
	}

	public TaskDTO getParent() {
		return parent;
	}

	public void setParent(TaskDTO parent) {
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
	public String toHashObject() {
		JsonObject json = new JsonObject();
		json.addProperty("id", id);
		json.addProperty("name", name);
		json.addProperty("description", description);
		// json.addProperty("address", address.toString());
		// json.addProperty("material", material.toString());
		// json.addProperty("parent", parent.getId());
		json.addProperty("startDate", startDate.toString());
		// json.addProperty("endDate", endDate.toString());
		json.addProperty("acquirableCompetences", acquirableCompetences.toString());
		json.addProperty("requiredCompetences", requiredCompetences.toString());
		return json.toString();
	}
}
