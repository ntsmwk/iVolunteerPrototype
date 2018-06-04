package at.jku.cis.iVolunteer.lib.mapper.participant.profile;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.lib.mapper.AbstractSpringMapper;
import at.jku.cis.iVolunteer.model.participant.profile.CompetenceEntry;
import at.jku.cis.iVolunteer.model.participant.profile.dto.CompetenceEntryDTO;

@Service
public class CompetenceEntryMapper extends AbstractSpringMapper<CompetenceEntry, CompetenceEntryDTO> {

	public CompetenceEntryMapper() {
		super(CompetenceEntry.class, CompetenceEntryDTO.class);
	}
}
