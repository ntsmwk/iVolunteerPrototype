package at.jku.cis.iVolunteer.mapper.property.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.PropertyToTypePropertyMapper;
import at.jku.cis.iVolunteer.model.property.BooleanProperty;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;



@Component
public class PropertyToBooleanPropertyMapper implements PropertyToTypePropertyMapper<BooleanProperty> {

	@Override
	public Property<?> toGenericProperty(BooleanProperty source) {
		if ( source == null ) {
            return null;
        }
		
		Property prop = new Property();
		prop.setId(source.getId());
		prop.setDefaultValue(source.getDefaultValue());
		prop.setKind(source.getKind());
		
		
		List<ListEntry> legalValues = new ArrayList<ListEntry>();
		if (source.getLegalValues() != null) {
			for (ListEntry entry : source.getLegalValues()) {
				legalValues.add(entry);
			}
			
		}
		prop.setLegalValues(legalValues);
		
		prop.setName(source.getName());
		prop.setRules(source.getRules());
		prop.setValue(source.getValue());
		
		
		
		return prop;
	}
	
	@Override
	public BooleanProperty toTypeProperty(Property<?> property) {
		if ( property == null ) {
            return null;
        }
		
		BooleanProperty bProp = new BooleanProperty();
		
		bProp.setId(property.getId());
		bProp.setDefaultValue(convertObjectToBoolean((property.getDefaultValue())));
		bProp.setKind(property.getKind());
		

		List<ListEntry<Boolean>> legalValues = new ArrayList<ListEntry<Boolean>>();
		if (property.getLegalValues() != null) {
			for (ListEntry<?> entry : property.getLegalValues()) {
				
				ListEntry<Boolean> bEntry = new ListEntry<Boolean>(entry.getId(), convertObjectToBoolean(entry.getValue()));
				legalValues.add(bEntry);
			}
		}
		bProp.setLegalValues(legalValues);
		
		List<ListEntry<Boolean>> values = new ArrayList<ListEntry<Boolean>>();
		if (property.getValues() != null) {
			for (ListEntry<?> entry : property.getValues()) {
				ListEntry<Boolean> bEntry = new ListEntry<Boolean>(entry.getId(), convertObjectToBoolean(entry.getValue()));
				values.add(bEntry);
			}
		}
		bProp.setValues(values);
		
		
		bProp.setName(property.getName());
		bProp.setRules(property.getRules());
		bProp.setValue(convertObjectToBoolean(property.getValue()));
		return bProp;
	}

	

	
	private Boolean convertObjectToBoolean(Object source) {
		try {
			
			return (Boolean) source;
		} catch (ClassCastException e) {
			System.out.println("Boolean ClassCastException triggered");
			return Boolean.FALSE;
		}
	}

	@Override
	public List<Property<?>> toGenericProperties(List<BooleanProperty> properties) {
		if (properties == null) {
            return null;
        }

        List<Property<?>> list = new ArrayList<Property<?>>(properties.size());
        for (BooleanProperty prop : properties) {
        	list.add(toGenericProperty(prop));
        }

        return list;
	}

	@Override
	public List<BooleanProperty> toTypeProperties(List<Property<?>> properties) {
		if (properties == null) {
            return null;
        }

        List<BooleanProperty> list = new ArrayList<BooleanProperty>(properties.size());
        for (Property<?> prop : properties) {
        	list.add(toTypeProperty(prop));
        }

        return list;
	}



	


	
	

}
