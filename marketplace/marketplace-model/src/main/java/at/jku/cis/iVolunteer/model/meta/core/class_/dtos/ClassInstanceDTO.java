package at.jku.cis.iVolunteer.model.meta.core.class_.dtos;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.property.dtos.PropertyInstanceDTO;
import at.jku.cis.iVolunteer.model.meta.matching.MatchingRule;

public class ClassInstanceDTO {
	String id;
	
	String classId;
	
	List<PropertyInstanceDTO<?>> properties;
	List<MatchingRule> matchingRules;
	
	
	public ClassInstanceDTO() {
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

	public List<PropertyInstanceDTO<?>> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyInstanceDTO<?>> properties) {
		this.properties = properties;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ClassInstanceDTO))
			return false;
		return ((ClassInstanceDTO) obj).id.equals(id);
	}
}
