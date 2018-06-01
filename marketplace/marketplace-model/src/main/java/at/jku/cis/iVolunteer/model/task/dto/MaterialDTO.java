package at.jku.cis.iVolunteer.model.task.dto;

public class MaterialDTO {
	private String name;
	private String description;

	private MaterialDTO() {
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
}
