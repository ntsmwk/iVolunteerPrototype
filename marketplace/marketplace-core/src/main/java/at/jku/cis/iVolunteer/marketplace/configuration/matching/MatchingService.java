package at.jku.cis.iVolunteer.marketplace.configuration.matching;

import static at.jku.cis.iVolunteer.marketplace.meta.core.class_.CollectionService.PATH_DELIMITER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.matching.configuration.MatchingConfigurationService;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.relationships.MatchingOperatorRelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@Service
public class MatchingService {

	@Autowired private MatchingConfigurationService matchingConfigurationService;
	@Autowired private MatchingOperatorRelationshipRepository matchingOperatorRelationshipRepository;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;

	public void match(String volunteerId, String tenantId) {
		List<MatchingOperatorRelationship> relationships = this.matchingOperatorRelationshipRepository
				.findByTenantId(tenantId);
		List<ClassInstance> classInstances = classInstanceRepository.getByUserIdAndTenantId(volunteerId, tenantId);
		List<ClassDefinition> classDefinitions = classDefinitionRepository.findByTenantId(tenantId);

		for (MatchingOperatorRelationship relationship : relationships) {
			String[] leftPathSplitted = relationship.getLeftMatchingEntityPath().split(PATH_DELIMITER);
			switch (relationship.getLeftMatchingEntityType()) {
			case PROPERTY:
				if (leftPathSplitted.length > 1) {
					String classId = leftPathSplitted[leftPathSplitted.length - 2];
					String propertyId = leftPathSplitted[leftPathSplitted.length - 1];

					System.out.println(classId);
					System.out.println(propertyId);
					ClassDefinition classDeifinition = classDefinitions.stream().filter(cd -> cd.getId().equals(classId)).findFirst().orElse(null);
					
					
				} else {
					throw new RuntimeException("left path too short");
				}
				break;
			case CLASS:
				break;
			}

		}

	}

	public float match(List<ClassInstance> classInstances, ClassInstance ci) {
//		5) compare CDs with left/right matchingEntity
//		6) calculate match

//		WHAT DO WE WANT: find MMRelationshipOperator between 2 CDs

//		matchingConfigurationService.getMatchingConfiguratorByClassConfigurationIds(producerClassConfigurationId, consumerClassConfigurationId)

		float sum = 0;
		for (ClassInstance outer : classInstances) {
			sum += matchClassInstance(outer, ci);
		}

		return sum;
	}

	public float matchClassInstance(ClassInstance ci1, ClassInstance ci2) {

		return 1f;
	}

}
