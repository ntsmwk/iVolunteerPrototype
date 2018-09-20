package at.jku.cis.iVolunteer.mapper.core.user;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;
import at.jku.cis.iVolunteer.model.core.user.dto.CoreHelpSeekerDTO;

@Mapper
public abstract class CoreHelpSeekerMapper implements AbstractMapper<CoreHelpSeeker, CoreHelpSeekerDTO> {

}
