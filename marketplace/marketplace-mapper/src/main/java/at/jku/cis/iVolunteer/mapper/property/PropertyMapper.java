package at.jku.cis.iVolunteer.mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.competence.CompetenceMapper;
import at.jku.cis.iVolunteer.mapper.property.listEntry.ListEntryMapper;
import at.jku.cis.iVolunteer.mapper.property.rule.MultiRuleMapper;
import at.jku.cis.iVolunteer.mapper.property.rule.SinglePropertyRuleMapper;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.SingleProperty;
import at.jku.cis.iVolunteer.model.property.MultiProperty;
import at.jku.cis.iVolunteer.model.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.dto.PropertyDTO;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;
import at.jku.cis.iVolunteer.model.property.rule.MultiPropertyRule;
import at.jku.cis.iVolunteer.model.property.rule.SinglePropertyRule;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;


//@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class PropertyMapper implements AbstractMapper<Property, PropertyDTO<Object>> {
	
	@Autowired SinglePropertyRuleMapper ruleMapper;
	@Autowired MultiRuleMapper multiRuleMapper;
	
	@Autowired CompetenceMapper competenceMapper;
	@Autowired ListEntryMapper listEntryMapper;
	@Autowired MultiPropertyMapper multiplePropertyMapper;
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
		propertyDTO.setOrder(source.getOrder());
		propertyDTO.setCustom(source.isCustom());
		
		
		//TODO rules

		
		
		if (propertyDTO.getKind().equals(PropertyKind.MULTI) || propertyDTO.getKind().equals(PropertyKind.MAP)) {
			List<PropertyDTO<Object>> props = new ArrayList<>();
			
			for (Property p : ((MultiProperty)source).getProperties()) {
				props.add(propertyMapper.toDTO(p));
			}
			
			propertyDTO.setProperties(props);
			
			if (((MultiProperty)source).getRules() != null) {
				List<MultiPropertyRule> rules = new ArrayList<>();
				for (MultiPropertyRule r : ((MultiProperty)source).getRules()) {
					rules.add(r);
				}
				propertyDTO.setRules(multiRuleMapper.toDTOs(rules));
			}
			
			
			
		} else {
			SingleProperty<Object> s = new SingleProperty<Object>(source);
			
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
			
			if (s.getDefaultValues() != null) {
				List<ListEntryDTO<Object>> values = new ArrayList<>();
				for (ListEntry<Object> entry : s.getDefaultValues()) {
					values.add(listEntryMapper.toDTO(entry));
				}
				propertyDTO.setDefaultValues(values);
			}
			
			
			if (((SingleProperty<?>)source).getRules() != null) {
				List<SinglePropertyRule> rules = new ArrayList<>();
				for (SinglePropertyRule r : ((SingleProperty<?>)source).getRules()) {
					rules.add(r);
				}
				propertyDTO.setRules(ruleMapper.toDTOs(rules));
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
		prop.setOrder(target.getOrder());
		prop.setCustom(target.isCustom());		
		
		
		
		if (prop.getKind().equals(PropertyKind.MULTI)) {
			MultiProperty ret = new MultiProperty(prop);
			List<Property> props = new ArrayList<>();
			
			if (target.getProperties() != null) {
				for (PropertyDTO<Object> dto : target.getProperties()) {
					props.add(propertyMapper.toEntity(dto));
				}
			}
//			((MultipleProperty)prop).setProperties(props);
			ret.setProperties(props);
			
			List<MultiPropertyRule> rules = new ArrayList<>();
			if (target.getRules() != null) {
				for (RuleDTO r : target.getRules()) {
					rules.add(multiRuleMapper.toEntity(r));
				}
			}
			ret.setRules(rules);
			
			return ret;
		
		} else {
			SingleProperty<Object> ret = new SingleProperty<>(prop);
			
			List<ListEntry<Object>> values = new ArrayList<>();
			
			if (target.getValues() != null) {
				for (ListEntryDTO<Object> entry : target.getValues()) {
					values.add(listEntryMapper.toEntity(entry, target.getKind()));
				}
			}
			ret.setValues(values);
			
			
			List<ListEntry<Object>> legalValues = new ArrayList<ListEntry<Object>>();
			
			if (target.getLegalValues() != null) {
				for (ListEntryDTO<Object> entry : target.getLegalValues()) {
					legalValues.add(listEntryMapper.toEntity(entry, target.getKind()));
				}
			}
			
			ret.setLegalValues(legalValues);
			
			List<ListEntry<Object>> defaultValues = new ArrayList<ListEntry<Object>>();
			
			if (target.getDefaultValues() != null) {
				for (ListEntryDTO<Object> entry : target.getDefaultValues()) {
					defaultValues.add(listEntryMapper.toEntity(entry, target.getKind()));
				}
			}
			
			ret.setDefaultValues(defaultValues);
			
			
			List<SinglePropertyRule> rules = new ArrayList<SinglePropertyRule>();
			if (target.getRules() != null) {
				System.out.println("Property Mapper: 213 get rules");
				for (RuleDTO r : target.getRules()) {
					rules.add(ruleMapper.toEntity(r));
				}
			}
			ret.setRules(rules);
			
			
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
	
//	//TODO ??not sure if fixed - kinda ugly 
//		private Date convertObjectToDate(Object source) {
//			try {
//				
//				//System.out.println("convert: " + source);
//				
//				if (source instanceof Long) {
//					//System.out.println("convert long - " + source.getClass().getName());
//					return new Date((Long)source);
//					
//				} else if (source instanceof Date) {
//					//System.out.println("convert Date - " + source.getClass().getName());
//					return (Date) source;
//				} else if (source instanceof String) {
//					//System.out.println("convert String - " + source.getClass().getName());
//					
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//					Date date = sdf.parse((String) source); 
//					
//					return date;
//				} else if (source == null) {
////					Date date = new Date(0);
//					return null;
//					
//				} else {
//					//System.out.println("class: " + source.getClass().getName());
//					//System.out.println(source);
//					throw new IllegalArgumentException();
//				}
//				
//			} catch (NullPointerException | NumberFormatException e ) {
//				System.out.println("entered Exception Branch convert Object to Date");
//				return null;
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				System.out.println("Unparsable Date");
//				return null;
//			}
//		}
//		
//		private Double convertObjectToDouble(Object source) {
//			try {
//				
//				return (Double) source;
//			} catch (ClassCastException e) {
//				try {
//						System.out.println("PropertyMapper: Double ClassCastException triggered: " + source + " trying to parse");
//						double ret;
//						
//						if (source instanceof String) {
//							ret =  Double.parseDouble((String) source);
//							return ret;
//						} else if (source instanceof Integer) {
//							ret = (Integer) source;
//							return ret;
//						} else {
//							throw new IllegalArgumentException("PropertyMapper - unable to parse - should not happen: " + source);
//						}
//						
//					} catch (NumberFormatException e2) {
//						System.out.println("PropertyMapper: NumberformatException triggered: returning 0.0");
//						return 0.0;
//					}
//			}
//		}
//		
//		private Object convertObjectToInteger(Object source) {
//			try {
//				
//				if (source == null) {
//					return source;
//				}
//				return (int) source;
//			} catch (ClassCastException e) {
//				try {
//					System.out.println("PropertyMapper: Long CCE triggered: " + source + "trying to pars ");
//					int ret = Integer.parseInt((String) source);
//					return ret;
//				} catch (NumberFormatException e2) {
//					System.out.println("PropertyMapper: NumberFormatException triggered: returning 0");
//					return 0;
//				}
//			}
//		}

	
}
