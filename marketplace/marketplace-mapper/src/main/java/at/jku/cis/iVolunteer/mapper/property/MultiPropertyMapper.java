package at.jku.cis.iVolunteer.mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.competence.CompetenceMapper;
import at.jku.cis.iVolunteer.mapper.property.listEntry.ListEntryMapper;
import at.jku.cis.iVolunteer.mapper.property.rule.MultiRuleMapper;
import at.jku.cis.iVolunteer.model.configurable.configurables.property.MultiProperty;
import at.jku.cis.iVolunteer.model.property.dto.MultiPropertyDTO;
import at.jku.cis.iVolunteer.model.property.rule.MultiPropertyRule;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;


//@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class MultiPropertyMapper implements AbstractMapper<MultiProperty, MultiPropertyDTO>{
	
	@Autowired MultiRuleMapper multiRuleMapper;
	@Autowired CompetenceMapper competenceMapper;
	@Autowired ListEntryMapper listEntryMapper;
	@Autowired PropertyMapper propertyMapper;

	@Override
	public MultiPropertyDTO toDTO(MultiProperty source) {
		
		if (source == null) {
			return null;
		}
		
		MultiPropertyDTO propertyDTO = new MultiPropertyDTO();
		
		propertyDTO.setId(source.getId());
		propertyDTO.setName(source.getName());
		propertyDTO.setKind(source.getKind());
		propertyDTO.setOrder(source.getOrder());
		
		propertyDTO.setProperties(propertyMapper.toDTOs(source.getProperties()));
		
		//TODO rules
		if (source.getRules() != null) {
			List<MultiPropertyRule> rules = new ArrayList<>();
			for (MultiPropertyRule r : source.getRules()) {
				rules.add(r);
			}
			propertyDTO.setRules(multiRuleMapper.toDTOs(rules));
		}
		
//		
//		propertyDTO.setValue(source.getValue());
//		
//		
//		propertyDTO.setKind(source.getKind());
//		propertyDTO.setDefaultValue(source.getDefaultValue());
//		
//		//TODO legal Values
//		if (source.getLegalValues() != null) {
//			
//			List<ListEntryDTO<Object>> legalValues = new ArrayList<>();
//			
//				for(ListEntry<Object> entry : source.getLegalValues()) {
//					legalValues.add(listEntryMapper.toDTO(entry));
//				}
//		
//			
//			propertyDTO.setLegalValues(legalValues);
//		}
//		

//		
//		//TODO values
//		
//		if (source.getValues() != null) {
//			List<ListEntryDTO<Object>> values = new ArrayList<>();
//			for (ListEntry<Object> entry : source.getValues()) {
//				values.add(listEntryMapper.toDTO(entry));
//			}
//			propertyDTO.setValues(values);
//		}
		
		return propertyDTO;
	}

	@Override
	public List<MultiPropertyDTO> toDTOs(List<MultiProperty> sources) {
		if (sources == null)  {
			return null;
		}
		
		List<MultiPropertyDTO> list = new ArrayList<MultiPropertyDTO>(sources.size());
        for ( MultiProperty propItem : sources ) {
            list.add( toDTO( propItem ) );
        }
		return list;
	}



	@Override
	public MultiProperty toEntity(MultiPropertyDTO target) {
		
		if (target == null) {
			return null;
		}
		
		MultiProperty prop = new MultiProperty();
		
		prop.setId(target.getId());
		prop.setName(target.getName());
		prop.setKind(target.getKind());
		prop.setOrder(target.getOrder());
		
		prop.setProperties(propertyMapper.toEntities(target.getProperties()));
				
		List<MultiPropertyRule> rules = new ArrayList<>();
		if (target.getRules() != null) {
			for (RuleDTO r : target.getRules()) {
				rules.add(multiRuleMapper.toEntity(r));
			}
		}
		prop.setRules(rules);
		
		
		
		return prop;
	
	}
	

	@Override
	public List<MultiProperty> toEntities(List<MultiPropertyDTO> targets) {
		// TODO Auto-generated method stub
		return null;
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
//				System.out.println("Double ClassCastException triggered: " + source);
//				return Double.parseDouble((String) source);
//			} catch (NumberFormatException e) {
//				return 0.0;
//			}
//		}

	
}
