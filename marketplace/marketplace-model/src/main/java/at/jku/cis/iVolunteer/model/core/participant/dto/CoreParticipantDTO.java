package at.jku.cis.iVolunteer.model.core.participant.dto;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.participant.dto.ParticipantDTO;

public class CoreParticipantDTO extends ParticipantDTO {

	private List<Marketplace> registeredMarketplaces = new ArrayList<>();

	public List<Marketplace> getRegisteredMarketplaces() {
		return registeredMarketplaces;
	}

	public void setRegisteredMarketplaces(List<Marketplace> registeredMarketplaces) {
		this.registeredMarketplaces = registeredMarketplaces;
	}
}
