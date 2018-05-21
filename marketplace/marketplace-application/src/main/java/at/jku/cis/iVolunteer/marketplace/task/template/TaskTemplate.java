package at.jku.cis.iVolunteer.marketplace.task.template;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.marketplace.competence.Competence;
import at.jku.cis.iVolunteer.marketplace.task.Address;

@Document
public class TaskTemplate {

	
	@Id
	private String id;
	private String name;
	private String description;
	private Address address;	
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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
		if (!(obj instanceof TaskTemplate)) {
			return false;
		}
		return ((TaskTemplate) obj).id.equals(id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
