package at.jku.cis.iVolunteer.model._httprequests.configurator;

import java.util.List;

import at.jku.cis.iVolunteer.model.configurations.matching.MatchingConfiguration;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingOperatorRelationship;

public class MatchingConfiguratorResponseRequestBody {
//	--body: matching-configuration + matching-relationships
	
	MatchingConfiguration matchingConfiguration;
	List<MatchingOperatorRelationship> matchingRelationships;
	
	
	public MatchingConfiguration getMatchingConfiguration() {
		return matchingConfiguration;
	}
	public void setMatchingConfiguration(MatchingConfiguration matchingConfiguration) {
		this.matchingConfiguration = matchingConfiguration;
	}
	public List<MatchingOperatorRelationship> getMatchingRelationships() {
		return matchingRelationships;
	}
	public void setMatchingRelationships(List<MatchingOperatorRelationship> matchingRelationships) {
		this.matchingRelationships = matchingRelationships;
	}
	
	
}
