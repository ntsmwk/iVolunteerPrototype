package at.jku.cis.iVolunteer.mapper.property.rule;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.property.rule.SinglePropertyRule;
import at.jku.cis.iVolunteer.model.property.rule.dto.RuleDTO;

@Mapper
public abstract class SingleRuleMapper implements AbstractMapper<SinglePropertyRule, RuleDTO> {

}
