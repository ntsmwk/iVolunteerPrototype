package at.jku.cis.iVolunteer.mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.competence.CompetenceMapper;
import at.jku.cis.iVolunteer.mapper.property.listEntry.ListEntryMapper;
import at.jku.cis.iVolunteer.mapper.property.rule.SinglePropertyRuleMapper;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.SingleProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.dto.SinglePropertyDTO;

import at.jku.cis.iVolunteer.model.property.listEntry.ListEntry;
import at.jku.cis.iVolunteer.model.property.listEntry.dto.ListEntryDTO;
import at.jku.cis.iVolunteer.model.property.rule.SinglePropertyRule;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;


//@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class SinglePropertyMapper implements AbstractMapper<SingleProperty<Object>, SinglePropertyDTO<Object>>{
	
	@Autowired SinglePropertyRuleMapper ruleMapper;
	@Autowired CompetenceMapper competenceMapper;
	@Autowired ListEntryMapper listEntryMapper;

	@Override
	public SinglePropertyDTO<Object> toDTO(SingleProperty<Object> source) {
		
		if (source == null) {
			return null;
		}
		
		SinglePropertyDTO<Object> propertyDTO = new SinglePropertyDTO<>();
		propertyDTO.setId(source.getId());
		propertyDTO.setName(source.getName());
		propertyDTO.setOrder(source.getOrder());	
		propertyDTO.setKind(source.getKind());
		
		if (source.getLegalValues() != null) {	
			List<ListEntryDTO<Object>> legalValues = new ArrayList<>();
			for(ListEntry<Object> entry : source.getLegalValues()) {
				legalValues.add(listEntryMapper.toDTO(entry));
			}
			propertyDTO.setLegalValues(legalValues);
		}
		
		if (source.getRules() != null) {
			
			System.out.println("rules not null");
			
			List<SinglePropertyRule> rules = new ArrayList<SinglePropertyRule>();
			for (SinglePropertyRule r : source.getRules()) {
				rules.add(r);
			}
			propertyDTO.setRules(ruleMapper.toDTOs(rules));
		}
		
		if (source.getValues() != null) {
			List<ListEntryDTO<Object>> values = new ArrayList<>();
			for (ListEntry<Object> entry : source.getValues()) {
				values.add(listEntryMapper.toDTO(entry));
			}
			propertyDTO.setValues(values);
		}
		
		if (source.getDefaultValues() != null) {
			List<ListEntryDTO<Object>> defaultValues = new ArrayList<>();
			for (ListEntry<Object> entry : source.getValues()) {
				defaultValues.add(listEntryMapper.toDTO(entry));
			}
			propertyDTO.setDefaultValues(defaultValues);
		}
		
		return propertyDTO;
	}

	@Override
	public List<SinglePropertyDTO<Object>> toDTOs(List<SingleProperty<Object>> sources) {
		if (sources == null)  {
			return null;
		}
		
		List<SinglePropertyDTO<Object>> list = new ArrayList<SinglePropertyDTO<Object>>(sources.size());
        for ( SingleProperty<Object> propItem : sources ) {
            list.add( toDTO( propItem ) );
        }
		return list;
	}



	@Override
	public SingleProperty<Object> toEntity(SinglePropertyDTO<Object> target) {
		
		if (target == null) {
			return null;
		}
		
		SingleProperty<Object> prop = new SingleProperty<>();
		
		prop.setId(target.getId());
		prop.setName(target.getName());
		prop.setOrder(target.getOrder());			
		prop.setKind(target.getKind());
		
		List<ListEntry<Object>> values = new ArrayList<ListEntry<Object>>();
		
		if (target.getValues() != null) {
			for (ListEntryDTO<Object> entry : target.getValues()) {
				values.add(listEntryMapper.toEntity(entry));
			}
		}
		prop.setValues(values);
		
		List<ListEntry<Object>> legalValues = new ArrayList<ListEntry<Object>>();
		
		if (target.getLegalValues() != null) {
			for (ListEntryDTO<Object> entry : target.getLegalValues()) {
				legalValues.add(listEntryMapper.toEntity(entry));
			}
		}	
		prop.setLegalValues(legalValues);
			
		List<ListEntry<Object>> defaultValues = new ArrayList<>();
		if (target.getDefaultValues() != null) {
			for (ListEntryDTO<Object> entry : target.getValues()) {
				defaultValues.add(listEntryMapper.toEntity(entry));
			}
		}
		prop.setDefaultValues(defaultValues);

		List<SinglePropertyRule> rules = new ArrayList<SinglePropertyRule>();

		if (target.getRules() != null) {
			for (RuleDTO r : target.getRules()) {
				rules.add(ruleMapper.toEntity(r));
			}
		}
		prop.setRules(rules);

		return prop;
	}

	@Override
	public List<SingleProperty<Object>> toEntities(List<SinglePropertyDTO<Object>> targets) {
		if (targets == null) {
			return null;
		}
		
		List<SingleProperty<Object>> list = new ArrayList<>();
		for (SinglePropertyDTO<Object> prop : targets) {
			list.add(this.toEntity(prop));
		}
		
		return list;
	}
}
