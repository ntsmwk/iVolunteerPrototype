package at.jku.cis.iVolunteer.mapper.meta.constraint.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.constraint.property.dto.PropertyConstraintDTO;

@Component
public class PropertyConstraintMapper implements AbstractMapper<PropertyConstraint<Object>, PropertyConstraintDTO<Object>> {

	@Override
	public PropertyConstraintDTO<Object> toDTO(PropertyConstraint<Object> source) {

		PropertyConstraintDTO<Object> dto = new PropertyConstraintDTO<Object>();
		dto.setId(source.getId());
		dto.setConstraintType(source.getConstraintType());
		
		dto.setValue(source.getValue());
		dto.setPropertyType(source.getPropertyType());
		
		dto.setMessage(source.getMessage());
		
		
		return dto;
	}

	@Override
	public List<PropertyConstraintDTO<Object>> toDTOs(List<PropertyConstraint<Object>> sources) {
		List<PropertyConstraintDTO<Object>> dtos = new ArrayList<PropertyConstraintDTO<Object>>();
		for (PropertyConstraint<Object> s : sources) {
			dtos.add(this.toDTO(s));
		}
		return dtos;
	}

	@Override
	public PropertyConstraint<Object> toEntity(PropertyConstraintDTO<Object> target) {
		
		PropertyConstraint<Object> entity = new PropertyConstraint<Object>();
		entity.setId(target.getId());
		entity.setConstraintType(target.getConstraintType());
		
		entity.setValue(target.getValue());
		entity.setPropertyType(target.getPropertyType());
		
		entity.setMessage(target.getMessage());
		
		return entity;
	}

	@Override
	public List<PropertyConstraint<Object>> toEntities(List<PropertyConstraintDTO<Object>> targets) {
		List<PropertyConstraint<Object>> entities = new ArrayList<PropertyConstraint<Object>>();
		for (PropertyConstraintDTO<Object> t : targets) {
			entities.add(this.toEntity(t));
		}
		
		return entities;
	}

}
