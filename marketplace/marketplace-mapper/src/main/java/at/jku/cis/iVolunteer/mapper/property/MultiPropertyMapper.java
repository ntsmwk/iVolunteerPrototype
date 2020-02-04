package at.jku.cis.iVolunteer.mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.competence.CompetenceMapper;
import at.jku.cis.iVolunteer.mapper.property.listEntry.ListEntryMapper;
import at.jku.cis.iVolunteer.mapper.property.rule.MultiRuleMapper;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.MultiProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.old.dto.MultiPropertyDTO;
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
		propertyDTO.setType(source.getType());
		propertyDTO.setOrder(source.getOrder());
		
		propertyDTO.setProperties(propertyMapper.toDTOs(source.getProperties()));
		
		if (source.getRules() != null) {
			List<MultiPropertyRule> rules = new ArrayList<>();
			for (MultiPropertyRule r : source.getRules()) {
				rules.add(r);
			}
			propertyDTO.setRules(multiRuleMapper.toDTOs(rules));
		}
	
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
		prop.setType(target.getType());
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
		return null;
	}
	
}
