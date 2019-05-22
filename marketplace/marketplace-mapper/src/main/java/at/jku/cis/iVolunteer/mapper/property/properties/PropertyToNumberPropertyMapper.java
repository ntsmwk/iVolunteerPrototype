// TODO delete
//
//package at.jku.cis.iVolunteer.mapper.property.properties;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.stereotype.Component;
//
//import at.jku.cis.iVolunteer.mapper.PropertyToTypePropertyMapper;
//import at.jku.cis.iVolunteer.model.property.NumberProperty;
//import at.jku.cis.iVolunteer.model.property.Property;
//import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
//
//
//@SuppressWarnings({ "rawtypes", "unchecked" })
//@Component
//public class PropertyToNumberPropertyMapper implements PropertyToTypePropertyMapper<NumberProperty> {
//
//	@Override
//	public Property<?> toGenericProperty(NumberProperty source) {
//		if ( source == null ) {
//            return null;
//        }
//		
//		Property prop = new Property();
//		prop.setId(source.getId());
//		prop.setDefaultValue(source.getDefaultValue());
//		prop.setKind(source.getKind());
//		
//		
//		List<ListEntry> legalValues = new ArrayList<ListEntry>();
//		if (source.getLegalValues() != null) {
//			for (ListEntry entry : source.getLegalValues()) {
//				legalValues.add(entry);
//			}
//			
//		}
//		prop.setLegalValues(legalValues);
//		
//		prop.setName(source.getName());
//		prop.setRules(source.getRules());
//		prop.setValue(source.getValue());
//		
//		
//		
//		return prop;
//	}
//	
//	@Override
//	public NumberProperty toTypeProperty(Property<?> property) {
//		if ( property == null ) {
//            return null;
//        }
//		
//		NumberProperty nProp = new NumberProperty();
//		nProp.setId(property.getId());
//		
//		
//		nProp.setDefaultValue(convertObjectToInteger((property.getDefaultValue())));
//		
//		
//		nProp.setKind(property.getKind());
//		
//
//		List<ListEntry<Integer>> legalValues = new ArrayList<ListEntry<Integer>>();
//		if (property.getLegalValues() != null) {
//			for (ListEntry entry : property.getLegalValues()) {
//				
//				ListEntry<Integer> iEntry = new ListEntry<Integer>(entry.getId(), convertObjectToInteger(entry.getValue()));
//				legalValues.add(iEntry);
//			}
//		}
//		nProp.setLegalValues(legalValues);
//		
//		List<ListEntry<Integer>> values = new ArrayList<ListEntry<Integer>>();
//		if (property.getValues() != null) {
//			for (ListEntry entry : property.getValues()) {
//				
//				ListEntry<Integer> iEntry = new ListEntry<Integer>(entry.getId(), convertObjectToInteger(entry.getValue()));
//				values.add(iEntry);
//			}
//		}
//		nProp.setLegalValues(values);
//		
//		
//		nProp.setName(property.getName());
//		nProp.setRules(property.getRules());
//		nProp.setValue(convertObjectToInteger(property.getValue()));
//		return nProp;
//	}
//
//	
//
//	
//	private Integer convertObjectToInteger(Object source) {
//		try {
//			if (source instanceof String) {
//				return Integer.parseInt((String) source);
//			} else if (source instanceof Integer) {
//				return (Integer) source;
//			}
//			return (Integer) source;
//			
//		} catch (ClassCastException e) {
//			
//			System.out.println("Integer ClassCastException triggered");
//			return 0;
//		} catch (NumberFormatException e) {
//			System.out.println("NumberFormatException triggered");
//			return 0;
//		}
//	}
//
//	@Override
//	public List<Property<?>> toGenericProperties(List<NumberProperty> properties) {
//		if (properties == null) {
//            return null;
//        }
//
//        List<Property<?>> list = new ArrayList<Property<?>>(properties.size());
//        for (NumberProperty prop : properties) {
//        	list.add(toGenericProperty(prop));
//        }
//
//        return list;
//	}
//
//	@Override
//	public List<NumberProperty> toTypeProperties(List<Property<?>> properties) {
//		if (properties == null) {
//            return null;
//        }
//
//        List<NumberProperty> list = new ArrayList<NumberProperty>(properties.size());
//        for (Property prop : properties) {
//        	list.add(toTypeProperty(prop));
//        }
//
//        return list;
//	}
//
//
//
//	
//
//
//	
//	
//
//}
