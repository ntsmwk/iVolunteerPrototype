package at.jku.cis.iVolunteer.configurator.model.configurations.matching.collector;

import org.springframework.data.annotation.Id;

import at.jku.cis.iVolunteer.configurator.model.matching.MatchingEntityMappings;

public class MatchingEntityMappingConfiguration {
	@Id String id;

	String classConfigurationId;

	MatchingEntityMappings mappings;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassConfigurationId() {
		return classConfigurationId;
	}

	public void setClassConfigurationId(String classConfigurationId) {
		this.classConfigurationId = classConfigurationId;
	}

	public MatchingEntityMappings getMappings() {
		return mappings;
	}

	public void setMappings(MatchingEntityMappings mappings) {
		this.mappings = mappings;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MatchingEntityMappingConfiguration)) {
			return false;
		}
		return ((MatchingEntityMappingConfiguration) obj).id.equals(id);
	}

}
