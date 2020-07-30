package at.jku.cis.iVolunteer.marketplace.configuration.matching;

import static at.jku.cis.iVolunteer.marketplace.meta.core.class_.CollectionService.PATH_DELIMITER;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.matching.configuration.MatchingConfigurationService;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.relationships.MatchingOperatorRelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

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
			ClassProperty<Object> leftClassProperty = null;
			ClassProperty<Object> rightClassProperty = null;

			final ClassDefinition leftClassDefinition = handleLeftEntity(classDefinitions, relationship);
			final ClassDefinition rightClassDefinition = handleRightEntity(classDefinitions, relationship);

			List<ClassInstance> leftClassInstances = classInstances.stream()
					.filter(ci -> ci.getClassDefinitionId().equals(leftClassDefinition.getId()))
					.collect(Collectors.toList());
			List<ClassInstance> rightClassInstances = classInstances.stream()
					.filter(ci -> ci.getClassDefinitionId().equals(rightClassDefinition.getId()))
					.collect(Collectors.toList());

			for (ClassInstance ci : leftClassInstances) {
				this.match(ci, leftClassProperty, rightClassInstances, rightClassProperty, relationship);
			}

		}

	}

	private ClassDefinition handleLeftEntity(List<ClassDefinition> classDefinitions,
			MatchingOperatorRelationship relationship) {
		ClassProperty<Object> leftClassProperty;
		ClassDefinition leftClassDefinition = null;
		String[] leftPathSplitted = relationship.getLeftMatchingEntityPath().split(PATH_DELIMITER);
		switch (relationship.getLeftMatchingEntityType()) {
		case PROPERTY:
			if (leftPathSplitted.length > 1) {
				String classId = leftPathSplitted[leftPathSplitted.length - 2];
				String propertyId = leftPathSplitted[leftPathSplitted.length - 1];

				leftClassDefinition = classDefinitions.stream().filter(cd -> cd.getId().equals(classId)).findFirst()
						.orElse(null);
				leftClassProperty = leftClassDefinition.getProperties().stream()
						.filter(cp -> cp.getId().equals(propertyId)).findFirst().orElse(null);
			} else {
				throw new RuntimeException("left path too short");
			}
			break;
		case CLASS:
			break;
		}
		return leftClassDefinition;
	}

	private ClassDefinition handleRightEntity(List<ClassDefinition> classDefinitions,
			MatchingOperatorRelationship relationship) {
		ClassDefinition rightClassDefinition = null;
		ClassProperty<Object> rightClassProperty;
		String[] rightPathSplitted = relationship.getRightMatchingEntityPath().split(PATH_DELIMITER);
		switch (relationship.getRightMatchingEntityType()) {
		case PROPERTY:
			if (rightPathSplitted.length > 1) {
				String classId = rightPathSplitted[rightPathSplitted.length - 2];
				String propertyId = rightPathSplitted[rightPathSplitted.length - 1];

				rightClassDefinition = classDefinitions.stream().filter(cd -> cd.getId().equals(classId)).findFirst()
						.orElse(null);
				rightClassProperty = rightClassDefinition.getProperties().stream()
						.filter(cp -> cp.getId().equals(propertyId)).findFirst().orElse(null);
			} else {
				throw new RuntimeException("right path too short");
			}
			break;
		case CLASS:
			break;
		}
		return rightClassDefinition;
	}

	public float match(ClassInstance ci, ClassProperty<Object> leftClassProperty, List<ClassInstance> classInstances,
			ClassProperty<Object> rightClassProperty, MatchingOperatorRelationship relationship) {

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
