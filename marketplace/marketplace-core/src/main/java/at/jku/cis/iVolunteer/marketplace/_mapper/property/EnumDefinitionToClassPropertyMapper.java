package at.jku.cis.iVolunteer.marketplace._mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.marketplace._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.CollectionService;
import at.jku.cis.iVolunteer.model.meta.core.enums.EnumDefinition;
import at.jku.cis.iVolunteer.model.meta.core.enums.EnumEntry;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

@Component
public class EnumDefinitionToClassPropertyMapper implements OneWayMapper<EnumDefinition, ClassProperty<Object>> {

	@Autowired CollectionService collectionService;
	
	@SuppressWarnings("unchecked")
	@Override
	public ClassProperty<Object> toTarget(EnumDefinition source) {

		if (source == null) {
			return null;
		}
		
		@SuppressWarnings("rawtypes")
		ClassProperty classProperty = new ClassProperty<EnumEntry>();

		classProperty.setId(source.getId());
		classProperty.setName(source.getName());

		classProperty.setAllowedValues(collectionService.collectEnumEntries(source));

		classProperty.setType(PropertyType.ENUM);

//		if (source.getPropertyConstraints() != null) {
//			classProperty
//					.setPropertyConstraints(new ArrayList<PropertyConstraint<Object>>(source.getPropertyConstraints()));
//		}

		classProperty.setMultiple(source.isMultiple());
		classProperty.setRequired(source.isRequired());
		
		return classProperty;
	}

	@Override
	public List<ClassProperty<Object>> toTargets(List<EnumDefinition> sources) {
			
		if (sources == null) {
			return null;
		}
		
		List<ClassProperty<Object>> classProperties = new ArrayList<>();
		for (EnumDefinition enumDefinition : sources) {
			toTarget(enumDefinition);
		}
		
		return classProperties;
	}

	

}
