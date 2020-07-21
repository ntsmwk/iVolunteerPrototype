package at.jku.cis.iVolunteer.marketplace.configuration.matching;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.matching.configuration.MatchingConfigurationService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@Service
public class MatchmakingService {

	@Autowired private MatchingConfigurationService matchingConfigurationService;
	
	
	public float match(List<ClassInstance> classInstances, ClassInstance ci) {
//		1) find all CDs of the CIs
//		2) get all ClassConfigurationIds of these CDs
//		3) get all MatchingConfigurations of the ClassDefinitions
//		4) get all MatchingRelationships of these MatchingCOnfigurations
//		5) compare CDs with left/right matchingEntity
//		6) calculate match

		
//		WHAT DO WE WANT: find MMRelationshipOperator between 2 CDs
		
		
		
//		matchingConfigurationService.getMatchingConfiguratorByClassConfigurationIds(producerClassConfigurationId, consumerClassConfigurationId)
		
		
		float sum = 0;
		for(ClassInstance outer : classInstances) {
			sum += matchClassInstance(outer, ci);
		}
		
		return sum;
	}
	
	
	
	public float matchClassInstance(ClassInstance ci1, ClassInstance ci2) {
		
		
		return 1f;
	}
	
}
