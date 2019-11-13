package at.jku.cis.iVolunteer.mapper.meta.core.class_;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyInstanceMapper;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassInstance;
import at.jku.cis.iVolunteer.model.meta.core.class_.dtos.ClassInstanceDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.dto.PropertyInstanceDTO;
import at.jku.cis.iVolunteer.model.meta.matching.MatchingRule;

@Component
public class ClassInstanceMapper implements AbstractMapper<ClassInstance, ClassInstanceDTO>{

	@Autowired PropertyInstanceMapper propertyInstanceMapper;
	
	@Override
	public ClassInstanceDTO toDTO(ClassInstance source) {
		if (source == null) {
			return null;
		}
		
		ClassInstanceDTO dto = new ClassInstanceDTO();
		
		dto.setId(source.getId());
		dto.setClassDefinitionId(source.getClassDefinitionId());
		dto.setParentClassInstanceId(source.getParentClassInstanceId());
		
		dto.setName(source.getName());
		
		List<PropertyInstanceDTO<Object>> properties = new ArrayList<PropertyInstanceDTO<Object>>();
		if (source.getProperties() != null) {
			for (PropertyInstance<Object> i : source.getProperties()) {
				properties.add(propertyInstanceMapper.toDTO(i));
			}
		}
		dto.setProperties(properties);
		
		List<MatchingRule> matchingRules = new ArrayList<MatchingRule>();
		if (source.getMatchingRules() != null) {
			for (MatchingRule r : source.getMatchingRules()) {
				matchingRules.add(r);
			}
		}
				
		return dto;
	}

	@Override
	public List<ClassInstanceDTO> toDTOs(List<ClassInstance> sources) {
		if (sources == null) {
			return null;
		}
		
		List<ClassInstanceDTO> dtos = new ArrayList<ClassInstanceDTO>();
		for (ClassInstance entity : sources) {
			dtos.add(this.toDTO(entity));
		}
		
		return dtos;
	}

	@Override
	public ClassInstance toEntity(ClassInstanceDTO target) {
		if (target == null) {
			return null;
		}

		ClassInstance entity = new ClassInstance();
		entity.setId(target.getId());
		entity.setClassDefinitionId(target.getClassDefinitionId());
		entity.setParentClassInstanceId(target.getParentClassInstanceId());
		
		entity.setName(target.getName());
		
		List<PropertyInstance<Object>> properties = new ArrayList<PropertyInstance<Object>>();
		if (target.getProperties() != null) {
			for (PropertyInstanceDTO<Object> i : target.getProperties()) {
				properties.add(propertyInstanceMapper.toEntity(i));
			}
		}
		entity.setProperties(properties);
		
		List<MatchingRule> matchingRules = new ArrayList<MatchingRule>();
		if (target.getMatchingRules() != null) {
			for (MatchingRule r : target.getMatchingRules()) {
				matchingRules.add(r);
			}
		}
		entity.setMatchingRules(matchingRules);

		return entity;
	}

	@Override
	public List<ClassInstance> toEntities(List<ClassInstanceDTO> targets) {
		if (targets == null) {
			return null;
		}
		
		List<ClassInstance> entities = new ArrayList<ClassInstance>();
		for (ClassInstanceDTO dto : targets) {
			entities.add(this.toEntity(dto));
		}

		return entities;
	}
	
	
}
