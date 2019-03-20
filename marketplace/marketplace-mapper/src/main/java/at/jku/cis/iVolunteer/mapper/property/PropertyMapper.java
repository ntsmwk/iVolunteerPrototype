package at.jku.cis.iVolunteer.mapper.property;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.competence.CompetenceMapper;
import at.jku.cis.iVolunteer.mapper.property.listEntry.ListEntryMapper;
import at.jku.cis.iVolunteer.mapper.property.rule.RuleMapper;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.SingleProperty;
import at.jku.cis.iVolunteer.model.property.MultipleProperty;
import at.jku.cis.iVolunteer.model.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.dto.PropertyDTO;
import at.jku.cis.iVolunteer.model.property.dto.SinglePropertyDTO;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;
import at.jku.cis.iVolunteer.model.property.rule.Rule;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;


//@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class PropertyMapper implements AbstractMapper<Property, PropertyDTO<Object>> {
	
	@Autowired RuleMapper ruleMapper;
	@Autowired CompetenceMapper competenceMapper;
	@Autowired ListEntryMapper listEntryMapper;
	@Autowired MultiplePropertyMapper multiplePropertyMapper;
	@Autowired PropertyMapper propertyMapper;

	@Override
	public PropertyDTO<Object> toDTO(Property source) {
		
		if (source == null) {
			return null;
		}
		
		PropertyDTO<Object> propertyDTO = new PropertyDTO<Object>();
		propertyDTO.setId(source.getId());
		propertyDTO.setName(source.getName());
		propertyDTO.setKind(source.getKind());
		
		
		//TODO rules
		if (source.getRules() != null) {
			List<Rule> rules = new ArrayList<Rule>();
			for (Rule r : source.getRules()) {
				rules.add(r);
			}
			propertyDTO.setRules(ruleMapper.toDTOs(rules));
		}
		
		
		if (propertyDTO.getKind().equals(PropertyKind.MULTIPLE)) {
			List<PropertyDTO<Object>> props = new LinkedList<>();
			
			for (Property p : ((MultipleProperty)source).getProperties()) {
				props.add(propertyMapper.toDTO(p));
			}
			
			propertyDTO.setProperties(props);
			
		} else {
			SingleProperty<Object> s = (SingleProperty<Object>) source;
			
			propertyDTO.setValue(s.getValue());
			propertyDTO.setDefaultValue(s.getDefaultValue());
			
			//TODO legal Values
			if (s.getLegalValues() != null) {
				
				List<ListEntryDTO<Object>> legalValues = new ArrayList<>();
				
					for(ListEntry<Object> entry : s.getLegalValues()) {
						legalValues.add(listEntryMapper.toDTO(entry));
					}
			
				
				propertyDTO.setLegalValues(legalValues);
			}
			
			//TODO values
			if (s.getValues() != null) {
				List<ListEntryDTO<Object>> values = new ArrayList<>();
				for (ListEntry<Object> entry : s.getValues()) {
					values.add(listEntryMapper.toDTO(entry));
				}
				propertyDTO.setValues(values);
			}
			
		}

		
		return propertyDTO;
	}

	@Override
	public List<PropertyDTO<Object>> toDTOs(List<Property> sources) {
		if (sources == null)  {
			return null;
		}
		
		List<PropertyDTO<Object>> list = new ArrayList<PropertyDTO<Object>>(sources.size());
        for ( Property propItem : sources ) {
            list.add( toDTO( propItem ) );
        }
		return list;
	}



	@Override
	public Property toEntity(PropertyDTO<Object> target) {
		
		if (target == null) {
			return null;
		}
		
		Property prop = new Property();
		
		prop.setId(target.getId());
		prop.setName(target.getName());
		prop.setKind(target.getKind());
		
		List<Rule> rules = new LinkedList<Rule>();
		if (target.getRules() != null) {
			for (RuleDTO r : target.getRules()) {
				rules.add(ruleMapper.toEntity(r));
			}
		}
		prop.setRules(rules);
		
		
		if (prop.getKind().equals(PropertyKind.MULTIPLE)) {
			MultipleProperty ret = new MultipleProperty(prop);
			List<Property> props = new LinkedList<>();
			
			if (target.getProperties() != null) {
				for (PropertyDTO<Object> dto : target.getProperties()) {
					props.add(propertyMapper.toEntity(dto));
				}
			}
//			((MultipleProperty)prop).setProperties(props);
			ret.setProperties(props);
			
			return ret;
		
		} else {
			SingleProperty<Object> ret = new SingleProperty<>(prop);

			if (target.getKind().equals(PropertyKind.DATE)) {
				ret.setValue(this.convertObjectToDate(target.getValue()));
				ret.setDefaultValue(this.convertObjectToDate(target.getDefaultValue()));
			
			
			} else if (target.getKind().equals(PropertyKind.FLOAT_NUMBER)) {
				ret.setValue(this.convertObjectToDouble(target.getValue()));
				ret.setDefaultValue(this.convertObjectToDouble(target.getDefaultValue()));
			} else {
				ret.setValue(target.getValue());
				ret.setDefaultValue(target.getDefaultValue());
			
			}
					
			
			
			List<ListEntry<Object>> values = new LinkedList<>();
			
			if (target.getValues() != null) {
				for (ListEntryDTO<Object> entry : target.getValues()) {
					values.add(listEntryMapper.toEntity(entry));
				}
			}
			ret.setValues(values);
			
			
			List<ListEntry<Object>> legalValues = new LinkedList<ListEntry<Object>>();
			
			if (target.getLegalValues() != null) {
				for (ListEntryDTO<Object> entry : target.getLegalValues()) {
					legalValues.add(listEntryMapper.toEntity(entry));
				}
			}
			
			ret.setLegalValues(legalValues);
			
			return ret;
			
		}
		
		
		
	
	}
	

	@Override
	public List<Property> toEntities(List<PropertyDTO<Object>> targets) {
		if (targets == null) {
			return null;
		}
		
		List<Property> list = new ArrayList<>();
		for (PropertyDTO<Object> prop : targets) {
			list.add(this.toEntity(prop));
		}
		
		return list;
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
//					Date date = new Date(0);
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
						System.out.println("Double ClassCastException triggered: " + source + " returning 0.0");
						double ret =  Double.parseDouble((String) source);
						return ret;
					} catch (NumberFormatException e2) {
						return 0.0;
					}
			}
		}

	
}
