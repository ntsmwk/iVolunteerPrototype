package at.jku.cis.iVolunteer.marketplace.configuration.matching;

import static at.jku.cis.iVolunteer.marketplace.meta.core.class_.CollectionService.PATH_DELIMITER;

import java.util.List;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

@Service
public class MatchingPreparationService {

	
	ClassDefinition handleLeftEntity(List<ClassDefinition> classDefinitions,
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

	ClassDefinition handleRightEntity(List<ClassDefinition> classDefinitions,
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
}
