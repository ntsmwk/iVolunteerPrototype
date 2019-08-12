package at.jku.cis.iVolunteer.model.configurable.asset;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.configurable.ConfigurableObject;
import at.jku.cis.iVolunteer.model.configurable.class_.ConfigurableClass;
import at.jku.cis.iVolunteer.model.configurable.configurables.MatchingRule;
import at.jku.cis.iVolunteer.model.configurable.configurables.property.Property;

@Document
public class ConfigurableAsset {
	@Id
	String id;
	
	String configurableClassId;
	
	List<Property> properties;
	List<MatchingRule> matchingRules;
	
	
	public ConfigurableAsset() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getConfigurableClassId() {
		return configurableClassId;
	}

	public void setConfigurableClassId(String configurableClassId) {
		this.configurableClassId = configurableClassId;
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
		if (!(obj instanceof ConfigurableAsset))
			return false;
		return ((ConfigurableAsset) obj).id.equals(id);
	}
}
