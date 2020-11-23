package at.jku.cis.iVolunteer.configurator.model.matching;

import java.util.List;

import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.collector.MatchingEntityMappingConfiguration;

public class MatchingDataRequestDTO {

	MatchingConfiguration matchingConfiguration;

	List<MatchingOperatorRelationship> relationships;

	MatchingEntityMappingConfiguration leftMappingConfigurations;
	MatchingEntityMappingConfiguration rightMappingConfigurations;

	String pathDelimiter;

	public MatchingConfiguration getMatchingConfiguration() {
		return matchingConfiguration;
	}

	public void setMatchingConfiguration(MatchingConfiguration matchingConfiguration) {
		this.matchingConfiguration = matchingConfiguration;
	}

	public List<MatchingOperatorRelationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<MatchingOperatorRelationship> relationships) {
		this.relationships = relationships;
	}

	public MatchingEntityMappingConfiguration getLeftMappingConfigurations() {
		return leftMappingConfigurations;
	}

	public void setLeftMappingConfigurations(MatchingEntityMappingConfiguration leftMappingConfigurations) {
		this.leftMappingConfigurations = leftMappingConfigurations;
	}

	public MatchingEntityMappingConfiguration getRightMappingConfigurations() {
		return rightMappingConfigurations;
	}

	public void setRightMappingConfigurations(MatchingEntityMappingConfiguration rightMappingConfigurations) {
		this.rightMappingConfigurations = rightMappingConfigurations;
	}

	public String getPathDelimiter() {
		return pathDelimiter;
	}

	public void setPathDelimiter(String pathDelimiter) {
		this.pathDelimiter = pathDelimiter;
	}


}
