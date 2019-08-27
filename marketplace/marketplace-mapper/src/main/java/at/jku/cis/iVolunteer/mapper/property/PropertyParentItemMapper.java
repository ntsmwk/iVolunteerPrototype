package at.jku.cis.iVolunteer.mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.OneWayDtoMapper;
import at.jku.cis.iVolunteer.mapper.property.listEntry.ListEntryMapper;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.Property;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.SingleProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.dto.PropertyListItemDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.dto.PropertyParentItemDTO;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;
import at.jku.cis.iVolunteer.model.task.template.UserDefinedTaskTemplate;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class PropertyParentItemMapper implements OneWayDtoMapper<UserDefinedTaskTemplate, PropertyParentItemDTO>{

	@Autowired ListEntryMapper listEntryMapper;
	
	@Override
	public PropertyParentItemDTO toDTO(UserDefinedTaskTemplate source) {
		
		if (source == null) {
			return null;
		}
		
		PropertyParentItemDTO item = new PropertyParentItemDTO();
		item.setId(source.getId());
		item.setName(source.getName());
	
		
		return item;
	}

	@Override
	public List<PropertyParentItemDTO> toDTOs(List<UserDefinedTaskTemplate> sources) {
		if (sources == null)  {
			return null;
		}
		
		List<PropertyParentItemDTO> list = new ArrayList<PropertyParentItemDTO>(sources.size());
        for ( UserDefinedTaskTemplate item : sources ) {
            list.add( toDTO( item ) );
        }
		return list;
	}

	
}
