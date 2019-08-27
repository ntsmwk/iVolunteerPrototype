package at.jku.cis.iVolunteer.model.meta.core.class_;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.Property;
import at.jku.cis.iVolunteer.model.meta.matching.MatchingRule;

@Document
public class ClassInstance {
	@Id
	String id;
	
	String classId;
	
	List<Property> properties;
	List<MatchingRule> matchingRules;
	
	
	public ClassInstance() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ClassInstance))
			return false;
		return ((ClassInstance) obj).id.equals(id);
	}
}
