package at.jku.csi.marketplace.competence;

import org.springframework.data.annotation.Id;

public class Competence {

	@Id
	private String id;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
