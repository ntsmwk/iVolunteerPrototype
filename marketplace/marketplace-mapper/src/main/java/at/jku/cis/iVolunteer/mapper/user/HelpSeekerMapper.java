package at.jku.cis.iVolunteer.mapper.user;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.user.HelpSeeker;
import at.jku.cis.iVolunteer.model.user.dto.HelpSeekerDTO;

@Mapper
public abstract class HelpSeekerMapper implements AbstractMapper<HelpSeeker, HelpSeekerDTO> {

}
