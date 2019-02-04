package at.jku.cis.iVolunteer.mapper.property;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.mapper.property.rule.RuleMapper;
import at.jku.cis.iVolunteer.model.property.DateProperty;
import at.jku.cis.iVolunteer.model.property.dto.DatePropertyDTO;

@Mapper(uses= {RuleMapper.class})
public abstract class DatePropertyMapper implements AbstractMapper<DateProperty, DatePropertyDTO> {

}
