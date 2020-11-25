package at.jku.cis.iVolunteer.marketplace.configuration.matching;

import static at.jku.cis.iVolunteer.marketplace.meta.core.class_.CollectionService.PATH_DELIMITER;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

@Service
public class MatchingPreparationService {

//	TODO MWE need to handle inherited properties for which matching is defined....
//	retrieve all CDs (list<CD>) from the left/right Path and handle all of their instances
// 	id of ClassProperty and their PropertyInstances are equal!!!
	
	List<ClassDefinition> retriveLeftClassDefinition(List<ClassDefinition> classDefinitions,
			MatchingOperatorRelationship relationship) {
		List<ClassDefinition> leftClassDefinitions = new ArrayList<>();
		String[] leftPathSplitted = relationship.getLeftMatchingEntityPath().split(PATH_DELIMITER);
		switch (relationship.getLeftMatchingEntityType()) {
		case PROPERTY:
			for (int i = 0; i < leftPathSplitted.length - 1; i++) {
				String classId = leftPathSplitted[i];
				// @formatter:off
					leftClassDefinitions.add(
							classDefinitions
								.stream()
								.filter(cd -> cd.getId().equals(classId))
								.findFirst()
								.orElse(null));					 
					// @formatter:on
			}
			break;
		case CLASS:
			break;
		}
		return leftClassDefinitions;
	}

	ClassProperty<Object> retrieveLeftClassProperty(List<ClassDefinition> leftClassDefinitions,
			MatchingOperatorRelationship relationship) {
		ClassProperty<Object> leftClassProperty = null;
		String[] leftPathSplitted = relationship.getLeftMatchingEntityPath().split(PATH_DELIMITER);
		switch (relationship.getLeftMatchingEntityType()) {
		case PROPERTY:
			if (leftPathSplitted.length > 1) {
				String propertyId = leftPathSplitted[leftPathSplitted.length - 1];

				// @formatter:off
				leftClassProperty = leftClassDefinitions
						.stream()
						.flatMap(cds -> cds.getProperties().stream())
						.filter(cp -> cp.getId().equals(propertyId))
						.findFirst()
						.orElse(null);
				// @formatter:on

			} else {
				throw new RuntimeException("left path too short");
			}
			break;
		case CLASS:
			break;
		}
		return leftClassProperty;
	}

	List<ClassDefinition> retrieveRightClassDefinitionEntity(List<ClassDefinition> classDefinitions,
			MatchingOperatorRelationship relationship) {
		List<ClassDefinition> rightClassDefinitions = new ArrayList<>();
		String[] rightPathSplitted = relationship.getRightMatchingEntityPath().split(PATH_DELIMITER);
		switch (relationship.getRightMatchingEntityType()) {
		case PROPERTY:
			for (int i = 0; i < rightPathSplitted.length - 1; i++) {
				String classId = rightPathSplitted[i];
				// @formatter:off
				rightClassDefinitions.add(
						classDefinitions
							.stream()
							.filter(cd -> cd.getId().equals(classId))
							.findFirst()
							.orElse(null));
				// @formatter:on
			}
			break;
		case CLASS:
			break;
		}
		return rightClassDefinitions;
	}

	ClassProperty<Object> retrieveRightClassProperty(List<ClassDefinition> rightClassDefinition,
			MatchingOperatorRelationship relationship) {
		ClassProperty<Object> rightClassProperty = null;
		String[] rightPathSplitted = relationship.getRightMatchingEntityPath().split(PATH_DELIMITER);
		switch (relationship.getRightMatchingEntityType()) {
		case PROPERTY:
			if (rightPathSplitted.length > 1) {
				String propertyId = rightPathSplitted[rightPathSplitted.length - 1];

				// @formatter:off
				rightClassProperty = rightClassDefinition
						.stream()
						.flatMap(cds -> cds.getProperties().stream())
						.filter(cp -> cp.getId().equals(propertyId))
						.findFirst()
						.orElse(null);
				// @formatter:on

			} else {
				throw new RuntimeException("right path too short");
			}
			break;
		case CLASS:
			break;
		}
		return rightClassProperty;
	}
}