package at.jku.cis.iVolunteer.model.core.tenant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Tag {

	@Id private String label;
	
	public Tag() {};
	
	public Tag(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof Tag)) {
			return false;
		}
		return ((Tag) obj).label.equals(label);
	}

	@Override
	public int hashCode() {
		return label.hashCode();
	}

}
