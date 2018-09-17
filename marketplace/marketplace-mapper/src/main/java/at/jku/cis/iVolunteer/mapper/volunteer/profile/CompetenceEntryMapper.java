package at.jku.cis.iVolunteer.mapper.volunteer.profile;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.volunteer.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.model.volunteer.profile.dto.CompetenceEntryDTO;

@Mapper
public abstract class CompetenceEntryMapper implements AbstractMapper<CompetenceEntry, CompetenceEntryDTO> {

}
