package at.jku.cis.iVolunteer.mapper.competence;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.participant.profile.CompetenceEntry;

@Component
public class CompetenceEntryToCompetenceMapper {
	
	public Competence toCompetence(CompetenceEntry competenceEntry) {
		Competence competence = new Competence(competenceEntry.getCompetenceName());
		competence.setId(competenceEntry.getCompetenceId());
		return competence;
	}
}
