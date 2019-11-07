package at.jku.cis.iVolunteer.mapper.meta.core.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.meta.constraint.property.PropertyConstraintMapper;
import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.constraint.property.dto.PropertyConstraintDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.ClassPropertyDTO;

@Component
public class ClassPropertyMapper implements AbstractMapper<ClassProperty<Object>, ClassPropertyDTO<Object>> {

	@Autowired PropertyConstraintMapper propertyConstraintMapper;
	
	@Override
	public ClassPropertyDTO<Object> toDTO(ClassProperty<Object> source) {
		
		if (source == null) {
			return null;
		}
		
		ClassPropertyDTO<Object> dto = new ClassPropertyDTO<Object>();
		
		dto.setId(source.getId());
		
		dto.setName(source.getName());
		
		List<Object> defaultValues = new ArrayList<Object>();
		if (source.getDefaultValues() != null) {
			for (Object o : source.getDefaultValues()) {
				defaultValues.add(o);
			}
		}
		dto.setDefaultValues(defaultValues);
		
		List<Object> allowedValues = new ArrayList<Object>();
		if (source.getAllowedValues() != null) {
			for (Object o : source.getAllowedValues()) {
				allowedValues.add(o);
			}
		}
		dto.setAllowedValues(allowedValues);
		
		dto.setType(source.getType());
		dto.setMultiple(source.isMultiple());
		
		dto.setImmutable(source.isImmutable());
		dto.setUpdateable(source.isUpdateable());
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
	public List<ClassPropertyDTO<Object>> toDTOs(List<ClassProperty<Object>> list) {
		if (list == null) {
			return null;
		}
		
		List<ClassPropertyDTO<Object>> dtos = new ArrayList<ClassPropertyDTO<Object>>();
		for (ClassProperty<Object> c : list) {
			dtos.add(this.toDTO(c));
		}
		
		return dtos;
	}

	@Override
	public ClassProperty<Object> toEntity(ClassPropertyDTO<Object> target) {
		if (target == null) {
			return null;
		}

		ClassProperty<Object> entity = new ClassProperty<Object>();
		entity.setId(target.getId());
		entity.setName(target.getName());
		
		List<Object> defaultValues = new ArrayList<Object>();
		if (target.getDefaultValues() != null) {
			for (Object o : target.getDefaultValues()) {
				defaultValues.add(o);
			}
		}
		entity.setDefaultValues(defaultValues);
		
		List<Object> allowedValues = new ArrayList<Object>();
		if (target.getAllowedValues() != null) {
			for (Object o : target.getAllowedValues()) {
				allowedValues.add(o);
			}
		}
		entity.setAllowedValues(allowedValues);
		
		entity.setType(target.getType());
		entity.setMultiple(target.isMultiple());
		
		entity.setImmutable(target.isImmutable());
		entity.setUpdateable(target.isUpdateable());
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
	public List<ClassProperty<Object>> toEntities(List<ClassPropertyDTO<Object>> targets) {
		if (targets == null) {
			return null;
		}
		
		List<ClassProperty<Object>> entities = new ArrayList<ClassProperty<Object>>();
		for (ClassPropertyDTO<Object> c : targets) {
			entities.add(this.toEntity(c));
		}
		
		return entities;
	}

}
