package at.jku.cis.iVolunteer.mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.competence.CompetenceMapper;
import at.jku.cis.iVolunteer.mapper.property.listEntry.ListEntryMapper;
import at.jku.cis.iVolunteer.mapper.property.rule.MultiRuleMapper;
import at.jku.cis.iVolunteer.mapper.property.rule.RuleMapper;
import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.SingleProperty;
import at.jku.cis.iVolunteer.model.property.MultipleProperty;
import at.jku.cis.iVolunteer.model.property.PropertyKind;
import at.jku.cis.iVolunteer.model.property.dto.PropertyDTO;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;
import at.jku.cis.iVolunteer.model.property.rule.MultiRule;
import at.jku.cis.iVolunteer.model.property.rule.Rule;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;


@Component
public class PropertyMapper implements AbstractMapper<Property, PropertyDTO<Object>> {
	
	@Autowired RuleMapper ruleMapper;
	@Autowired MultiRuleMapper multiRuleMapper;
	
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
		propertyDTO.setOrder(source.getOrder());
		propertyDTO.setCustom(source.isCustom());
		
		if (source.getRules() != null) {
			List<Rule> rules = new ArrayList<Rule>();
			for (Rule r : source.getRules()) {
				rules.add(r);
			}
			propertyDTO.setRules(ruleMapper.toDTOs(rules));
		}
		
		if (propertyDTO.getKind().equals(PropertyKind.MULTIPLE) || propertyDTO.getKind().equals(PropertyKind.MAP)) {
			List<PropertyDTO<Object>> props = new ArrayList<>();
			for (Property p : ((MultipleProperty)source).getProperties()) {
				props.add(propertyMapper.toDTO(p));
			}
			propertyDTO.setProperties(props);
			
			if (((MultipleProperty)source).getRules() != null) {
				List<MultiRule> rules = new ArrayList<>();
				for (MultiRule r : ((MultipleProperty)source).getRules()) {
					rules.add(r);
				}
				propertyDTO.setRules(multiRuleMapper.toDTOs(rules));
			}
			
			
			
		} else {
			SingleProperty<Object> s = (SingleProperty<Object>) source;
			
			if (s.getLegalValues() != null) {	
				List<ListEntryDTO<Object>> legalValues = new ArrayList<>();	
				for(ListEntry<Object> entry : s.getLegalValues()) {
					legalValues.add(listEntryMapper.toDTO(entry));
				}
				propertyDTO.setLegalValues(legalValues);
			}
			
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
		

		List<Rule> rules = new ArrayList<Rule>();
		if (target.getRules() != null) {
			for (RuleDTO r : target.getRules()) {
				rules.add(ruleMapper.toEntity(r));
			}
		}
		prop.setRules(rules);	

		
		if (prop.getKind().equals(PropertyKind.MULTIPLE)) {
			MultipleProperty ret = new MultipleProperty(prop);
			List<Property> props = new ArrayList<>();
			
			if (target.getProperties() != null) {
				for (PropertyDTO<Object> dto : target.getProperties()) {
					props.add(propertyMapper.toEntity(dto));
				}
			}
			ret.setProperties(props);
			
			List<MultiRule> rules = new ArrayList<>();
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
}
