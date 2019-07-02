package at.jku.cis.iVolunteer.mapper.task.template;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.property.PropertyMapper;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.dto.PropertyDTO;
import at.jku.cis.iVolunteer.model.task.template.MultiUserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.SingleUserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.dto.UserDefinedTaskTemplateDTO;
import at.jku.cis.iVolunteer.model.task.template.dto.SingleUserDefinedTaskTemplateDTO;

@Component
public class UserDefinedTaskTemplateMapper implements AbstractMapper<UserDefinedTaskTemplate, UserDefinedTaskTemplateDTO> {

	@Autowired PropertyMapper propertyMapper;
	@Autowired SingleUserDefinedTaskTemplateMapper singleUserDefinedTaskTemplateMapper;
	
	@Override
	public UserDefinedTaskTemplateDTO toDTO(UserDefinedTaskTemplate source) {
		if (source == null) {
			return null;
		}
		
		UserDefinedTaskTemplateDTO dto = new UserDefinedTaskTemplateDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setDescription(source.getDescription());
		
		dto.setOrder(source.getOrder());
		
		
//		System.out.println("instance of");
//		System.out.println("single: " + (source instanceof SingleUserDefinedTaskTemplate));
//		System.out.println("nested: " + (source instanceof NestedUserDefinedTaskTemplate));
//		
		if (source instanceof SingleUserDefinedTaskTemplate) {
			SingleUserDefinedTaskTemplate s = (SingleUserDefinedTaskTemplate) source;
			
			List<PropertyDTO<Object>> props = new ArrayList<>();
			if (s.getProperties() != null) {
				for (Property p : s.getProperties()) {
					props.add(propertyMapper.toDTO(p));
					
				}
			}
			
			dto.setKind("single");
			
			dto.setProperties(props);
			
		} else if (source instanceof MultiUserDefinedTaskTemplate) {
			MultiUserDefinedTaskTemplate m = (MultiUserDefinedTaskTemplate) source;
			
			List<SingleUserDefinedTaskTemplateDTO> templates = new ArrayList<>();
			if (m.getTemplates() != null) {
				for (SingleUserDefinedTaskTemplate t : m.getTemplates()) {
					templates.add(singleUserDefinedTaskTemplateMapper.toDTO(t));
				}
			}
			
			dto.setKind("nested");
			
			dto.setTemplates(templates);	
		}
		return dto;
	}

	@Override
	public List<UserDefinedTaskTemplateDTO> toDTOs(List<UserDefinedTaskTemplate> sources) {
		List<UserDefinedTaskTemplateDTO> ret = new ArrayList<UserDefinedTaskTemplateDTO>();
		
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
		
		template.setOrder(target.getOrder());
		
		
		if (target.getProperties() != null) {
			
			SingleUserDefinedTaskTemplate s = new SingleUserDefinedTaskTemplate(template);
			List<Property> props = new ArrayList<>();

			for (PropertyDTO<Object> p : target.getProperties()) {
				props.add(propertyMapper.toEntity(p));
				
			}
			
			s.setProperties(props);
			return s;
		}
		
		if (target.getTemplates() != null) {
			MultiUserDefinedTaskTemplate n = new MultiUserDefinedTaskTemplate(template);
			
			List<SingleUserDefinedTaskTemplate> templates = new ArrayList<>();
			
			for (SingleUserDefinedTaskTemplateDTO t : target.getTemplates()) {
				templates.add(singleUserDefinedTaskTemplateMapper.toEntity(t));
			}
			n.setTemplates(templates);
			return n;
		}
		
		
		
		return template;
	}

	@Override
	public List<UserDefinedTaskTemplate> toEntities(List<UserDefinedTaskTemplateDTO> targets) {
		List <UserDefinedTaskTemplate> ret = new ArrayList<UserDefinedTaskTemplate>();
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
