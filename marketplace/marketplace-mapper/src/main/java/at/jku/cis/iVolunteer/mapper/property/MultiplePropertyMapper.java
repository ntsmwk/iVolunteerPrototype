package at.jku.cis.iVolunteer.mapper.property;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.competence.CompetenceMapper;
import at.jku.cis.iVolunteer.mapper.property.listEntry.ListEntryMapper;
import at.jku.cis.iVolunteer.mapper.property.rule.RuleMapper;
import at.jku.cis.iVolunteer.model.property.MultipleProperty;
import at.jku.cis.iVolunteer.model.property.dto.MultiplePropertyDTO;
import at.jku.cis.iVolunteer.model.property.rule.Rule;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;


//@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class MultiplePropertyMapper implements AbstractMapper<MultipleProperty, MultiplePropertyDTO>{
	
	@Autowired RuleMapper ruleMapper;
	@Autowired CompetenceMapper competenceMapper;
	@Autowired ListEntryMapper listEntryMapper;
	@Autowired PropertyMapper propertyMapper;

	@Override
	public MultiplePropertyDTO toDTO(MultipleProperty source) {
		
		if (source == null) {
			return null;
		}
		
		MultiplePropertyDTO propertyDTO = new MultiplePropertyDTO();
		
		propertyDTO.setId(source.getId());
		propertyDTO.setName(source.getName());
		propertyDTO.setKind(source.getKind());
		propertyDTO.setOrder(source.getOrder());
		
		propertyDTO.setProperties(propertyMapper.toDTOs(source.getProperties()));
		
		//TODO rules
		if (source.getRules() != null) {
			List<Rule> rules = new ArrayList<Rule>();
			for (Rule r : source.getRules()) {
				rules.add(r);
			}
			propertyDTO.setRules(ruleMapper.toDTOs(rules));
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
	public List<MultiplePropertyDTO> toDTOs(List<MultipleProperty> sources) {
		if (sources == null)  {
			return null;
		}
		
		List<MultiplePropertyDTO> list = new ArrayList<MultiplePropertyDTO>(sources.size());
        for ( MultipleProperty propItem : sources ) {
            list.add( toDTO( propItem ) );
        }
		return list;
	}



	@Override
	public MultipleProperty toEntity(MultiplePropertyDTO target) {
		
		if (target == null) {
			return null;
		}
		
		MultipleProperty prop = new MultipleProperty();
		
		prop.setId(target.getId());
		prop.setName(target.getName());
		prop.setKind(target.getKind());
		prop.setOrder(target.getOrder());
		
		prop.setProperties(propertyMapper.toEntities(target.getProperties()));
				
		List<Rule> rules = new LinkedList<Rule>();
		if (target.getRules() != null) {
			for (RuleDTO r : target.getRules()) {
				rules.add(ruleMapper.toEntity(r));
			}
		}
		prop.setRules(rules);
		
		
		
		return prop;
	
	}
	

	@Override
	public List<MultipleProperty> toEntities(List<MultiplePropertyDTO> targets) {
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
