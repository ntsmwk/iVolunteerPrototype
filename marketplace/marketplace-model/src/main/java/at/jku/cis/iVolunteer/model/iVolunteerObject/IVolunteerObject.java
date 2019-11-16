package at.jku.cis.iVolunteer.model.iVolunteerObject;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

@Document
public class IVolunteerObject {

	@Id public String id;

	private List<PropertyInstance<Object>> propertyInstances;

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

	public List<PropertyInstance<Object>> getPropertyInstances() {
		return propertyInstances;
	}

	public void setPropertyInstances(List<PropertyInstance<Object>> propertyInstances) {
		this.propertyInstances = propertyInstances;
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
