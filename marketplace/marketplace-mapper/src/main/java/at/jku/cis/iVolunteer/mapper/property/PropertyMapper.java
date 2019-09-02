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
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.MultiProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.Property;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.SingleProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.dto.PropertyDTO;
import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;
import at.jku.cis.iVolunteer.model.property.rule.MultiPropertyRule;
import at.jku.cis.iVolunteer.model.property.rule.SinglePropertyRule;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;


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
		propertyDTO.setType(source.getType());
		propertyDTO.setOrder(source.getOrder());
		propertyDTO.setCustom(source.isCustom());
				
		//TODO rules

		
		
		if (propertyDTO.getType().equals(PropertyType.MULTI) || propertyDTO.getType().equals(PropertyType.MAP) || propertyDTO.getType().equals(PropertyType.GRAPH)) {
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
//			SingleProperty<Object> s = new SingleProperty<Object>(source);
			
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
		prop.setType(target.getType());
		prop.setOrder(target.getOrder());
		prop.setCustom(target.isCustom());	
		
		if (prop.getType().equals(PropertyType.MULTI) || prop.getType().equals(PropertyType.MAP) || prop.getType().equals(PropertyType.GRAPH)) {
			MultiProperty ret = new MultiProperty(prop);
			List<Property> props = new ArrayList<>();
			
			if (target.getProperties() != null) {
				for (PropertyDTO<Object> dto : target.getProperties()) {
					props.add(propertyMapper.toEntity(dto));
				}
			}
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
					values.add(listEntryMapper.toEntity(entry, target.getType()));
				}
			}
			ret.setValues(values);
			
			List<ListEntry<Object>> legalValues = new ArrayList<ListEntry<Object>>();
			
			if (target.getLegalValues() != null) {
				for (ListEntryDTO<Object> entry : target.getLegalValues()) {
					legalValues.add(listEntryMapper.toEntity(entry, target.getType()));
				}
			}
			
			ret.setLegalValues(legalValues);
			
			List<ListEntry<Object>> defaultValues = new ArrayList<ListEntry<Object>>();
			
			if (target.getDefaultValues() != null) {
				for (ListEntryDTO<Object> entry : target.getDefaultValues()) {
					defaultValues.add(listEntryMapper.toEntity(entry, target.getType()));
				}
			}
			
			ret.setDefaultValues(defaultValues);
						
			List<SinglePropertyRule> rules = new ArrayList<SinglePropertyRule>();
			if (target.getRules() != null) {
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
}
