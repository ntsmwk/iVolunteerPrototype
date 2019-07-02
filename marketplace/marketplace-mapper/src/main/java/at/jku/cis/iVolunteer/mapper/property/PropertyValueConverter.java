package at.jku.cis.iVolunteer.mapper.property;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model.property.PropertyKind;

@Component
public class PropertyValueConverter {
	
	public Object convert (Object source, PropertyKind kind) {
		Object ret;
		
		if (kind.equals(PropertyKind.DATE)) {
			ret = convertObjectToDate(source);
		} else if (kind.equals(PropertyKind.FLOAT_NUMBER)) {
			ret = convertObjectToDouble(source);
		} else if (kind.equals(PropertyKind.WHOLE_NUMBER)) {
			ret = convertObjectToInteger(source);
		} else {
			ret = source;
		}
		
		
		return ret;
	}
	
	//TODO ??not sure if fixed - kinda ugly 
	private Date convertObjectToDate(Object source) {
		try {
				
			if (source instanceof Long) {
				return new Date((Long)source);
				
			} else if (source instanceof Date) {
				return (Date) source;
			} else if (source instanceof String) {				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				Date date = sdf.parse((String) source); 
				
				return date;
			} else if (source == null) {
				return null;
				
			} else {
				throw new IllegalArgumentException();
			}
			
		} catch (NullPointerException | NumberFormatException e ) {
			return null;
		} catch (ParseException e) {
			System.out.println("Unparsable Date");
			return null;
		}
	}
	
	private Double convertObjectToDouble(Object source) {
		try {
			
			return (Double) source;
		} catch (ClassCastException e) {
			try {
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
				return 0.0;
			}
		}
	}
	
	private long convertObjectToInteger(Object source) {
		try {
			return (int) source;
		} catch (ClassCastException e) {
			try {
				long ret = Integer.parseInt((String) source);
				return ret;
			} catch (NumberFormatException e2) {
				return 0;
			}
		}
	}

}
