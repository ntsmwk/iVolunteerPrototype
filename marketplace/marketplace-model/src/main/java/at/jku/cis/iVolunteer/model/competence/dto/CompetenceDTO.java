package at.jku.cis.iVolunteer.model.competence.dto;

public class CompetenceDTO {

	private String id;
	private String name;

	public CompetenceDTO() {
	}

	public CompetenceDTO(String name) {
		this.name = name;
	}

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
}
