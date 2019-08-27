package at.jku.cis.iVolunteer.mapper.property;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@Component
public class PropertyValueConverter {
	
	public Object convert (Object source, PropertyType type) {
		Object ret;
		
		if (type.equals(PropertyType.DATE)) {
			ret = convertObjectToDate(source);
		} else if (type.equals(PropertyType.FLOAT_NUMBER)) {
			ret = convertObjectToDouble(source);
		} else if (type.equals(PropertyType.WHOLE_NUMBER)) {
			ret = convertObjectToInteger(source);
		} else {
			ret = source;
		}
		
		
		return ret;
	}
	
	//TODO ??not sure if fixed - kinda ugly 
			private Date convertObjectToDate(Object source) {
				try {
					
					//System.out.println("convert: " + source);
					
					if (source instanceof Long) {
						//System.out.println("convert long - " + source.getClass().getName());
						return new Date((Long)source);
						
					} else if (source instanceof Date) {
						//System.out.println("convert Date - " + source.getClass().getName());
						return (Date) source;
					} else if (source instanceof String) {
						//System.out.println("convert String - " + source.getClass().getName());
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
						Date date = sdf.parse((String) source); 
						
						return date;
					} else if (source == null) {
//						Date date = new Date(0);
						return null;
						
					} else {
						//System.out.println("class: " + source.getClass().getName());
						//System.out.println(source);
						throw new IllegalArgumentException();
					}
					
				} catch (NullPointerException | NumberFormatException e ) {
					System.out.println("entered Exception Branch convert Object to Date");
					return null;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.out.println("Unparsable Date");
					return null;
				}
			}
			
			private Double convertObjectToDouble(Object source) {
				try {
					
					return (Double) source;
				} catch (ClassCastException e) {
					try {
							System.out.println("PropertyMapper: Double ClassCastException triggered: " + source + " trying to parse");
							double ret;
							
							if (source instanceof String) {
								ret =  Double.parseDouble((String) source);
								return ret;
							} else if (source instanceof Integer) {
								ret = (Integer) source;
								return ret;
							} else {
								throw new IllegalArgumentException("PropertyMapper - unable to parse - should not happen: " + source);
							}
							
						} catch (NumberFormatException e2) {
							System.out.println("PropertyMapper: NumberformatException triggered: returning 0.0");
							return 0.0;
						}
				}
			}
			
			private long convertObjectToInteger(Object source) {
				try {
					return (int) source;
				} catch (ClassCastException e) {
					try {
						System.out.println("PropertyMapper: Long CCE triggered: " + source + "trying to pars ");
						long ret = Integer.parseInt((String) source);
						return ret;
					} catch (NumberFormatException e2) {
						System.out.println("PropertyMapper: NumberFormatException triggered: returning 0");
						return 0;
					}
				}
			}

}
