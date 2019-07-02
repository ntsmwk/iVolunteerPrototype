package at.jku.cis.iVolunteer.mapper.property;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.property.rule.RuleMapper;
import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.property.MultiplePropertyRet;
import at.jku.cis.iVolunteer.model.property.dto.MultiplePropertyRetDTO;

@Mapper(uses = { RuleMapper.class})
public abstract class MultiplePropertyRetMapper implements AbstractMapper<MultiplePropertyRet, MultiplePropertyRetDTO> {

}