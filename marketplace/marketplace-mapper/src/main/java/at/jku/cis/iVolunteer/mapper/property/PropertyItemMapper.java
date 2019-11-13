package at.jku.cis.iVolunteer.mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.meta.core.property.dtos.PropertyItemDTO;
import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;

@Component
public class PropertyItemMapper implements OneWayMapper<UserDefinedTaskTemplate, PropertyItemDTO>{
	
	@Override
	public PropertyItemDTO toTarget(UserDefinedTaskTemplate source) {
		
		if (source == null) {
			return null;
		}
		
		PropertyItemDTO item = new PropertyItemDTO();
		item.setId(source.getId());
		item.setName(source.getName());
	
		
		return item;
	}

	@Override
	public List<PropertyItemDTO> toTargets(List<UserDefinedTaskTemplate> sources) {
		if (sources == null)  {
			return null;
		}
		
		List<PropertyItemDTO> list = new ArrayList<PropertyItemDTO>(sources.size());
        for ( UserDefinedTaskTemplate item : sources ) {
            list.add( toTarget( item ) );
        }
		return list;
	}

	
}
