package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;
import at.jku.cis.iVolunteer.model.meta.form.EnumEntry;
import at.jku.cis.iVolunteer.model.meta.form.EnumRepresentation;
import at.jku.cis.iVolunteer.model.meta.form.FormConfiguration;
import at.jku.cis.iVolunteer.model.meta.form.FormEntry;

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

	List<FormConfiguration> getParentsById(List<String> childIds) {
		List<ClassDefinition> childClassDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(childIds).forEach(childClassDefinitions::add);

		List<FormConfiguration> configList = new ArrayList<FormConfiguration>();

		// Pre-Condition: Graph must be acyclic - a child can only have one parent, one
		// parent can have multiple children
		// Work our way up the chain until we are at the root

		for (ClassDefinition childClassDefinition : childClassDefinitions) {
			FormConfiguration formConfig = new FormConfiguration();
			formConfig.setName(childClassDefinition.getName());
			formConfig.setId(childClassDefinition.getId());
			
			FormEntry formEntry = new FormEntry();
			formEntry.setClassDefinitions(new ArrayList<ClassDefinition>());
			formEntry.setClassProperties(new ArrayList<ClassProperty<Object>>());
			formEntry.setEnumRepresentations(new ArrayList<EnumRepresentation>());
			formEntry.setSubEntries(new ArrayList<FormEntry>());

			ClassDefinition currentClassDefinition = childClassDefinition;
			while (!currentClassDefinition.isRoot()) {

				formEntry.getClassDefinitions().add(currentClassDefinition);
				for (ClassProperty<Object> property : currentClassDefinition.getProperties()) {
					if (!formEntry.getClassProperties().contains(property)) {
						formEntry.getClassProperties().add(property);
					}
				}
				
				List<Relationship> associationList = relationshipRepository.findBySourceAndRelationshipType(currentClassDefinition.getId(), RelationshipType.ASSOCIATION);
				
				
				if (associationList != null) {
					
					
					for (Relationship r : associationList) {
						// for each Relationship, Do a DFS to construct dropdown options menu
						
						System.out.println(r.getId());
						System.out.println(associationList.size());
						
						ClassDefinition classDefinition = classDefinitionRepository.findOne(r.getTarget());
						EnumRepresentation enumRepresentation = createEnumRepresentation(classDefinition);
						enumRepresentation.setClassDefinition(classDefinition);
						formEntry.getEnumRepresentations().add(enumRepresentation);
					}
				}

				List<Relationship> inheritanceList = relationshipRepository
						.findByTargetAndRelationshipType(currentClassDefinition.getId(), RelationshipType.INHERITANCE);

				if (inheritanceList == null || inheritanceList.size() == 0) {
					throw new NotAcceptableException("getParentById: child is not root and has no parent");
				}

				currentClassDefinition = classDefinitionRepository.findOne(inheritanceList.get(0).getSource());
			}

			formEntry.getClassDefinitions().add(currentClassDefinition);
			for (ClassProperty<Object> property : currentClassDefinition.getProperties()) {
				if (!formEntry.getClassProperties().contains(property)) {
					formEntry.getClassProperties().add(property);
				}
			}
			
			formConfig.setFormEntry(formEntry);
			configList.add(formConfig);
		}

		return configList;
	}
	
	private EnumRepresentation createEnumRepresentation(ClassDefinition root) {
		List<EnumEntry> entries = new ArrayList<EnumEntry>();
		entries = performDFS(root, 0, entries);
		
		EnumRepresentation enumRepresentation = new EnumRepresentation();
		enumRepresentation.setEnumEntries(entries);
		enumRepresentation.setId(root.getId());
		
		return enumRepresentation;
	}
	
	private List<EnumEntry> performDFS(ClassDefinition root, int level, List<EnumEntry> list) {
		Stack<Relationship> stack = new Stack<>();
		List<Relationship> relationships = this.relationshipRepository.findBySourceAndRelationshipType(root.getId(), RelationshipType.INHERITANCE);
		Collections.reverse(relationships);
		stack.addAll(relationships);
		
		if (stack == null || stack.size() <= 0) {
			return list;
		} else {
			while (!stack.isEmpty()) {
				Relationship relationship = stack.pop();
				ClassDefinition classDefinition = classDefinitionRepository.findOne(relationship.getTarget());
				EnumEntry enumEntry = new EnumEntry(level, classDefinition.getName(), true);
				enumEntry.setPosition(new int[level+1]);
				list.add(enumEntry);
				this.performDFS(classDefinition, level+1, list);
			}
		}
		return list;
	}

	// TODO @Alex implement
	List<String> getChildrenById(List<String> rootIds) {

		List<ClassDefinition> rootClassDefintions = new ArrayList<ClassDefinition>();
		classDefinitionRepository.findAll(rootIds).forEach(rootClassDefintions::add);

		List<String> returnIds;
		for (ClassDefinition rootClassDefinitions : rootClassDefintions) {

		}

		return null;
	}

	public List<ClassDefinition> getClassDefinitionByArchetype(ClassArchetype archetype) {
		return classDefinitionRepository.getByClassArchetype(archetype);
	}

}
