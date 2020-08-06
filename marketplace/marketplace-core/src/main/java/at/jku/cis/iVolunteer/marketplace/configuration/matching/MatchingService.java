package at.jku.cis.iVolunteer.marketplace.configuration.matching;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
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
	private static final Logger logger = Logger.getLogger(MatchingService.class);

//	TODO need to return list with <CI, score>
//	here only overall similarity is calculated

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
			
			System.out.println("Fuzzyness" + relationship.getFuzzyness());
			System.out.println("Weight" + relationship.getWeighting());
			System.out.println("Necessary" + relationship.isNecessary());
		}
		 
		// @formatter:on
		System.out.println("Matching Score: " + sum);
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
			logger.warn("cannot compare two properties with different types");
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
			logger.warn("property value is either not set or multiple are set.");
		}

		switch (leftClassProperty.getType()) {
		case BOOL:
			return compareBoolean(leftPropertyInstance, rightPropertyInstance, relationship);
		case DATE:
			return compareDate(leftPropertyInstance, rightPropertyInstance, relationship);
		case ENUM:
//			TODO
			return 0;
		case FLOAT_NUMBER:
			return compareFloat(leftPropertyInstance, rightPropertyInstance, relationship);
		case LONG_TEXT:
		case TEXT:
			return CompareText(leftPropertyInstance, rightPropertyInstance, relationship);
		case TUPLE:
//			TODO
			return 0;
		case WHOLE_NUMBER:
			return compareWholeNumber(leftPropertyInstance, rightPropertyInstance, relationship);
		default:
			return 0;
		}
	}

	private float compareBoolean(PropertyInstance<Object> leftPropertyInstance,
			PropertyInstance<Object> rightPropertyInstance, MatchingOperatorRelationship relationship) {
		boolean leftBoolean = Boolean.parseBoolean((String) leftPropertyInstance.getValues().get(0));
		boolean rightBoolean = Boolean.parseBoolean((String) rightPropertyInstance.getValues().get(0));
		switch (relationship.getMatchingOperatorType()) {
		case ALL:
		case EXISTS:
		case GREATER:
		case GREATER_EQUAL:
		case LESS:
		case LESS_EQUAL:
			logger.warn("Matching Operator not supported for boolean!");
		case EQUAL:
			return leftBoolean == rightBoolean ? 1 : 0;
		}
		return 0;
	}

	private float compareDate(PropertyInstance<Object> leftPropertyInstance,
			PropertyInstance<Object> rightPropertyInstance, MatchingOperatorRelationship relationship) {
//		TODO calculate fuzzyness???
		Date leftDate = this.dateTimeService.parseMultipleDateFormats((String) leftPropertyInstance.getValues().get(0));
		Date rightDate = this.dateTimeService
				.parseMultipleDateFormats((String) rightPropertyInstance.getValues().get(0));
		switch (relationship.getMatchingOperatorType()) {
		case ALL:
		case EXISTS:
			logger.warn("Matching Operator not supported for date!");
		case GREATER:
			return leftDate.compareTo(rightDate) > 0 ? 1 : 0;
		case GREATER_EQUAL:
			return leftDate.compareTo(rightDate) >= 0 ? 1 : 0;
		case LESS:
			return leftDate.compareTo(rightDate) < 0 ? 1 : 0;
		case LESS_EQUAL:
			return leftDate.compareTo(rightDate) <= 0 ? 1 : 0;
		case EQUAL:
			return leftDate.compareTo(rightDate) == 0 ? 1 : 0;
		}
		return 0;
	}

	private float compareFloat(PropertyInstance<Object> leftPropertyInstance,
			PropertyInstance<Object> rightPropertyInstance, MatchingOperatorRelationship relationship) {
		double leftDouble = Double.parseDouble((String) leftPropertyInstance.getValues().get(0));
		double rightDouble = Double.parseDouble((String) rightPropertyInstance.getValues().get(0));
		switch (relationship.getMatchingOperatorType()) {
		case ALL:
		case EXISTS:
			logger.warn("Matching Operator not supported for float!");
		case EQUAL:
			return calculateFuzzynessLower(leftDouble, relationship.getFuzzyness()) <= rightDouble
					&& rightDouble >= calculateFuzzynessUpper(leftDouble, relationship.getFuzzyness()) ? 1 : 0;
		case GREATER:
			return calculateFuzzynessUpper(leftDouble, relationship.getFuzzyness()) > rightDouble ? 1 : 0;
		case GREATER_EQUAL:
			return calculateFuzzynessUpper(leftDouble, relationship.getFuzzyness())  >= rightDouble ? 1 : 0;
		case LESS:
			return calculateFuzzynessLower(leftDouble, relationship.getFuzzyness()) < rightDouble ? 1 : 0;
		case LESS_EQUAL:
			return calculateFuzzynessLower(leftDouble, relationship.getFuzzyness()) <= rightDouble ? 1 : 0;
		}
		return 0;
	}

	private float CompareText(PropertyInstance<Object> leftPropertyInstance,
			PropertyInstance<Object> rightPropertyInstance, MatchingOperatorRelationship relationship) {
//		TODO implement fuzzyness??
		String leftString = (String) leftPropertyInstance.getValues().get(0);
		String rightString = (String) rightPropertyInstance.getValues().get(0);
		switch (relationship.getMatchingOperatorType()) {
		case ALL:
		case EXISTS:
		case GREATER:
		case GREATER_EQUAL:
		case LESS:
		case LESS_EQUAL:
			logger.warn("Matching Operator not supported for text!");
		case EQUAL:
			return leftString.equals(rightString) ? 1 : 0;
		}
		return 0;

	}

	private float compareWholeNumber(PropertyInstance<Object> leftPropertyInstance,
			PropertyInstance<Object> rightPropertyInstance, MatchingOperatorRelationship relationship) {
		long leftLong = Long.parseLong((String) leftPropertyInstance.getValues().get(0));
		long rightLong = Long.parseLong((String) rightPropertyInstance.getValues().get(0));
		switch (relationship.getMatchingOperatorType()) {
		case ALL:
		case EXISTS:
			logger.warn("Matching Operator not supported for whole number!");
		case EQUAL:
			return calculateFuzzynessLower(leftLong, relationship.getFuzzyness()) <= rightLong
					&& rightLong >= calculateFuzzynessUpper(leftLong, relationship.getFuzzyness()) ? 1 : 0;
		case GREATER:
			return calculateFuzzynessUpper(leftLong, relationship.getFuzzyness()) > rightLong ? 1 : 0;
		case GREATER_EQUAL:
			return calculateFuzzynessUpper(leftLong, relationship.getFuzzyness())  >= rightLong ? 1 : 0;
		case LESS:
			return calculateFuzzynessLower(leftLong, relationship.getFuzzyness()) < rightLong ? 1 : 0;
		case LESS_EQUAL:
			return calculateFuzzynessLower(leftLong, relationship.getFuzzyness()) <= rightLong ? 1 : 0;
		}
		return 0;
	}

	private double calculateFuzzynessLower(double value, float weighting) {
		return value - value * weighting / 100;
	}

	private double calculateFuzzynessUpper(double value, float weighting) {
		return value + value * weighting / 100;
	}
	
}
