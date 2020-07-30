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

	public float match(String volunteerId, String tenantId) {
		List<MatchingOperatorRelationship> relationships = this.matchingOperatorRelationshipRepository
				.findByTenantId(tenantId);
		List<ClassInstance> classInstances = classInstanceRepository.getByUserIdAndTenantId(volunteerId, tenantId);
		List<ClassDefinition> classDefinitions = classDefinitionRepository.findByTenantId(tenantId);

		float sum = 0;

		// @formatter:off
		for (MatchingOperatorRelationship relationship : relationships) {
			ClassDefinition leftClassDefinition = 
					matchingPreparationService
						.retriveLeftClassDefinition(classDefinitions, relationship);
			
			ClassProperty<Object> leftClassProperty = 
					matchingPreparationService
						.retrieveLeftClassProperty(leftClassDefinition, relationship);
			
			ClassDefinition rightClassDefinition = 
					matchingPreparationService
						.retrieveRightClassDefinitionEntity(classDefinitions, relationship);
			
			ClassProperty<Object> rightClassProperty = 
					matchingPreparationService
						.retrieveRightClassProperty(rightClassDefinition, relationship);

			List<ClassInstance> leftClassInstances = 
					classInstances
					.stream()
					.filter(ci -> ci.getClassDefinitionId().equals(leftClassDefinition.getId()))
					.collect(Collectors.toList());
			
			List<ClassInstance> rightClassInstances = 
					classInstances
					.stream()
					.filter(ci -> ci.getClassDefinitionId().equals(rightClassDefinition.getId()))
					.collect(Collectors.toList());
			 
			// @formatter:on

			sum += this.matchListAndList(leftClassInstances, leftClassProperty, rightClassInstances, rightClassProperty,
					relationship);
		}
		return sum;
	}

	private float matchListAndList(List<ClassInstance> leftClassInstances, ClassProperty<Object> leftClassProperty,
			List<ClassInstance> rightClassInstances, ClassProperty<Object> rightClassProperty,
			MatchingOperatorRelationship relationship) {
		float sum = 0;
		for (ClassInstance ci : leftClassInstances) {
			sum += this.matchListAndSingle(ci, leftClassProperty, rightClassInstances, rightClassProperty,
					relationship);
		}
		return sum;
	}

	public float matchListAndSingle(ClassInstance leftClassInstance, ClassProperty<Object> leftClassProperty,
			List<ClassInstance> classInstances, ClassProperty<Object> rightClassProperty,
			MatchingOperatorRelationship relationship) {

		float sum = 0;
		for (ClassInstance rightClassInstance : classInstances) {
			sum += matchSingleAndSingle(leftClassInstance, leftClassProperty, rightClassInstance, rightClassProperty, relationship);
		}

		return sum;
	}

	private float matchSingleAndSingle(ClassInstance leftClassInstance, ClassProperty<Object> leftClassProperty,
			ClassInstance rightClassInstance, ClassProperty<Object> rightClassProperty,
			MatchingOperatorRelationship relationship) {
		
		System.out.println(leftClassInstance);
		System.out.println(leftClassProperty);
		System.out.println(rightClassInstance);
		System.out.println(rightClassProperty);
		System.out.println(relationship);
		
		
		return 0;
	}

	

}
