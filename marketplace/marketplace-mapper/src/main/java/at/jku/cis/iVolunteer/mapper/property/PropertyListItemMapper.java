package at.jku.cis.iVolunteer.mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.OneWayDtoMapper;
import at.jku.cis.iVolunteer.mapper.property.listEntry.ListEntryMapper;
import at.jku.cis.iVolunteer.model.configurable.configurables.property.Property;
import at.jku.cis.iVolunteer.model.configurable.configurables.property.PropertyKind;
import at.jku.cis.iVolunteer.model.configurable.configurables.property.SingleProperty;
import at.jku.cis.iVolunteer.model.property.dto.PropertyListItemDTO;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class PropertyListItemMapper implements OneWayDtoMapper<Property, PropertyListItemDTO<Object>>{

	@Autowired ListEntryMapper listEntryMapper;
	
	@Override
	public PropertyListItemDTO<Object> toDTO(Property source) {
		
		if (source == null) {
			return null;
		}
		
		PropertyListItemDTO<Object> propertyListItemDTO = new PropertyListItemDTO();
		propertyListItemDTO.setId(source.getId());
		propertyListItemDTO.setName(source.getName());
		propertyListItemDTO.setOrder(source.getOrder());
		propertyListItemDTO.setCustom(source.isCustom());
		
		propertyListItemDTO.setKind(source.getKind());
		
		if (!propertyListItemDTO.getKind().equals(PropertyKind.MULTI) && !propertyListItemDTO.getKind().equals(PropertyKind.MAP)) {
			
			SingleProperty<Object> s = (SingleProperty) source;
			
			if (s.getValues() != null) {
				List<ListEntryDTO<Object>> values = new ArrayList<>();
				
				for (ListEntry<Object> entry : s.getValues()) {
					values.add(listEntryMapper.toDTO(entry));
				}
				propertyListItemDTO.setValues(values);
				
			}
			
			if (s.getDefaultValues() != null) {
				List<ListEntryDTO<Object>> values = new ArrayList<>();
				
				for (ListEntry<Object> entry : s.getDefaultValues()) {
					values.add(listEntryMapper.toDTO(entry));
				}
				propertyListItemDTO.setDefaultValues(values);
			}
			
			
			
		} else {
			List<ListEntryDTO<String>> values = new ArrayList<>();
			values.add(new ListEntryDTO<String>("0","multiple"));
		}
		
		return propertyListItemDTO;
	}

	@Override
	public List<PropertyListItemDTO<Object>> toDTOs(List<Property> sources) {
		if (sources == null)  {
			return null;
		}
		
		List<PropertyListItemDTO<Object>> list = new ArrayList<PropertyListItemDTO<Object>>(sources.size());
        for ( Property propItem : sources ) {
            list.add( toDTO( propItem ) );
        }
		return list;
	}

	
}
