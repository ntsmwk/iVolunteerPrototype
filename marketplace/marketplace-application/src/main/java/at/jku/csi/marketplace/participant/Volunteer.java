package at.jku.csi.marketplace.participant;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.csi.marketplace.competence.Competence;

@Document
public class Volunteer extends Participant {

	private List<Competence> competenceList;

	public List<Competence> getCompetenceList() {
		return competenceList;
	}

	public void setCompetenceList(List<Competence> competenceList) {
		this.competenceList = competenceList;
	}
}
