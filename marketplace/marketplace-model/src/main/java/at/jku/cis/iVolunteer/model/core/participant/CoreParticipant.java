package at.jku.cis.iVolunteer.model.core.participant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.participant.Participant;

@Document
public abstract class CoreParticipant extends Participant{

	@DBRef
	private List<Marketplace> registeredMarketplaces = new ArrayList<>();

	public List<Marketplace> getRegisteredMarketplaces() {
		return registeredMarketplaces;
	}

	public void setRegisteredMarketplaces(List<Marketplace> registeredMarketplaces) {
		this.registeredMarketplaces = registeredMarketplaces;
	}
}
