package at.jku.cis.iVolunteer.marketplace.configuration.matching;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.marketplace.configurations.matching.relationships.MatchingOperatorRelationshipRepository;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@RestController
public class MatchingController {

	private static final String TENANT_ID = "5fbcf2495c219044f5574858";
//	Auftrag
	private static final String LEFT_CLASS_INSTANCE = "5fbcf75f3929cfcfba50fdd9";

	private static final String RIGHT_CLASS_INSTANCE = "5fbcf78672f5447d141b0f1d";

	@Autowired private MatchingService matchingService;
	@Autowired private MatchingOperatorRelationshipRepository matchingOperatorRelationshipRepository;

	@GetMapping("matching/test")
	public void testMatching() {
//		matchingService.match(VOLUNTEER_ID, TENANT_ID);
	}

	@GetMapping("matching/test1")
	public void testMatching1() {
		List<MatchingOperatorRelationship> relationships = this.matchingOperatorRelationshipRepository
				.findAll();

		matchingService.matchClassInstanceAndClassInstance(LEFT_CLASS_INSTANCE, RIGHT_CLASS_INSTANCE, relationships);
	}
	
	@GetMapping("match/flexProd")
	public Map<ClassInstanceComparison, Double> matchClassInstancesAndClassInstances(List<ClassInstance> leftClassInstances, List<ClassInstance> rightClassInstances,
			List<ClassDefinition> classDefinitions, List<MatchingOperatorRelationship> relationships) {
		return matchingService.matchClassInstancesAndClassInstances(leftClassInstances, rightClassInstances, classDefinitions, relationships);
	}

}
