package at.jku.cis.trustifier.model.competence;

public class Competence {

	private String id;
	private String name;

	public Competence() {
	}

	public Competence(String name) {
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
