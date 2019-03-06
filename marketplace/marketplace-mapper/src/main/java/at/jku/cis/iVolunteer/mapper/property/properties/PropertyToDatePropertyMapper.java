// TODO delete
//
//package at.jku.cis.iVolunteer.mapper.property.properties;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.stereotype.Component;
//
//import at.jku.cis.iVolunteer.mapper.PropertyToTypePropertyMapper;
//import at.jku.cis.iVolunteer.model.property.DateProperty;
//import at.jku.cis.iVolunteer.model.property.Property;
//import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
//
//@SuppressWarnings({ "rawtypes", "unchecked" })
//@Component
//public class PropertyToDatePropertyMapper implements PropertyToTypePropertyMapper<DateProperty> {
//
//	@Override
//	public Property<?> toGenericProperty(DateProperty source) {
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
//		List<ListEntry> values = new ArrayList<ListEntry>();
//		if (source.getValues() != null) {
//			for (ListEntry entry : source.getLegalValues()) {
//				values.add(entry);
//			}
//		}
//		prop.setValues(values);
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
//	public DateProperty toTypeProperty(Property<?> property) {
//		
//		if ( property == null ) {
//            return null;
//        }
//		
//		DateProperty dProp = new DateProperty();
//		dProp.setId(property.getId());
//		
//		dProp.setDefaultValue(convertObjectToDate((property.getDefaultValue())));
//		
//		dProp.setKind(property.getKind());
//		
//
//		List<ListEntry<Date>> legalValues = new ArrayList<ListEntry<Date>>();
//		if (property.getLegalValues() != null) {
//			for (ListEntry entry : property.getLegalValues()) {
//				legalValues.add(new ListEntry<Date>(entry.getId(), convertObjectToDate(entry.getValue())));
//			}
//		}
//		dProp.setLegalValues(legalValues);
//		
//		List<ListEntry<Date>> values = new ArrayList<ListEntry<Date>>();
//		if (property.getValues() != null) {
//			for (ListEntry entry : property.getLegalValues()) {
//				values.add(new ListEntry<Date>(entry.getId(), convertObjectToDate(entry.getValue())));
//			}
//		}
//		dProp.setValues(values);
//		
//		dProp.setName(property.getName());
//		dProp.setRules(property.getRules());
//		dProp.setValue(convertObjectToDate(property.getValue()));
//		return dProp;
//	}
//	
//	//TODO ??not sure if fixed
//	private Date convertObjectToDate(Object source) {
//		try {
//			
//			//System.out.println("convert: " + source);
//			
//			if (source instanceof Long) {
//				//System.out.println("convert long - " + source.getClass().getName());
//				return new Date((Long)source);
//				
//			} else if (source instanceof Date) {
//				//System.out.println("convert Date - " + source.getClass().getName());
//				return (Date) source;
//			} else if (source instanceof String) {
//				//System.out.println("convert String - " + source.getClass().getName());
//				
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//				Date date = sdf.parse((String) source); 
//				
//				return date;
//			} else if (source == null) {
////				Date date = new Date(0);
//				return null;
//				
//			} else {
//				//System.out.println("class: " + source.getClass().getName());
//				//System.out.println(source);
//				throw new IllegalArgumentException();
//			}
//			
//		} catch (NullPointerException | NumberFormatException e ) {
//			System.out.println("entered Exception Branch convert Object to Date");
//			return null;
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			System.out.println("Unparsable Date");
//			return null;
//		}
//	}
//	
//	
//
//	@Override
//	public List<Property<?>> toGenericProperties(List<DateProperty> sources) {
//		if (sources == null) {
//            return null;
//        }
//
//        List<Property<?>> list = new ArrayList<Property<?>>(sources.size());
//        for (DateProperty prop : sources) {
//        	list.add(toGenericProperty(prop));
//        }
//
//        return list;
//	}
//
//	@Override
//	public List<DateProperty> toTypeProperties(List<Property<?>> properties) {
//		if (properties == null) {
//            return null;
//        }
//
//        List<DateProperty> list = new ArrayList<DateProperty>(properties.size());
//        for (Property prop : properties) {
//        	list.add(toTypeProperty(prop));
//        }
//
//        return list;
//	}
//
//}
