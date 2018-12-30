package at.jku.cis.iVolunteer.model.volunteer.profile.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonObject;

import at.jku.cis.iVolunteer.model.hash.IHashObject;

public class CompetenceEntryDTO implements IHashObject {

	private String id;
	private String competenceId;
	private String competenceName;
	private String marketplaceId;
	private Date timestamp;
	
	// added
	private String volunteerId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompetenceId() {
		return competenceId;
	}

	public void setCompetenceId(String competenceId) {
		this.competenceId = competenceId;
	}

	public String getCompetenceName() {
		return competenceName;
	}

	public void setCompetenceName(String competenceName) {
		this.competenceName = competenceName;
	}

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}
	
	public void setVolunteerId(String volunteerId) {
		this.volunteerId = volunteerId;
	}
	
	public String getVolunteerId() {
		return volunteerId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toHashObject() {
		JsonObject json = new JsonObject();
		//json.addProperty("id", id);
		json.addProperty("timeStamp", new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'").format(timestamp).toString());
		json.addProperty("competenceId", competenceId);
		//json.addProperty("competenceName", competenceName);

		// added
		json.addProperty("marketplaceId", marketplaceId);
		json.addProperty("volunteerId", volunteerId);
		System.out.println(json.toString());
		return json.toString();
	}
	
//	public MultiValueMap<String, String> getProperties() {
//		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//		map.add("timeStamp", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(getTimestamp()));
//		map.add("competenceId", getCompetenceId());
//		map.add("marketplaceId", getMarketplaceId());
//		map.add("volunteerId", getVolunteerId());
//		return map;
//	}
}
