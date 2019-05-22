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
//import at.jku.cis.iVolunteer.model.property.Property;
//import at.jku.cis.iVolunteer.model.property.TextProperty;
//import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
//
//
//@SuppressWarnings({ "rawtypes", "unchecked" })
//@Component
//public class PropertyToTextPropertyMapper implements PropertyToTypePropertyMapper<TextProperty> {
//
//	
//	@Override
//	public Property<?> toGenericProperty(TextProperty source) {
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
//	public TextProperty toTypeProperty(Property<?> property) {
//		if ( property == null ) {
//            return null;
//        }
//		
//		TextProperty tProp = new TextProperty();
//		tProp.setId(property.getId());
//		
//		
//		tProp.setDefaultValue(convertObjectToString((property.getDefaultValue())));
//		
//		
//		tProp.setKind(property.getKind());
//		
//
//		List<ListEntry<String>> legalValues = new ArrayList<ListEntry<String>>();
//		if (property.getLegalValues() != null) {
//			for (ListEntry entry : property.getLegalValues()) {
//				
//				ListEntry<String> sEntry = new ListEntry<String>(entry.getId(), convertObjectToString(entry.getValue()));
//				legalValues.add(sEntry);
//			}
//		}
//		tProp.setLegalValues(legalValues);
//		
//		List<ListEntry<String>> values = new ArrayList<ListEntry<String>>();
//		if (property.getValues() != null) {
//			for (ListEntry entry : property.getValues()) {
//				ListEntry<String> sEntry = new ListEntry<String>(entry.getId(), convertObjectToString(entry.getValue()));
//				values.add(sEntry);
//			}
//		}
//		tProp.setValues(values);
//		
//		tProp.setName(property.getName());
//		tProp.setRules(property.getRules());
//		tProp.setValue(convertObjectToString(property.getValue()));
//		return tProp;
//	}
//
//	
//
//	
//	private String convertObjectToString(Object source) {
//		try {
//			
//			return (String) source;
//		} catch (ClassCastException e) {
//			System.out.println("String ClassCastException triggered");
//			return "";
//		}
//	}
//
//	@Override
//	public List<Property<?>> toGenericProperties(List<TextProperty> properties) {
//		if (properties == null) {
//            return null;
//        }
//
//        List<Property<?>> list = new ArrayList<Property<?>>(properties.size());
//        for (TextProperty prop : properties) {
//        	list.add(toGenericProperty(prop));
//        }
//
//        return list;
//	}
//
//	@Override
//	public List<TextProperty> toTypeProperties(List<Property<?>> properties) {
//		if (properties == null) {
//            return null;
//        }
//
//        List<TextProperty> list = new ArrayList<TextProperty>(properties.size());
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
