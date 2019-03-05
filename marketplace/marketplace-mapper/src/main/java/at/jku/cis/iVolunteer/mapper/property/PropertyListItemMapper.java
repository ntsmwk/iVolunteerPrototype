package at.jku.cis.iVolunteer.mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.OneWayDtoMapper;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.dto.PropertyListItemDTO;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class PropertyListItemMapper implements OneWayDtoMapper<Property<?>, PropertyListItemDTO<?>>{

	@Override
	public PropertyListItemDTO toDTO(Property<?> source) {
		
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
	public List<PropertyListItemDTO<?>> toDTOs(List<Property<?>> sources) {
		if (sources == null)  {
			return null;
		}
		
		List<PropertyListItemDTO<?>> list = new ArrayList<PropertyListItemDTO<?>>(sources.size());
        for ( Property<?> propItem : sources ) {
            list.add( toDTO( propItem ) );
        }
		return list;
	}

	
}
