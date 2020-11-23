package at.jku.cis.iVolunteer.configurator.model._httprequests;

import java.util.List;

import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.RelationshipDTO;

public class SaveMatchingConfigurationRequest {
	MatchingConfiguration matchingConfiguration;
	List<MatchingOperatorRelationship> matchingOperatorRelationships;
	String tenantId;
	
	public MatchingConfiguration getMatchingConfiguration() {
		return matchingConfiguration;
	}
	public void setMatchingConfiguration(MatchingConfiguration matchingConfiguration) {
		this.matchingConfiguration = matchingConfiguration;
	}
	public List<MatchingOperatorRelationship> getMatchingOperatorRelationships() {
		return matchingOperatorRelationships;
	}
	public void setMatchingOperatorRelationships(List<MatchingOperatorRelationship> matchingOperatorRelationships) {
		this.matchingOperatorRelationships = matchingOperatorRelationships;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	
	
}
