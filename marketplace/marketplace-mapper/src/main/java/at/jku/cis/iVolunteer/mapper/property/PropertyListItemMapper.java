package at.jku.cis.iVolunteer.mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.OneWayDtoMapper;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.SingleProperty;
import at.jku.cis.iVolunteer.model.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.dto.PropertyListItemDTO;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class PropertyListItemMapper implements OneWayDtoMapper<Property, PropertyListItemDTO<Object>>{

	@Override
	public PropertyListItemDTO<Object> toDTO(Property source) {
		
		if (source == null) {
			return null;
		}
		
		PropertyListItemDTO<Object> propertyListItemDTO = new PropertyListItemDTO();
		propertyListItemDTO.setId(source.getId());
		propertyListItemDTO.setName(source.getName());
		
		
		propertyListItemDTO.setKind(source.getKind());
		
		if (!propertyListItemDTO.getKind().equals(PropertyKind.MULTIPLE)) {
			propertyListItemDTO.setValue(((SingleProperty)source).getValue());
		} else {
			propertyListItemDTO.setValue(PropertyKind.MULTIPLE);
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
