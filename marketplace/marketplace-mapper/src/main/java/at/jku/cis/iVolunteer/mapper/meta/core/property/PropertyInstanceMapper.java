package at.jku.cis.iVolunteer.mapper.meta.core.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.meta.constraint.property.PropertyConstraintMapper;
import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.constraint.property.dto.PropertyConstraintDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.dto.PropertyInstanceDTO;

@Component
public class PropertyInstanceMapper implements AbstractMapper<PropertyInstance<Object>, PropertyInstanceDTO<Object>> {

	@Autowired PropertyConstraintMapper propertyConstraintMapper;
	
	@Override
	public PropertyInstanceDTO<Object> toDTO(PropertyInstance<Object> source) {
		if (source == null) {
			return null;
		}
		
		PropertyInstanceDTO<Object> dto = new PropertyInstanceDTO<Object>();
		
		dto.setId(source.getId());
		dto.setName(source.getName());
		
		List<Object> values = new ArrayList<Object>();
		if (source.getValues() != null) {
			for (Object o : source.getValues()) {
				values.add(o);
			}
		}
		dto.setValues(values);
		
		dto.setType(source.getType());
		
		dto.setRequired(source.isRequired());
		
		dto.setPosition(source.getPosition());
		
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
	public List<PropertyInstanceDTO<Object>> toDTOs(List<PropertyInstance<Object>> sources) {
		if (sources == null) {
			return null;
		}
		
		List<PropertyInstanceDTO<Object>> dtos = new ArrayList<PropertyInstanceDTO<Object>>();
		for (PropertyInstance<Object> entity : sources) {
			dtos.add(this.toDTO(entity));
		}
		
		return dtos;
	}

	@Override
	public PropertyInstance<Object> toEntity(PropertyInstanceDTO<Object> target) {
		if (target == null) {
			return null;
		}
		
		PropertyInstance<Object> entity = new PropertyInstance<Object>();
		entity.setId(target.getId());		
		entity.setName(target.getName());
		
		List<Object> values = new ArrayList<Object>();
		if (target.getValues() != null) {
			for (Object o : target.getValues()) {
				values.add(o);
			}
		}
		entity.setValues(values);
		
		entity.setType(target.getType());

		entity.setRequired(target.isRequired());
		
		entity.setPosition(target.getPosition());
		
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
	public List<PropertyInstance<Object>> toEntities(List<PropertyInstanceDTO<Object>> targets) {
		if (targets == null) {
			return null;
		}
		
		List<PropertyInstance<Object>> entities = new ArrayList<PropertyInstance<Object>>();
		for (PropertyInstanceDTO<Object> dto : targets) {
			entities.add(this.toEntity(dto));
		}
		
		return entities;
	}

}
