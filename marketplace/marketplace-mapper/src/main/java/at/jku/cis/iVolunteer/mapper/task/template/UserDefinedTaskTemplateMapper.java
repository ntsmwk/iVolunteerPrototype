package at.jku.cis.iVolunteer.mapper.task.template;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.property.PropertyMapper;
import at.jku.cis.iVolunteer.mapper.property.SinglePropertyMapper;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.SingleProperty;
import at.jku.cis.iVolunteer.model.property.dto.PropertyDTO;
import at.jku.cis.iVolunteer.model.property.dto.SinglePropertyDTO;
import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.dto.UserDefinedTaskTemplateDTO;

@Component
public class UserDefinedTaskTemplateMapper implements AbstractMapper<UserDefinedTaskTemplate, UserDefinedTaskTemplateDTO> {

	@Autowired PropertyMapper propertyMapper;
	
	@Override
	public UserDefinedTaskTemplateDTO toDTO(UserDefinedTaskTemplate source) {
		if (source == null) {
			return null;
		}
		
		UserDefinedTaskTemplateDTO dto = new UserDefinedTaskTemplateDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setDescription(source.getDescription());
		
		List<PropertyDTO<Object>> props = new LinkedList<>();
		if (source.getProperties() != null) {
			for (Property p : source.getProperties()) {
				props.add(propertyMapper.toDTO(p));
				
			}
		}
		
		dto.setProperties(props);
		
		return dto;
	}

	@Override
	public List<UserDefinedTaskTemplateDTO> toDTOs(List<UserDefinedTaskTemplate> sources) {
		List<UserDefinedTaskTemplateDTO> ret = new LinkedList<UserDefinedTaskTemplateDTO>();
		
		if (sources == null) {
			return null;
		} else {
			for (UserDefinedTaskTemplate t : sources) {
				ret.add(this.toDTO(t));
			}
			
		}
		return ret;
	}

	@Override
	public UserDefinedTaskTemplate toEntity(UserDefinedTaskTemplateDTO target) {

		if (target == null) {
			return null;
		}
		
		UserDefinedTaskTemplate template = new UserDefinedTaskTemplate();
		template.setId(target.getId());
		template.setName(target.getName());
		template.setDescription(target.getDescription());
		
		List<Property> props = new LinkedList<>();
		if (target.getProperties() != null) {
			for (PropertyDTO<Object> p : target.getProperties()) {
				props.add(propertyMapper.toEntity(p));
				
			}
		}
		template.setProperties(props);
		
		return template;
	}

	@Override
	public List<UserDefinedTaskTemplate> toEntities(List<UserDefinedTaskTemplateDTO> targets) {
		List <UserDefinedTaskTemplate> ret = new LinkedList<UserDefinedTaskTemplate>();
		if (targets == null) {
			return null;
		} else {
			for (UserDefinedTaskTemplateDTO dto : targets) {
				ret.add(this.toEntity(dto));
			}
		}
		
		return ret;
	}
	


}
