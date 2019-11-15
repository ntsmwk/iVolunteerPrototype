package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassDefinitionMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.class_.dtos.ClassDefinitionDTO;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;

@Service
public class ClassDefinitionService {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private ClassDefinitionMapper classDefinitionMapper;

	ClassDefinitionDTO getClassDefinitionById(String id) {
		return classDefinitionMapper.toDTO(classDefinitionRepository.findOne(id));
	}

	List<ClassDefinitionDTO> getClassDefinitonsById(List<String> ids) {
		List<ClassDefinition> classDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(ids).forEach(classDefinitions::add);
		return classDefinitionMapper.toDTOs(classDefinitions);
	}

	ClassDefinitionDTO newClassDefinition(ClassDefinitionDTO classDefinition) {
		return classDefinitionMapper
				.toDTO(classDefinitionRepository.save(classDefinitionMapper.toEntity(classDefinition)));
	}

	ClassDefinitionDTO changeClassDefinitionName(String id, String newName) {
		ClassDefinition clazz = classDefinitionRepository.findOne(id);
		clazz.setName(newName);
		return classDefinitionMapper.toDTO(classDefinitionRepository.save(clazz));
	}

	List<ClassDefinitionDTO> deleteClassDefinition(List<String> idsToRemove) {
		for (String id : idsToRemove) {
			classDefinitionRepository.delete(id);
		}
		return classDefinitionMapper.toDTOs(classDefinitionRepository.findAll());

	}

	List<ClassDefinitionDTO> addOrUpdateClassDefinitions(List<ClassDefinitionDTO> classDefinitions) {
		return this.classDefinitionMapper
				.toDTOs(this.classDefinitionRepository.save(classDefinitionMapper.toEntities(classDefinitions)));
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
				currentClassDefinition = classDefinitionRepository.findOne(relationshipList.get(0).getClassId2());

			} while (!currentClassDefinition.isRoot());
			// TODO turn map into JSON
			// TODO append JSON to String List
		}

		return null;
	}

}
