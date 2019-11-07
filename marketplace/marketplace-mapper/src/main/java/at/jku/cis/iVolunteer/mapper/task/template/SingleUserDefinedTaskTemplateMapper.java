package at.jku.cis.iVolunteer.mapper.task.template;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.property.ClassPropertyMapper;
import at.jku.cis.iVolunteer.mapper.meta.core.property.PropertyInstanceMapper;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.ClassPropertyDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.PropertyInstanceDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;
import at.jku.cis.iVolunteer.model.task.template.SingleUserDefinedTaskTemplate;
import at.jku.cis.iVolunteer.model.task.template.dto.SingleUserDefinedTaskTemplateDTO;

@Component
public class SingleUserDefinedTaskTemplateMapper implements AbstractMapper<SingleUserDefinedTaskTemplate, SingleUserDefinedTaskTemplateDTO> {

	@Autowired ClassPropertyMapper classPropertyMapper;
	
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

		
		
		List<ClassPropertyDTO<Object>> props = new ArrayList<>();
		if (source.getTemplateProperties() != null) {
			for (ClassProperty<Object> p : source.getTemplateProperties()) {
				props.add(classPropertyMapper.toDTO(p));
			}
		}	
		dto.setKind("single");
		dto.setTemplateProperties(props);
			
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
		
		if (target.getTemplateProperties() != null) {
			
			SingleUserDefinedTaskTemplate s = new SingleUserDefinedTaskTemplate(template);
			List<ClassProperty<Object>> props = new ArrayList<>();

			for (ClassPropertyDTO<Object> p : target.getTemplateProperties()) {
				props.add(classPropertyMapper.toEntity(p));
				
			}
			
			s.setTemplateProperties(props);
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
