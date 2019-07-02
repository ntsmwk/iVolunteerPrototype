package at.jku.cis.iVolunteer.mapper.property;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.property.rule.MultiRuleMapper;
import at.jku.cis.iVolunteer.mapper.property.rule.SingleRuleMapper;
import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.property.MultiPropertyRet;
import at.jku.cis.iVolunteer.model.property.dto.MultiPropertyRetDTO;

@Mapper(uses = { SingleRuleMapper.class, MultiRuleMapper.class})
public abstract class MultiPropertyRetMapper implements AbstractMapper<MultiPropertyRet, MultiPropertyRetDTO> {

}