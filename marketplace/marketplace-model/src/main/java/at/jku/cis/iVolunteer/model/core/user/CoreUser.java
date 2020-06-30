package at.jku.cis.iVolunteer.model.core.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.user.User;

@Document
public class CoreUser extends User {
	@DBRef
	private List<CoreUser> follower = new ArrayList<>();
	@DBRef
	private List<Marketplace> registeredMarketplaces = new ArrayList<>();

	public List<Marketplace> getRegisteredMarketplaces() {
		return registeredMarketplaces;
	}

	public void setRegisteredMarketplaces(List<Marketplace> registeredMarketplaces) {
		this.registeredMarketplaces = registeredMarketplaces;
	}
}
