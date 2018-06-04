package at.jku.cis.iVolunteer.lib.mapper.participant.profile;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.lib.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.participant.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.model.participant.profile.dto.CompetenceEntryDTO;

@Mapper
public abstract class CompetenceEntryMapper implements AbstractMapper<CompetenceEntry, CompetenceEntryDTO> {

}
