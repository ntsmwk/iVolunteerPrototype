package at.jku.cis.iVolunteer.configurator.model.meta.core.property;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Tuple<X, Y> {

	private X id;

	private Y label;

	public Tuple(X id, Y label) {
		this.id = id;
		this.label = label;
	}

	public Tuple() {
	}

	public X getId() {
		return id;
	}

	public void setId(X id) {
		this.id = id;
	}

	public Y getLabel() {
		return label;
	}

	public void setLabel(Y label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "(" + id + ", " + label + ")";
	}

}
