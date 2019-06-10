package at.jku.cis.iVolunteer.mapper.property.rule;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.property.rule.MultiPropertyRule;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;

@Mapper
public abstract class MultiRuleMapper implements AbstractMapper<MultiPropertyRule, RuleDTO> {

}
