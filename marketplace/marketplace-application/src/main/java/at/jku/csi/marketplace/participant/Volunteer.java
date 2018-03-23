package at.jku.csi.marketplace.participant;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.csi.marketplace.competence.Competence;

@Document
public class Volunteer extends Participant {

	private Set<Competence> competenceList = new HashSet<>();

	public Set<Competence> getCompetenceList() {
		return competenceList;
	}

	public void setCompetenceList(Set<Competence> competenceList) {
		this.competenceList = competenceList;
	}
}
