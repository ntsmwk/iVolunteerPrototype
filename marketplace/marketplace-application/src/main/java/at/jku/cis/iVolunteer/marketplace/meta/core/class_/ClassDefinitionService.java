package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;

@Service
public class ClassDefinitionService {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private RelationshipRepository relationshipRepository;

	ClassDefinition getClassDefinitionById(String id) {
		return classDefinitionRepository.findOne(id);
	}

	List<ClassDefinition> getClassDefinitonsById(List<String> ids) {
		List<ClassDefinition> classDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(ids).forEach(classDefinitions::add);
		return classDefinitions;
	}

	ClassDefinition newClassDefinition(ClassDefinition classDefinitionDTO) {
		return classDefinitionRepository.save(classDefinitionDTO);
	}

	ClassDefinition changeClassDefinitionName(String id, String newName) {
		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		clazz.setName(newName);
		return (classDefinitionRepository.save(clazz));
	}

	List<ClassDefinition> deleteClassDefinition(List<String> idsToRemove) {
		for (String id : idsToRemove) {
			classDefinitionRepository.delete(id);
		}
		return classDefinitionRepository.findAll();
	}

	List<ClassDefinition> addOrUpdateClassDefinitions(List<ClassDefinition> classDefinitions) {
		return classDefinitionRepository.save(classDefinitions);
	}

	List<String> getParentsById(List<String> childIds) {
		List<ClassDefinition> childClassDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(childIds).forEach(childClassDefinitions::add);

		List<Relationship> allRelationships = relationshipRepository.findAll();
		List<String> returnIds = new ArrayList<>();

		// Pre-Condition: Graph must be acyclic - a child can only have one parent, one
		// parent can have multiple children
		// Work our way up the chain until we are at the root

		for (ClassDefinition childClassDefinition : childClassDefinitions) {
			Map<String, String> returnIdMap = new HashMap<String, String>();
			int i = 0;
			ClassDefinition currentClassDefinition = childClassDefinition;
			do {
				// add to map
				returnIdMap.put(i + "", currentClassDefinition.getId());
				// find relationship connecting this child with its parents
				List<Relationship> relationshipList = relationshipRepository.findByClassId1AndRelationshipType(
						currentClassDefinition.getId(), RelationshipType.INHERITANCE);

				if (relationshipList == null || relationshipList.size() == 0) {
					throw new NotAcceptableException("getParentById: child has no parent");
				}
				// traverse - find and assign new currentClassDefinition
				currentClassDefinition = classDefinitionRepository.findOne(relationshipList.get(0).getTarget());

			} while (!currentClassDefinition.isRoot());
			// TODO turn map into JSON
			// TODO append JSON to String List
		}

		return null;
	}

	public List<ClassDefinition> getClassDefinitionByArchetype(ClassArchetype archetype) {
		return classDefinitionRepository.getByClassArchetype(archetype);
	}

}
