package at.jku.cis.iVolunteer.model.iVolunteerObject;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class IVolunteerObject {

	@Id
	public String id;
	
	
	public IVolunteerObject() {
	}
	
	public IVolunteerObject(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IVolunteerObject)) {
			return false;
		}
		return ((IVolunteerObject) obj).id.equals(id);
	}

	
	
	
	
	
	
}
