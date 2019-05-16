package at.jku.cis.iVolunteer.mapper.property;

import java.util.ArrayList;
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
		
		if (source.getRules() != null) {
			List<Rule> rules = new ArrayList<Rule>();
			for (Rule r : source.getRules()) {
				rules.add(r);
			}
			propertyDTO.setRules(ruleMapper.toDTOs(rules));
		}
	
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
				
		List<Rule> rules = new ArrayList<Rule>();
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
		return null;
	}
	
}
