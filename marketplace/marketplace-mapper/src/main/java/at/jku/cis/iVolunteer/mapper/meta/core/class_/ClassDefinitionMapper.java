package at.jku.cis.iVolunteer.mapper.meta.core.class_;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.property.ClassPropertyMapper;
import at.jku.cis.iVolunteer.model.meta.core.class_.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.class_.dtos.ClassDefinitionDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.ClassPropertyDTO;
import at.jku.cis.iVolunteer.model.meta.matching.MatchingRule;

@Component
public class ClassDefinitionMapper implements AbstractMapper<ClassDefinition, ClassDefinitionDTO> {

	@Autowired ClassPropertyMapper classPropertyMapper;
//	@Autowired PropertyMapper propertyMapper;
	
	@Override
	public ClassDefinitionDTO toDTO(ClassDefinition source) {
		if (source == null) {
			return null;
		}
		
		ClassDefinitionDTO dto = new ClassDefinitionDTO();
		
		dto.setId(source.getId());
		dto.setParentId(source.getParentId());
		
		dto.setName(source.getName());
		
		List<ClassPropertyDTO<Object>> properties = new ArrayList<ClassPropertyDTO<Object>>();
		if (source.getProperties() != null) {
			for (ClassProperty<Object> p : source.getProperties()) {
				properties.add(classPropertyMapper.toDTO(p));
			}
		}
		dto.setProperties(properties);
		
		
		List<MatchingRule> matchingRules = new ArrayList<MatchingRule>();
		if (source.getMatchingRules() != null) {
			for (MatchingRule r : source.getMatchingRules()) {
				matchingRules.add(r);
			}
		}
		dto.setMatchingRules(matchingRules);
		
		dto.setRoot(source.isRoot());
		return dto;
	}

	@Override
	public List<ClassDefinitionDTO> toDTOs(List<ClassDefinition> sources) {
		if (sources == null) {
			return null;
		}
		
		List<ClassDefinitionDTO> dtos = new ArrayList<ClassDefinitionDTO>();
		for (ClassDefinition entity : sources) {
			dtos.add(this.toDTO(entity));
		}
		
		return dtos;
	}

	@Override
	public ClassDefinition toEntity(ClassDefinitionDTO target) {
		if (target == null) {
			return null;
		}

		ClassDefinition entity = new ClassDefinition();
		entity.setId(target.getId());
		entity.setParentId(target.getParentId());
		entity.setName(target.getName());

//TODO		
//		List<ClassProperty<Object>> properties = new ArrayList<ClassProperty<Object>>();
//		if (target.getProperties() != null) {
//			for (ClassPropertyDTO<Object> p : target.getProperties()) {
//				properties.add(classPropertyMapper.toEntity(p));
//			}
//		}
//		entity.setProperties(properties);
		
		List<ClassProperty<Object>> properties = new ArrayList<ClassProperty<Object>>();
		if (target.getProperties() != null) {
			for (ClassPropertyDTO<Object> p : target.getProperties()) {
				properties.add(classPropertyMapper.toEntity(p));
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
		entity.setRoot(target.isRoot());
		return entity;
	}

	@Override
	public List<ClassDefinition> toEntities(List<ClassDefinitionDTO> targets) {
		if (targets == null) {
			return null;
		}
		
		List<ClassDefinition> entities = new ArrayList<ClassDefinition>();
		for (ClassDefinitionDTO dto : targets) {
			entities.add(this.toEntity(dto));
		}
		
		return entities;
	}
	
}
