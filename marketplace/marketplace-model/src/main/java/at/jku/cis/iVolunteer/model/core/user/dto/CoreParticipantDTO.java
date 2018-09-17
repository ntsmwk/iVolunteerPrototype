package at.jku.cis.iVolunteer.model.core.user.dto;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.dto.UserDTO;

public class CoreParticipantDTO extends UserDTO {

	private List<Marketplace> registeredMarketplaces = new ArrayList<>();

	public List<Marketplace> getRegisteredMarketplaces() {
		return registeredMarketplaces;
	}

	public void setRegisteredMarketplaces(List<Marketplace> registeredMarketplaces) {
		this.registeredMarketplaces = registeredMarketplaces;
	}
}
