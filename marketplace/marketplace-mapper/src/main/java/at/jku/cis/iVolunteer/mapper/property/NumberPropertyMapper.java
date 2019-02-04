package at.jku.cis.iVolunteer.mapper.property;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.property.rule.RuleMapper;
import at.jku.cis.iVolunteer.model.property.NumberProperty;
import at.jku.cis.iVolunteer.model.property.dto.NumberPropertyDTO;


@Mapper(uses= {RuleMapper.class})
public abstract class NumberPropertyMapper implements AbstractMapper<NumberProperty, NumberPropertyDTO> {

}
