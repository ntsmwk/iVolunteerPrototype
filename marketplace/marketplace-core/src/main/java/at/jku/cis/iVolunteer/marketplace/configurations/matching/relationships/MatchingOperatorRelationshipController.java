package at.jku.cis.iVolunteer.marketplace.configurations.matching.relationships;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import at.jku.cis.iVolunteer.model.configurations.matching.MatchingOperatorRelationship;

@Controller
public class MatchingOperatorRelationshipController {

	@Autowired private MatchingOperatorRelationshipRepository matchingOperatorRelationshipRepository;

	@GetMapping("matching-operator-relationship/{matchingConfigurationId}")
	public List<MatchingOperatorRelationship> getMatchingOperatorRelationshipByMatchingConfiguration(
			@PathVariable String matchingConfigurationId) {
		matchingOperatorRelationshipRepository.findByMatchingConfigurationId(matchingConfigurationId);
		return null;
	}

}
