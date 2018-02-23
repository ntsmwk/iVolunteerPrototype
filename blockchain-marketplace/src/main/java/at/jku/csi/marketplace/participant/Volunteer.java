package at.jku.csi.marketplace.participant;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.csi.marketplace.competence.Competence;
import at.jku.csi.marketplace.interest.Interest;

@Document
public class Volunteer extends Participant {

	private List<Interest> interestList;
	private List<Competence> competenceList;

	public List<Interest> getInterestList() {
		return interestList;
	}

	public void setInterestList(List<Interest> interestList) {
		this.interestList = interestList;
	}

	public List<Competence> getCompetenceList() {
		return competenceList;
	}

	public void setCompetenceList(List<Competence> competenceList) {
		this.competenceList = competenceList;
	}
}
