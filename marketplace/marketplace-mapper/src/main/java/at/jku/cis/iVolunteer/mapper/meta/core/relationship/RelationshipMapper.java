package at.jku.cis.iVolunteer.mapper.meta.core.relationship;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipDTO;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipType;

@Component
public class RelationshipMapper implements AbstractMapper<Relationship, RelationshipDTO> {

	/**
	 * //General Fields
	String id;
	
	String classId1;
	String classId2;
		
	RelationshipType relationshipType;
	
	//Association Fields
	AssociationParameter param1;
	AssociationParameter param2;
	
	//Inheritance Fields
	String superClassId;
	 */
	@Autowired InheritanceMapper inheritanceMapper;
	@Autowired AssociationMapper associationMapper;
	
	@Override
	public RelationshipDTO toTarget(Relationship source) {
		if (source == null) {
			return null;
		}
		
		RelationshipDTO dto = new RelationshipDTO();
		dto.setId(source.getId());
		dto.setClassId1(source.getClassId1());
		dto.setClassId2(source.getClassId2());
		dto.setRelationshipType(source.getRelationshipType());
		
		
		if (source.getRelationshipType().equals(RelationshipType.INHERITANCE)) {
			dto = inheritanceMapper.toDTO((Inheritance) source, dto);
		} else if (source.getRelationshipType().equals(RelationshipType.ASSOCIATION)) {
			dto = associationMapper.toDTO((Association) source, dto);
		}
		
		return dto;
	}

	@Override
	public List<RelationshipDTO> toTargets(List<Relationship> sources) {
		if (sources == null) {
			return null;
		}
		
		List<RelationshipDTO> dtos = new ArrayList<RelationshipDTO>();
		for (Relationship entity : sources) {
			dtos.add(this.toTarget(entity));
		}
		
		return dtos;
		
	}

	@Override
	public Relationship toSource(RelationshipDTO target) {
		if (target == null) {
			return null;
		}
		
		Relationship entity = new Relationship();
		entity.setId(target.getId());
		entity.setClassId1(target.getClassId1());
		entity.setClassId2(target.getClassId2());
		entity.setRelationshipType(target.getRelationshipType());
		
		if (target.getRelationshipType().equals(RelationshipType.INHERITANCE)) {
			entity = inheritanceMapper.toEntity(target, entity);
		} else if (target.getRelationshipType().equals(RelationshipType.ASSOCIATION)) {
			entity = associationMapper.toEntity(target, entity);
		}
		
		return entity;
	}

	@Override
	public List<Relationship> toSources(List<RelationshipDTO> targets) {
		if (targets == null) {
			return null;
		}
		
		List<Relationship> entities = new ArrayList<Relationship>();
		for (RelationshipDTO dto : targets) {
			entities.add(this.toSource(dto));
		}
		
		return entities;
	}

}
