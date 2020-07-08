package at.jku.cis.iVolunteer.model.core.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.user.User;

@Document
public class CoreUser extends User {
	@DBRef
	private List<CoreUser> follower = new ArrayList<>();
	private List<String> registeredMarketplaceIds = new ArrayList<>();

	public List<String> getRegisteredMarketplaceIds() {
		return registeredMarketplaceIds;
	}

	public void setRegisteredMarketplaceIds(List<String> registeredMarketplaceIds) {
		this.registeredMarketplaceIds = registeredMarketplaceIds;
	}
}
