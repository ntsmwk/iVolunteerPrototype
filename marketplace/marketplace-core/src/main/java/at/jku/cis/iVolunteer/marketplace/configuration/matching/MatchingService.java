package at.jku.cis.iVolunteer.marketplace.configuration.matching;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.commons.DateTimeService;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.relationships.MatchingOperatorRelationshipRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassInstanceRepository;
import at.jku.cis.iVolunteer.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

@Service
public class MatchingService {

	@Autowired private MatchingOperatorRelationshipRepository matchingOperatorRelationshipRepository;
	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private MatchingPreparationService matchingPreparationService;
	@Autowired private DateTimeService dateTimeService;

	public float match(String volunteerId, String tenantId) {
		List<MatchingOperatorRelationship> relationships = this.matchingOperatorRelationshipRepository
				.findByTenantId(tenantId);
		List<ClassInstance> classInstances = classInstanceRepository.getByUserIdAndTenantId(volunteerId, tenantId);
		List<ClassDefinition> classDefinitions = classDefinitionRepository.findByTenantId(tenantId);

		float sum = 0;

		// @formatter:off
		for (MatchingOperatorRelationship relationship : relationships) {
			List<ClassDefinition> leftClassDefinitions = 
					matchingPreparationService
						.retriveLeftClassDefinition(classDefinitions, relationship);
			
			ClassProperty<Object> leftClassProperty = 
					matchingPreparationService
						.retrieveLeftClassProperty(leftClassDefinitions, relationship);
			
			List<ClassDefinition> rightClassDefinitions = 
					matchingPreparationService
						.retrieveRightClassDefinitionEntity(classDefinitions, relationship);
			
			ClassProperty<Object> rightClassProperty = 
					matchingPreparationService
						.retrieveRightClassProperty(rightClassDefinitions, relationship);

			List<ClassInstance> leftClassInstances = 
					classInstances
					.stream()
					.filter(
							ci -> leftClassDefinitions
								.stream()
								.anyMatch(lcd -> lcd.getId().equals(ci.getClassDefinitionId())))
					.collect(Collectors.toList());
			
			List<ClassInstance> rightClassInstances = 
					classInstances
					.stream()
					.filter(
							ci -> rightClassDefinitions
								.stream()
								.anyMatch(rcd -> rcd.getId().equals(ci.getClassDefinitionId())))
					.collect(Collectors.toList());

			sum += this.matchListAndList(leftClassInstances, leftClassProperty, rightClassInstances, rightClassProperty,
					relationship);
		}
		 
		// @formatter:on

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
			sum += matchSingleAndSingle(leftClassInstance, leftClassProperty, rightClassInstance, rightClassProperty,
					relationship);
		}

		return sum;
	}

	private float matchSingleAndSingle(ClassInstance leftClassInstance, ClassProperty<Object> leftClassProperty,
			ClassInstance rightClassInstance, ClassProperty<Object> rightClassProperty,
			MatchingOperatorRelationship relationship) {

		if (leftClassProperty.getType() != rightClassProperty.getType()) {
			throw new UnsupportedOperationException("cannot compare two properties with different types");
		}

		// @formatter:off
		PropertyInstance<Object> leftPropertyInstance = 
				leftClassInstance
					.getProperties()
					.stream()
					.filter(p -> p.getId().equals(leftClassProperty.getId()))
					.findFirst()
					.orElse(null);
		
		PropertyInstance<Object> rightPropertyInstance = 
				rightClassInstance
					.getProperties()
					.stream()
					.filter(p -> p.getId().equals(rightClassProperty.getId()))
					.findFirst()
					.orElse(null);
		// @formatter:on

		if (leftPropertyInstance.getValues().size() != 1 || rightPropertyInstance.getValues().size() != 1) {
			throw new UnsupportedOperationException("property value is either not set or multiple are set.");
		}

		switch (leftClassProperty.getType()) {
		case BOOL:
			boolean leftBoolean = Boolean.parseBoolean((String) leftPropertyInstance.getValues().get(0));
			boolean rightBoolean = Boolean.parseBoolean((String) rightPropertyInstance.getValues().get(0));
			return leftBoolean == rightBoolean ? 1 : 0;
		case DATE:
			Date leftDate = this.dateTimeService
					.parseMultipleDateFormats((String) leftPropertyInstance.getValues().get(0));
			Date rightDate = this.dateTimeService
					.parseMultipleDateFormats((String) rightPropertyInstance.getValues().get(0));
			return leftDate.compareTo(rightDate) == 0 ? 1 : 0;
		case ENUM:
//			TODO
			return 0;
		case FLOAT_NUMBER:

			break;
		case LONG_TEXT:
			break;
		case TEXT:
			break;
		case TUPLE:
			break;
		case WHOLE_NUMBER:
			break;
		default:
			break;

		}

		System.out.println(leftClassInstance);
		System.out.println(leftClassProperty);
		System.out.println(rightClassInstance);
		System.out.println(rightClassProperty);
		System.out.println(relationship);

		return 0;
	}

}
