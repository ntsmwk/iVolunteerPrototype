package at.jku.cis.iVolunteer.mapper.property.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.PropertyToTypePropertyMapper;
import at.jku.cis.iVolunteer.model.property.DoubleProperty;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;



@Component
public class PropertyToDoublePropertyMapper implements PropertyToTypePropertyMapper<DoubleProperty> {

	@Override
	public Property<?> toGenericProperty(DoubleProperty source) {
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
	public DoubleProperty toTypeProperty(Property<?> property) {
		if ( property == null ) {
            return null;
        }
		
		DoubleProperty dProp = new DoubleProperty();
		dProp.setId(property.getId());
		
		
		dProp.setDefaultValue(convertObjectToDouble((property.getDefaultValue())));
		
		
		dProp.setKind(property.getKind());
		

		List<ListEntry<Double>> legalValues = new ArrayList<ListEntry<Double>>();
		if (property.getLegalValues() != null) {
			for (ListEntry entry : property.getLegalValues()) {
				
				ListEntry<Double> iEntry = new ListEntry<Double>(entry.getId(), convertObjectToDouble(entry.getValue()));
				legalValues.add(iEntry);
			}
		}
		dProp.setLegalValues(legalValues);
		
		List<ListEntry<Double>> values = new ArrayList<ListEntry<Double>>();
		if (property.getValues() != null) {
			for (ListEntry entry : property.getValues()) {
				ListEntry<Double> iEntry = new ListEntry<Double>(entry.getId(), convertObjectToDouble(entry.getValue()));
				values.add(iEntry);
			}
		}
		dProp.setValues(values);
		
		
		dProp.setName(property.getName());
		dProp.setRules(property.getRules());
		dProp.setValue(convertObjectToDouble(property.getValue()));
		return dProp;
	}

	

	
	private Double convertObjectToDouble(Object source) {
		try {
			
			return (Double) source;
		} catch (ClassCastException e) {
			System.out.println("Double ClassCastException triggered");
			return Double.NaN;
		}
	}

	@Override
	public List<Property<?>> toGenericProperties(List<DoubleProperty> properties) {
		if (properties == null) {
            return null;
        }

        List<Property<?>> list = new ArrayList<Property<?>>(properties.size());
        for (DoubleProperty prop : properties) {
        	list.add(toGenericProperty(prop));
        }

        return list;
	}

	@Override
	public List<DoubleProperty> toTypeProperties(List<Property<?>> properties) {
		if (properties == null) {
            return null;
        }

        List<DoubleProperty> list = new ArrayList<DoubleProperty>(properties.size());
        for (Property prop : properties) {
        	list.add(toTypeProperty(prop));
        }

        return list;
	}



	


	
	

}
