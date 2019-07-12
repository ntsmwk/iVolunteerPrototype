package at.jku.cis.iVolunteer.mapper.property;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.property.rule.SinglePropertyRuleMapper;
import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.configurable.configurables.property.MultiPropertyRet;
import at.jku.cis.iVolunteer.model.property.dto.MultiPropertyRetDTO;

@Mapper(uses = { SinglePropertyRuleMapper.class})
public abstract class MultiPropertyRetMapper implements AbstractMapper<MultiPropertyRet, MultiPropertyRetDTO> {

}