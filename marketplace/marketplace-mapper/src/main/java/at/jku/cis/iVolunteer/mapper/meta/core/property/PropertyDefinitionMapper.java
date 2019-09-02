package at.jku.cis.iVolunteer.mapper.meta.core.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.meta.constraint.property.PropertyConstraintMapper;
import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.constraint.property.dto.PropertyConstraintDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.PropertyDefinitionDTO;

@Component
public class PropertyDefinitionMapper implements AbstractMapper<PropertyDefinition<Object>, PropertyDefinitionDTO<Object>> {

	@Autowired PropertyConstraintMapper propertyConstraintMapper;
	
	@Override
	public PropertyDefinitionDTO<Object> toDTO(PropertyDefinition<Object> source) {
		if (source == null) {
			return null;
		}
		
		PropertyDefinitionDTO<Object> dto = new PropertyDefinitionDTO<Object>();
		
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setType(source.getType());
		dto.setCustom(source.isCustom());
		
		List<PropertyConstraintDTO<Object>> propertyConstraints = new ArrayList<PropertyConstraintDTO<Object>>();
		if (source.getPropertyConstraints() != null) {
			for (PropertyConstraint<Object> c : source.getPropertyConstraints()) {
				propertyConstraints.add(propertyConstraintMapper.toDTO(c));
			}
		}
		dto.setPropertyConstraints(propertyConstraints);
		
		return dto;
	}

	@Override
	public List<PropertyDefinitionDTO<Object>> toDTOs(List<PropertyDefinition<Object>> sources) {
		if (sources == null) {
			return null;
		}
		
		List<PropertyDefinitionDTO<Object>> dtos = new ArrayList<PropertyDefinitionDTO<Object>>();
		for (PropertyDefinition<Object> d : sources) {
			dtos.add(this.toDTO(d));
		}
		
		return dtos;
	}

	@Override
	public PropertyDefinition<Object> toEntity(PropertyDefinitionDTO<Object> target) {
		if (target == null) {
			return null;
		}
		
		PropertyDefinition<Object> entity = new PropertyDefinition<Object>();
		
		entity.setId(target.getId());
		entity.setName(target.getName());
		entity.setType(target.getType());
		entity.setCustom(target.isCustom());
		
		List<PropertyConstraint<Object>> propertyConstraints = new ArrayList<PropertyConstraint<Object>>();
		if (target.getPropertyConstraints() != null) {
			for (PropertyConstraintDTO<Object> c : target.getPropertyConstraints()) {
				propertyConstraints.add(propertyConstraintMapper.toEntity(c));
			}
		}
		entity.setPropertyConstraints(propertyConstraints);
		
		return entity;
	}

	@Override
	public List<PropertyDefinition<Object>> toEntities(List<PropertyDefinitionDTO<Object>> targets) {
		if (targets == null) {
			return null;
		}
		
		List<PropertyDefinition<Object>> entities = new ArrayList<PropertyDefinition<Object>>();
		for (PropertyDefinitionDTO<Object> d : targets) {
			entities.add(this.toEntity(d));
		}
		return entities;
	}

}
