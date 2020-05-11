package at.jku.cis.iVolunteer.model.meta.core.property;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Tuple<X, Y> {

	public X x;

	public Y y;

	public Tuple(X x, Y y) {
		this.x = x;
		this.y = y;
	}

	public Tuple() {
	}

	public X getX() {
		return x;
	}

	public void setX(X x) {
		this.x = x;
	}

	public Y getY() {
		return y;
	}

	public void setY(Y y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
