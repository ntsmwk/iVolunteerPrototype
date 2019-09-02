package at.jku.cis.iVolunteer.model.meta.core.class_.dtos;

import java.util.List;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.ClassPropertyDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.dto.PropertyDTO;
import at.jku.cis.iVolunteer.model.meta.matching.MatchingRule;

public class ClassDefinitionDTO {

	String id;
	String parentId;
	String name;

//TODO
//	List<ClassPropertyDTO<Object>> properties;
	
	List<PropertyDTO<Object>> properties;
	List<MatchingRule> matchingRules;
	
	public ClassDefinitionDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

//TODO
//	public List<ClassPropertyDTO<Object>> getProperties() {
//		return properties;
//	}
//
//	public void setProperties(List<ClassPropertyDTO<Object>> properties) {
//		this.properties = properties;
//	}
//	
	
	public List<PropertyDTO<Object>> getProperties() {
	return properties;
}

public void setProperties(List<PropertyDTO<Object>> properties) {
	this.properties = properties;
}

	
	
	
	public List<MatchingRule> getMatchingRules() {
		return matchingRules;
	}

	public void setMatchingRules(List<MatchingRule> matchingRules) {
		this.matchingRules = matchingRules;
	}
	
	
	
	
	
}
