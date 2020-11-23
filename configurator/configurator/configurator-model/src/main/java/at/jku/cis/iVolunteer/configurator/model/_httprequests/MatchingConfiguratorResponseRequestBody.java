package at.jku.cis.iVolunteer.configurator.model._httprequests;

import java.util.List;

import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingOperatorRelationship;

public class MatchingConfiguratorResponseRequestBody {
	
	String action;
	MatchingConfiguration matchingConfiguration;
	List<MatchingOperatorRelationship> matchingRelationships;
	List<String> idsToDelete;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
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
	public List<String> getIdsToDelete() {
		return idsToDelete;
	}
	public void setIdsToDelete(List<String> idsToDelete) {
		this.idsToDelete = idsToDelete;
	}
	

}
