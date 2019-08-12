package at.jku.cis.iVolunteer.model.configurable.class_;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.configurable.ConfigurableObject;
import at.jku.cis.iVolunteer.model.configurable.configurables.MatchingRule;
import at.jku.cis.iVolunteer.model.configurable.configurables.property.Property;

@Document
public class ConfigurableClass {

	@Id
	String id;
	String parentId;
	String name;
	
	List<Property> properties;
	List<MatchingRule> matchingRules;
	
	public ConfigurableClass() {
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

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
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
		if (!(obj instanceof ConfigurableClass)) {
			return false;
		}
		return ((ConfigurableClass) obj).id.equals(id);
	}
	
	
	
	
	
}
