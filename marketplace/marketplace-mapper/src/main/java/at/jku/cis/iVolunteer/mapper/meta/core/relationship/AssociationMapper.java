package at.jku.cis.iVolunteer.mapper.meta.core.relationship;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Association;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipDTO;

@Component
public class AssociationMapper implements AbstractMapper<Association, RelationshipDTO>{

	/**
	//Association Fields
	AssociationParameter param1;
	AssociationParameter param2;

	 */
	
	public RelationshipDTO toDTO(Association source, RelationshipDTO dto) {
		dto.setParam1(source.getParam1());
		dto.setParam2(source.getParam2());
		
		return dto;
	}

	public Association toEntity(RelationshipDTO target, Relationship entity) {
		Association a = new Association(entity);
		a.setParam1(target.getParam1());
		a.setParam2(target.getParam2());

		return a;
	}

	@Override
	public RelationshipDTO toDTO(Association source) {
		if (source == null) {
			return null;
		}
		
		RelationshipDTO dto = new RelationshipDTO();
		
		dto.setId(source.getId());
		dto.setClassId1(source.getClassId1());
		dto.setClassId2(source.getClassId2());
		dto.setRelationshipType(source.getRelationshipType());
		dto.setParam1(source.getParam1());
		dto.setParam2(source.getParam2());
		
		return dto;
	}

	@Override
	public List<RelationshipDTO> toDTOs(List<Association> sources) {
		if (sources == null) {
			return null;
		}
		
		List<RelationshipDTO> dtos = new ArrayList<RelationshipDTO>();
		
		for (Association entity : sources) {
			dtos.add(this.toDTO(entity));
		}

		return dtos;
	}

	@Override
	public Association toEntity(RelationshipDTO target) {
		if (target == null) {
			return null;
		}
		
		Association entity = new Association();
		
		entity.setId(target.getId());
		entity.setClassId1(target.getClassId1());
		entity.setClassId2(target.getClassId2());
		entity.setRelationshipType(target.getRelationshipType());
		entity.setParam1(target.getParam1());
		entity.setParam2(target.getParam2());

		return entity;
	}

	@Override
	public List<Association> toEntities(List<RelationshipDTO> targets) {
		if (targets == null) {
			return null;
		}
		
		List<Association> entities = new ArrayList<Association>();
		for (RelationshipDTO dto : targets) {
			entities.add(this.toEntity(dto));
		}
	
		return entities;
	}


	

}
