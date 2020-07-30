package at.jku.cis.iVolunteer.marketplace.configuration.matching;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.matching.relationships.MatchingOperatorRelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

@Service
public class MatchingService {

	@Autowired private MatchingOperatorRelationshipRepository matchingOperatorRelationshipRepository;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private MatchingPreparationService matchingPreparationService;

	public void match(String volunteerId, String tenantId) {
		List<MatchingOperatorRelationship> relationships = this.matchingOperatorRelationshipRepository
				.findByTenantId(tenantId);
		List<ClassInstance> classInstances = classInstanceRepository.getByUserIdAndTenantId(volunteerId, tenantId);
		List<ClassDefinition> classDefinitions = classDefinitionRepository.findByTenantId(tenantId);

		for (MatchingOperatorRelationship relationship : relationships) {
			ClassProperty<Object> leftClassProperty = null;
			ClassProperty<Object> rightClassProperty = null;

			final ClassDefinition leftClassDefinition = matchingPreparationService.handleLeftEntity(classDefinitions,
					relationship);
			final ClassDefinition rightClassDefinition = matchingPreparationService.handleRightEntity(classDefinitions,
					relationship);

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
