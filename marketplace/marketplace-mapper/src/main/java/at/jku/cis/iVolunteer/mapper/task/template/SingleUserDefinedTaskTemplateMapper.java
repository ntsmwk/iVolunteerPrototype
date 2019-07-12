package at.jku.cis.iVolunteer.mapper.task.template;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.property.PropertyMapper;
import at.jku.cis.iVolunteer.model.configurable.configurables.property.Property;
import at.jku.cis.iVolunteer.model.property.dto.PropertyDTO;
import at.jku.cis.iVolunteer.model.task.template.SingleUserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.dto.SingleUserDefinedTaskTemplateDTO;

@Component
public class SingleUserDefinedTaskTemplateMapper implements AbstractMapper<SingleUserDefinedTaskTemplate, SingleUserDefinedTaskTemplateDTO> {

	@Autowired PropertyMapper propertyMapper;
	
	@Override
	public SingleUserDefinedTaskTemplateDTO toDTO(SingleUserDefinedTaskTemplate source) {
		if (source == null) {
			return null;
		}
		
		SingleUserDefinedTaskTemplateDTO dto = new SingleUserDefinedTaskTemplateDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setDescription(source.getDescription());
		
		dto.setOrder(source.getOrder());

		
		
		List<PropertyDTO<Object>> props = new ArrayList<>();
		if (source.getProperties() != null) {
			for (Property p : source.getProperties()) {
				props.add(propertyMapper.toDTO(p));
			}
		}	
		dto.setKind("single");
		dto.setProperties(props);
			
		return dto;
	}

	@Override
	public List<SingleUserDefinedTaskTemplateDTO> toDTOs(List<SingleUserDefinedTaskTemplate> sources) {
		List<SingleUserDefinedTaskTemplateDTO> ret = new ArrayList<SingleUserDefinedTaskTemplateDTO>();
		
		if (sources == null) {
			return null;
		} else {
			for (SingleUserDefinedTaskTemplate t : sources) {
				ret.add(this.toDTO(t));
			}
			
		}
		return ret;
	}

	@Override
	public SingleUserDefinedTaskTemplate toEntity(SingleUserDefinedTaskTemplateDTO target) {

		if (target == null) {
			return null;
		}
		
		SingleUserDefinedTaskTemplate template = new SingleUserDefinedTaskTemplate();
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
	
		return template;
	}

	@Override
	public List<SingleUserDefinedTaskTemplate> toEntities(List<SingleUserDefinedTaskTemplateDTO> targets) {
		List <SingleUserDefinedTaskTemplate> ret = new ArrayList<SingleUserDefinedTaskTemplate>();
		if (targets == null) {
			return null;
		} else {
			for (SingleUserDefinedTaskTemplateDTO dto : targets) {
				ret.add(this.toEntity(dto));
			}
		}
		
		return ret;
	}
	


}
