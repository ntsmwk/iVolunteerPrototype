package at.jku.cis.iVolunteer.mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.OneWayDtoMapper;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.dto.PropertyListItemDTO;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class PropertyListItemMapper implements OneWayDtoMapper<Property<Object>, PropertyListItemDTO<Object>>{

	@Override
	public PropertyListItemDTO toDTO(Property<Object> source) {
		
		if (source == null) {
			return null;
		}
		
		PropertyListItemDTO propertyListItemDTO = new PropertyListItemDTO();
		propertyListItemDTO.setId(source.getId());
		propertyListItemDTO.setName(source.getName());
		propertyListItemDTO.setValue(source.getValue());
		propertyListItemDTO.setKind(source.getKind());
		return propertyListItemDTO;
	}

	@Override
	public List<PropertyListItemDTO<Object>> toDTOs(List<Property<Object>> sources) {
		if (sources == null)  {
			return null;
		}
		
		List<PropertyListItemDTO<Object>> list = new ArrayList<PropertyListItemDTO<Object>>(sources.size());
        for ( Property<Object> propItem : sources ) {
            list.add( toDTO( propItem ) );
        }
		return list;
	}

	
}
