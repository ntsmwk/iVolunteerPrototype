package at.jku.cis.iVolunteer.mapper.property;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.property.rule.RuleMapper;
import at.jku.cis.iVolunteer.model.property.TextProperty;
import at.jku.cis.iVolunteer.model.property.dto.TextPropertyDTO;

@Mapper(uses= {RuleMapper.class})
public abstract class TextPropertyMapper implements AbstractMapper<TextProperty, TextPropertyDTO> {

}
