package at.jku.cis.iVolunteer.model.meta.core.class_.dtos;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.property.instance.dto.PropertyInstanceDTO;
import at.jku.cis.iVolunteer.model.meta.matching.MatchingRule;

public class ClassInstanceDTO {
	private String id;
	private String classDefinitionId;
	private String parentClassInstanceId;

	private String name;
	private List<PropertyInstanceDTO<Object>> properties;
	private List<MatchingRule> matchingRules;

	public ClassInstanceDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassDefinitionId() {
		return classDefinitionId;
	}

	public void setClassDefinitionId(String classDefinitionId) {
		this.classDefinitionId = classDefinitionId;
	}

	public String getParentClassInstanceId() {
		return parentClassInstanceId;
	}

	public void setParentClassInstanceId(String parentClassInstanceId) {
		this.parentClassInstanceId = parentClassInstanceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PropertyInstanceDTO<Object>> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyInstanceDTO<Object>> properties) {
		this.properties = properties;
	}

	public List<MatchingRule> getMatchingRules() {
		return matchingRules;
	}

	public void setMatchingRules(List<MatchingRule> matchingRules) {
		this.matchingRules = matchingRules;
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
