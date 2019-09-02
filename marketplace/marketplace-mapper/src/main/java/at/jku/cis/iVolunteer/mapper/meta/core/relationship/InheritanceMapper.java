package at.jku.cis.iVolunteer.mapper.meta.core.relationship;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.model.meta.core.relationship.RelationshipDTO;

@Component
public class InheritanceMapper implements AbstractMapper<Inheritance, RelationshipDTO>{

	/**	
	//Inheritance Fields
	String superClassId;
	*/
	
	public RelationshipDTO toDTO(Inheritance source, RelationshipDTO dto) {
		dto.setSuperClassId(source.getSuperClassId());
		return dto;
	}


	public Inheritance toEntity(RelationshipDTO target, Relationship entity) {
		Inheritance i = new Inheritance(entity);
		
		i.setSuperClassId(target.getSuperClassId());
		
		return i;
	}


	@Override
	public RelationshipDTO toDTO(Inheritance source) {
		if (source == null) {
			return null;
		}
		
		RelationshipDTO dto = new RelationshipDTO();
		
		dto.setId(source.getId());
		dto.setClassId1(source.getClassId1());
		dto.setClassId2(source.getClassId2());
		dto.setRelationshipType(source.getRelationshipType());
		dto.setSuperClassId(source.getSuperClassId());

		return dto;
	}


	@Override
	public List<RelationshipDTO> toDTOs(List<Inheritance> sources) {
		if (sources == null) {
			return null;
		}
		
		List<RelationshipDTO> dtos = new ArrayList<RelationshipDTO>();
		for (Inheritance entity : sources) {
			dtos.add(this.toDTO(entity));
		}

		return dtos;
	}


	@Override
	public Inheritance toEntity(RelationshipDTO target) {
		if (target == null) {
			return null;
		}
		
		Inheritance entity = new Inheritance();
		entity.setId(target.getId());
		entity.setClassId1(target.getClassId1());
		entity.setClassId2(target.getClassId2());
		entity.setRelationshipType(target.getRelationshipType());
		entity.setSuperClassId(target.getSuperClassId());
		
		return entity;
	}


	@Override
	public List<Inheritance> toEntities(List<RelationshipDTO> targets) {
		if (targets == null) {
			return null;
		}

		List<Inheritance> entities = new ArrayList<Inheritance>();
		for (RelationshipDTO dto : targets) {
			entities.add(this.toEntity(dto));
		}
		
		return entities;
	}
	




}
