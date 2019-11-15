package at.jku.cis.iVolunteer.model.meta.core.class_;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.meta.matching.MatchingRule;

@Document
public class ClassInstance {
	@Id 
	private String id;
	private String classDefinitionId;
	private String parentClassInstanceId;

	private String name;

	private List<PropertyInstance<Object>> properties;
	private List<MatchingRule> matchingRules;

	public ClassInstance() {
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

	public void setParentClassInstanceId(String parentClassInstanceId) {
		this.parentClassInstanceId = parentClassInstanceId;
	}

	public String getParentClassInstanceId() {
		return parentClassInstanceId;
	}

	public void setClassDefinitionId(String classDefinitionId) {
		this.classDefinitionId = classDefinitionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PropertyInstance<Object>> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyInstance<Object>> properties) {
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
		if (!(obj instanceof ClassInstance))
			return false;
		return ((ClassInstance) obj).id.equals(id);
	}
}
