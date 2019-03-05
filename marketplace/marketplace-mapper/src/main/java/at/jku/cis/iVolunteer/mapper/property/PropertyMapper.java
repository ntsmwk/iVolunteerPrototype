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
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.dto.PropertyDTO;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;
import at.jku.cis.iVolunteer.model.property.rule.Rule;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;


@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class PropertyMapper implements AbstractMapper<Property<?>, PropertyDTO<?>>{
	
	@Autowired RuleMapper ruleMapper;
	@Autowired CompetenceMapper competenceMapper;
	@Autowired ListEntryMapper listEntryMapper;

	@Override
	public PropertyDTO<?> toDTO(Property<?> source) {
		
		if (source == null) {
			return null;
		}
		
		PropertyDTO propertyDTO = new PropertyDTO();
		propertyDTO.setId(source.getId());
		propertyDTO.setName(source.getName());
		propertyDTO.setValue(source.getValue());
		propertyDTO.setKind(source.getKind());
		propertyDTO.setDefaultValue(source.getDefaultValue());
		
		//TODO legal Values
		if (source.getLegalValues() != null) {
			
			List<ListEntryDTO<?>> legalValues = new ArrayList<>();
			
				for(ListEntry<?> entry : source.getLegalValues()) {
					legalValues.add(listEntryMapper.toDTO(entry));
				}
		
			
			propertyDTO.setLegalValues(legalValues);
		}
		
		//TODO rules
		if (source.getRules() != null) {
			List<Rule> rules = new ArrayList<Rule>();
			for (Rule r : source.getRules()) {
				rules.add(r);
			}
			propertyDTO.setRules(ruleMapper.toDTOs(rules));
		}
		
		//TODO values
		
		if (source.getValues() != null) {
			List<ListEntryDTO> values = new ArrayList<>();
			for (ListEntry<?> entry : source.getValues()) {
				values.add(listEntryMapper.toDTO(entry));
			}
			propertyDTO.setValues(values);
		}
		
		return propertyDTO;
	}

	@Override
	public List<PropertyDTO<?>> toDTOs(List<Property<?>> sources) {
		if (sources == null)  {
			return null;
		}
		
		List<PropertyDTO<?>> list = new ArrayList<PropertyDTO<?>>(sources.size());
        for ( Property<?> propItem : sources ) {
            list.add( toDTO( propItem ) );
        }
		return list;
	}



	@Override
	public Property<?> toEntity(PropertyDTO<?> target) {
		
		if (target == null) {
			return null;
		}
		
		Property prop = new Property();
		
		prop.setId(target.getId());
		prop.setName(target.getName());
		prop.setDefaultValue(target.getDefaultValue());
		prop.setValue(target.getValue());
		
		prop.setKind(target.getKind());
		
		List<ListEntry<?>> values = new LinkedList<ListEntry<?>>();
		
		if (target.getValues() != null) {
			for (ListEntryDTO entry : target.getValues()) {
				values.add(listEntryMapper.toEntity(entry));
			}
		}
		prop.setValues(values);
		
		
		List<ListEntry<?>> legalValues = new LinkedList<ListEntry<?>>();
		
		if (target.getLegalValues() != null) {
			for (ListEntryDTO entry : target.getLegalValues()) {
				legalValues.add(listEntryMapper.toEntity(entry));
			}
		}
		
		prop.setLegalValues(legalValues);
		
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
	public List<Property<?>> toEntities(List<PropertyDTO<?>> targets) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
